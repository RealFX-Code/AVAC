package rocks.realfx.avac.client;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.payload.CustomPayload;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import rocks.realfx.avac.AvAC;
import rocks.realfx.avac.avacState;
import rocks.realfx.avac.common.avacPayload;
import rocks.realfx.avac.common.validatePayload;

import javax.swing.*;

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
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
				AvAC.LOGGER.error(String.valueOf(e));
            }

			new JFXPanel();

			Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("FATAL ERROR");
				alert.setHeaderText("AN INTENTIONAL ERROR HAS OCCURRED.");
                alert.setContentText("Error code: 2");
                alert.showAndWait();
				client.scheduleStop();
                Platform.exit();
            });
		}

	}
}
