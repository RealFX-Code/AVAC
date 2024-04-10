package rocks.realfx.avac.client;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.text.Text;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import rocks.realfx.avac.avacConfig;

import static net.minecraft.server.command.CommandManager.literal;

public class registerCommands {
	public static void RegisterCommands(){

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(literal("getClientBrand")
				.executes(context -> {
					String out = "Client Brand : From Vanilla  : " + ClientBrandRetriever.getClientModName() + "\n" +
						"Client Brand : From Config : " + avacConfig.clientBrandWhenSuccess + "\n";

					context.getSource().sendFeedback(() -> Text.literal(out), false);

					return 1;
				}));
		});
	}
}
