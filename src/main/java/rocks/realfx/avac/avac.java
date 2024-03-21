package rocks.realfx.avac;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.util.ModStatus;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.client.ClientBrandRetriever;

import static net.minecraft.server.command.CommandManager.*;


public class avac implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("avac");

	@Comment("The Mod ID for this Mod.")
	public static final String MOD_ID = "avac";

	@Override
	public void onInitialize(@NotNull ModContainer mod) {

		LOGGER.warn(" Loading {}...", mod.metadata().name());

		LOGGER.info(" Initializing Configuration...");

		MidnightConfig.init(MOD_ID, avacConfig.class);

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("isClientModded")
			.executes(context -> {
				ModStatus.Confidence[] IsModdedConfidences = {
					ModStatus.Confidence.PROBABLY_NOT,
					ModStatus.Confidence.VERY_LIKELY,
					ModStatus.Confidence.DEFINITELY
				};
				StringBuilder out = new StringBuilder();
				// add config value
				out.append("Is client modded? : ").append(avacConfig.VanillaModdedState).append(" : ").append("FROM_LOCAL_CONFIG").append("\n");
				for (ModStatus.Confidence confidence : IsModdedConfidences) {
					ModStatus modStatus = new ModStatus(confidence, "");
					out.append("Is client modded? : ").append(modStatus.isModded()).append(" : ").append(confidence).append("\n");
				}

                context.getSource().sendFeedback(() -> Text.literal(out.toString()), false);

				return 1;
			})));

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("getClientBrand")
			.executes(context -> {

                String out = "Client Brand : From Mixin   : " + ClientBrandRetriever.getClientModName() + "\n" +
                        "Client Brand : From Config : " + avacConfig.customClientBrand + "\n";

				context.getSource().sendFeedback(() -> Text.literal(out), false);

				return 1;
			})));

	}
}
