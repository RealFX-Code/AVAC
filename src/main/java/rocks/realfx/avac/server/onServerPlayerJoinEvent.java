package rocks.realfx.avac.server;

import rocks.realfx.avac.AvAC;
import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;

public class onServerPlayerJoinEvent implements ServerPlayConnectionEvents.Join {

	@Override
	public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender<CustomPayload> sender, MinecraftServer server) {
		AvAC.LOGGER.warn(handler.getPlayer().getProfileName() + " joined!");
	}
}
