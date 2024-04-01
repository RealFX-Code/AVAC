package rocks.realfx.avac.client;

import net.minecraft.client.MinecraftClient;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;
import rocks.realfx.avac.AvAC;
import rocks.realfx.avac.avacConfig;
import rocks.realfx.avac.common.validatePayload;

public class AvACClient implements ClientModInitializer {

	private final ClientPlayConnectionEvents.Join onPlayerJoinEvent = new onClientPlayerJoinEvent();

	@Override
	public void onInitializeClient(ModContainer mod) {
		AvAC.LOGGER.info("Performing Client-Side initialization...");
		registerCommands.RegisterCommands();

		if(!avacConfig.enableAvAC){
			AvAC.LOGGER.warn("AvAC is disabled.");
			return;
		}

		ClientPlayConnectionEvents.JOIN.register(onPlayerJoinEvent);

	}
}
