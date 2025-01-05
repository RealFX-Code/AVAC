package rocks.realfx.avac.server;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.server.DedicatedServerModInitializer;
import org.quiltmc.qsl.networking.api.CustomPayloads;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import io.netty.buffer.Unpooled;
import rocks.realfx.avac.AvAC;
import rocks.realfx.avac.common.avacPayload;
import rocks.realfx.avac.common.NetworkingConstants;
import static rocks.realfx.avac.common.NetworkingConstants.HANDSHAKE_PACKET;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AvACServer implements DedicatedServerModInitializer {

  private final onServerPlayerJoinEvent onPlayerJoinEvent = new onServerPlayerJoinEvent();
  private static final Map<UUID, Boolean> HANDSHAKE_STATUS = new ConcurrentHashMap<>();
  private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);

  void handleAvACPayload(
      MinecraftServer server,
      ServerPlayerEntity player,
      ServerPlayNetworkHandler handler,
      avacPayload payload,
      PacketSender<CustomPayload> responseSender) {
    AvAC.LOGGER.info(player.getProfileName() + " : " + payload.toString());

    if (rocks.realfx.avac.common.payloadValidator.validate(
        payload,
        rocks.realfx.avac.common.payloadValidator.env.SERVER,
        player,
        handler,
        responseSender,
        server)) { // SUCCESS
      AvAC.LOGGER.info("Let {} into the server!", player.getProfileName());
    } else { // no success
      if (!player.isDisconnected()) {
        throw new Error("Client validation failed, but player wasn't disconnected!");
      }
    }
  }

  @Override
  public void onInitializeServer(ModContainer mod) {
    AvAC.LOGGER.info("Registering player join event...");

    // Register payload validation
    ServerPlayConnectionEvents.JOIN.register(onPlayerJoinEvent);
    CustomPayloads.registerC2SPayload(NetworkingConstants.HIGHLIGHT_PACKET_ID, avacPayload::new);
    ServerPlayNetworking.registerGlobalReceiver(
        NetworkingConstants.HIGHLIGHT_PACKET_ID, this::handleAvACPayload);

    // Register handshake
    // Handle handshake response from client
    ServerPlayNetworking.registerGlobalReceiver(
        HANDSHAKE_PACKET,
        (server, player, handler, buf, responseSender) -> {
          AvAC.LOGGER.info("Recieved handshake from player {}!", player.getProfileName());
          boolean modEnabled = buf.readBoolean();
          if (modEnabled) {
            HANDSHAKE_STATUS.put(player.getUuid(), true);
          } else {
            HANDSHAKE_STATUS.put(player.getUuid(), false);
          }
        });

    // Handle player join and initiate handshake
    ServerPlayConnectionEvents.JOIN.register(
        (handler, sender, server) -> {
          ServerPlayerEntity player = handler.player;
          HANDSHAKE_STATUS.put(player.getUuid(), false); // Mark as not yet verified

          // Wait 1 second before sending handshake request
          // This allows clients on connections with more than nanoseconds of latency to join.
          SCHEDULER.schedule(
              () ->
                  server.execute(
                      () -> {
                        // Send the handshake packet to the client
                        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                        buf.writeBoolean(true); // Request handshake
                        ServerPlayNetworking.send(player, HANDSHAKE_PACKET, buf);
                      }),
              1,
              TimeUnit.SECONDS);

          // Allow client up to 5 seconds to respond to handshake.
          SCHEDULER.schedule(
              () ->
                  server.execute(
                      () -> {
                        // if (HANDSHAKE_STATUS.getOrDefault(player.getUuid(), false)) {
                        //	player.networkHandler.disconnect(Text.literal("AVAC must be enabled and
                        // installed in order to join this server."));
                        //	AvAC.LOGGER.warn("Player {} doesn't have AvAC!",
                        // player.getProfileName());
                        // } else {
                        //	AvAC.LOGGER.info("Player {} seems to have AvAC installed!",
                        // player.getProfileName());
                        // }
                        if (HANDSHAKE_STATUS.containsKey(player.getUuid())) {
                          if (HANDSHAKE_STATUS.get(player.getUuid())) {
                            AvAC.LOGGER.info(
                                "Player {} seems to have AvAC installed!", player.getProfileName());
                          } else {
                            player.networkHandler.disconnect(
                                Text.literal(
                                    "AVAC must be enabled and installed in order to join this server."));
                            AvAC.LOGGER.warn(
                                "Player {} doesn't have AvAC!", player.getProfileName());
                          }
                        } else {
                          player.networkHandler.disconnect(
                              Text.literal(
                                  "AVAC must be enabled and installed in order to join this server."));
                          AvAC.LOGGER.warn("Player {} doesn't have AvAC!", player.getProfileName());
                        }
                      }),
              5,
              TimeUnit.SECONDS);
        });

    // Handle player disconnect to clean up map
    ServerPlayConnectionEvents.DISCONNECT.register(
        (handler, server) -> {
          HANDSHAKE_STATUS.remove(handler.player.getUuid());
        });
  }
}
