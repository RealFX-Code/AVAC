package rocks.realfx.avac.client;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.text.Text;
import net.minecraft.util.ModStatus;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import rocks.realfx.avac.avacConfig;

import static net.minecraft.server.command.CommandManager.literal;

public class registerCommands {
	public static void RegisterCommands(){

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(literal("getClientBrand")
				.executes(context -> {
					String out = "Client Brand : From Mixin   : " + ClientBrandRetriever.getClientModName() + "\n" +
						"Client Brand : From Config : " + avacConfig.teststring1 + "\n";

					context.getSource().sendFeedback(() -> Text.literal(out), false);

					return 1;
				}));
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(literal("isClientModded")
				.executes(context -> {
					ModStatus.Confidence[] IsModdedConfidences = {
						ModStatus.Confidence.PROBABLY_NOT,
						ModStatus.Confidence.VERY_LIKELY,
						ModStatus.Confidence.DEFINITELY
					};
					StringBuilder out = new StringBuilder();
					// add config value
					out.append("Is client modded? : ").append(avacConfig.testval2).append(" : ").append("FROM_LOCAL_CONFIG").append("\n");
					for (ModStatus.Confidence confidence : IsModdedConfidences) {
						ModStatus modStatus = new ModStatus(confidence, "");
						out.append("Is client modded? : ").append(modStatus.isModded()).append(" : ").append(confidence).append("\n");
					}

					context.getSource().sendFeedback(() -> Text.literal(out.toString()), false);

					return 1;
				}));
		});
	}
}
