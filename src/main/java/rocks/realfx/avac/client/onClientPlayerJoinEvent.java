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
import rocks.realfx.avac.avacConfig;
import rocks.realfx.avac.avacState;
import rocks.realfx.avac.common.avacPayload;
import rocks.realfx.avac.common.payloadValidator;

import javax.swing.JOptionPane;

import static rocks.realfx.avac.common.NetworkingConstants.HANDSHAKE_PACKET;

public class onClientPlayerJoinEvent implements ClientPlayConnectionEvents.Join {

	@Override
	public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender<CustomPayload> sender, MinecraftClient client) {

		// Respect user's choice.
		if(!avacConfig.enableAvAC) {
			AvAC.LOGGER.warn("Skipped running AvAC as it's disabled.");
			return;
		}

		// Register handshake
		ClientPlayNetworking.registerReceiver(HANDSHAKE_PACKET, (minecraftClient, clientPlayNetworkHandler, buf, responseSender) -> {
			boolean handshakeRequested = buf.readBoolean();
			AvAC.LOGGER.info("Handshake requested from server!1");
			if (handshakeRequested) {
				AvAC.LOGGER.info("Handshake requested from server!2");
				minecraftClient.execute(() -> {
					PacketByteBuf responseBuf = PacketByteBufs.create();
					responseBuf.writeBoolean(avacConfig.enableAvAC);
					ClientPlayNetworking.send(HANDSHAKE_PACKET, responseBuf);
				});
			}
		});

		// Register AVAC payload

		gatherClientInformation gatherClientInformation = new gatherClientInformation();
		avacPayload clientInfo = gatherClientInformation.getClientInfo();

		boolean clientValidated = payloadValidator.validate(
			clientInfo,
			payloadValidator.env.CLIENT
		);

		if (clientValidated) avacState.clientSuccessfulValidation = true;

		ClientPlayNetworking.getSender().sendPacket(ClientPlayNetworking.createC2SPacket(clientInfo));

		if (!clientValidated) {
			JOptionPane.showMessageDialog(
				null,
				"An intentional error has occurred. Please contact an administrator.\nError code: 2"
			);
		}

	}
}
