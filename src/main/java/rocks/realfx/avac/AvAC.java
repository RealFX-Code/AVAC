package rocks.realfx.avac;

import eu.midnightdust.lib.config.MidnightConfig;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static rocks.realfx.avac.modInfo.MOD_DESCRIPTOR;
import static rocks.realfx.avac.modInfo.MOD_ID;

public class AvAC implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize(@NotNull ModContainer mod) {

		LOGGER.info(" Loading {}...", MOD_DESCRIPTOR);

		LOGGER.info(" Initializing Configuration...");

		MidnightConfig.init(MOD_ID, avacConfig.class);

	}
}
