package rocks.realfx.avac.client;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;
import rocks.realfx.avac.AvAC;

public class AvACClient implements ClientModInitializer {

	private final ClientPlayConnectionEvents.Join onPlayerJoinEvent = new onClientPlayerJoinEvent();

	@Override
	public void onInitializeClient(ModContainer mod) {
		AvAC.LOGGER.info("Hi, From Client!");
		registerCommands.RegisterCommands();

		ClientPlayConnectionEvents.JOIN.register(onPlayerJoinEvent);

	}
}
