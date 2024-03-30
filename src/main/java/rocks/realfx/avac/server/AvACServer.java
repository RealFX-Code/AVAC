package rocks.realfx.avac.server;

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
import rocks.realfx.avac.AvAC;
import rocks.realfx.avac.common.NetworkingConstants;
import rocks.realfx.avac.common.avacPayload;

import java.util.List;
public class AvACServer implements DedicatedServerModInitializer {

	private final ServerPlayConnectionEvents.Join onPlayerJoinEvent = new onServerPlayerJoinEvent();

	private void handleTestPayload(
		MinecraftServer server,
		ServerPlayerEntity player,
		ServerPlayNetworkHandler handler,
		avacPayload payload,
		PacketSender<CustomPayload> responseSender)
	{
		AvAC.LOGGER.info("{}", payload.toString());

		List<String> resourcepacks = payload.rpacks();

		for (String pack : resourcepacks){
			if(pack.toLowerCase().contains("xray")){
                player.networkHandler.disconnect(Text.of("You have to uninstall the resource pack: \"" + pack + "\" to play!"));
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
			this::handleTestPayload
		);

	}

}
