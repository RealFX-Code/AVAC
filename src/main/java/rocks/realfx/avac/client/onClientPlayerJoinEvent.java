package rocks.realfx.avac.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.payload.CustomPayload;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import rocks.realfx.avac.AvAC;
import rocks.realfx.avac.common.AvACConfig;
import rocks.realfx.avac.common.AvACPayload;
import rocks.realfx.avac.common.PayloadValidator;

import javax.swing.JOptionPane;

import static rocks.realfx.avac.common.NetworkingConstants.HANDSHAKE_PACKET;

public class onClientPlayerJoinEvent implements ClientPlayConnectionEvents.Join {

  @Override
  public void onPlayReady(
      ClientPlayNetworkHandler handler,
      PacketSender<CustomPayload> sender,
      MinecraftClient client) {

    // Respect user's choice.
    if (!AvACConfig.enableAvAC) {
      AvAC.LOGGER.warn("Skipped running AvAC as it's disabled.");
      return;
    }

    // Register handshake
    ClientPlayNetworking.registerReceiver(
        HANDSHAKE_PACKET,
        (minecraftClient, clientPlayNetworkHandler, buf, responseSender) -> {
          boolean handshakeRequested = buf.readBoolean();
          AvAC.LOGGER.info("Handshake requested from server!1");
          if (handshakeRequested) {
            AvAC.LOGGER.info("Handshake requested from server!2");
            minecraftClient.execute(
                () -> {
                  PacketByteBuf responseBuf = PacketByteBufs.create();
                  responseBuf.writeBoolean(AvACConfig.enableAvAC);
                  ClientPlayNetworking.send(HANDSHAKE_PACKET, responseBuf);
                });
          }
        });

    // Register AVAC payload

    GatherClientInformation gatherClientInformation = new GatherClientInformation();
    AvACPayload clientInfo = gatherClientInformation.get();

    boolean clientValidated = PayloadValidator.validate(clientInfo, PayloadValidator.env.CLIENT);

    if (clientValidated) AvACState.clientSuccessfulValidation = true;

    ClientPlayNetworking.getSender().sendPacket(ClientPlayNetworking.createC2SPacket(clientInfo));

    if (!clientValidated) {
      JOptionPane.showMessageDialog(
          null,
          "An intentional error has occurred. Please contact an administrator.\nError code: 2");
    }
  }
}
