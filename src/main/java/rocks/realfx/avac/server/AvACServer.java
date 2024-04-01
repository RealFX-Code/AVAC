package rocks.realfx.avac.server;

import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.server.DedicatedServerModInitializer;
import org.quiltmc.qsl.networking.api.CustomPayloads;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import rocks.realfx.avac.AvAC;
import rocks.realfx.avac.common.NetworkingConstants;
import rocks.realfx.avac.common.avacPayload;

public class AvACServer implements DedicatedServerModInitializer {

	private final ServerPlayConnectionEvents.Join onPlayerJoinEvent = new onServerPlayerJoinEvent();

	void handleAvACPayload(
		MinecraftServer server,
		ServerPlayerEntity player,
		ServerPlayNetworkHandler handler,
		avacPayload payload,
		PacketSender<CustomPayload> responseSender
	) {
		AvAC.LOGGER.info(player.getProfileName() + " : " + payload.toString());

		if(rocks.realfx.avac.common.validatePayload.validatePayload(
			payload,
			rocks.realfx.avac.common.validatePayload.env.SERVER,
			player,
			handler,
			responseSender,
			server
		)){ // SUCCESS
			AvAC.LOGGER.info("Let {} into the server!", player.getProfileName());
		} else {
			if(!player.isDisconnected()){
				throw new Error("Client validation failed, but player wasn't disconnected!");
			}
		}

	}

	@Override
	public void onInitializeServer(ModContainer mod) {
		AvAC.LOGGER.info("Registering player join event...");
		ServerPlayConnectionEvents.JOIN.register(onPlayerJoinEvent);

		CustomPayloads.registerC2SPayload(NetworkingConstants.HIGHLIGHT_PACKET_ID, avacPayload::new);
		ServerPlayNetworking.registerGlobalReceiver(
			NetworkingConstants.HIGHLIGHT_PACKET_ID,
			this::handleAvACPayload
		);

	}

}
