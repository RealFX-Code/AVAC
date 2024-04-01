package rocks.realfx.avac.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.payload.CustomPayload;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import rocks.realfx.avac.avacState;
import rocks.realfx.avac.common.avacPayload;
import rocks.realfx.avac.common.validatePayload;

import javax.swing.JOptionPane;
import java.awt.*;

public class onClientPlayerJoinEvent implements ClientPlayConnectionEvents.Join {

	@Override
	public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender<CustomPayload> sender, MinecraftClient client) {

		gatherClientInformation gatherClientInformation = new gatherClientInformation();
		avacPayload clientInfo = gatherClientInformation.getClientInfo();

		boolean clientValidated = validatePayload.validatePayload(
			clientInfo,
			validatePayload.env.CLIENT
		);

		if (clientValidated) avacState.clientSuccessfulValidation = true;

		ClientPlayNetworking.getSender().sendPacket(ClientPlayNetworking.createC2SPacket(clientInfo));

		if (!clientValidated) {
			JOptionPane.showMessageDialog(
				null,
				"Ask @tromsobadet on discord for more information.\nError code: 2",
				"FATAL ERROR",
				JOptionPane.ERROR_MESSAGE
			);
			MinecraftClient.getInstance().stop();
		}

	}
}
