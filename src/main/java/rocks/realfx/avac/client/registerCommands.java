package rocks.realfx.avac.client;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.text.Text;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import rocks.realfx.avac.avacConfig;
import rocks.realfx.avac.common.avacPayload;

import static net.minecraft.server.command.CommandManager.literal;

public class registerCommands {
  public static void RegisterCommands() {

    CommandRegistrationCallback.EVENT.register(
        (dispatcher, registryAccess, environment) -> {
          // getClientBrand
          dispatcher.register(
              literal("getClientBrand")
                  .executes(
                      context -> {
                        String out =
                            "Client Brand : From Vanilla  : "
                                + ClientBrandRetriever.getClientModName()
                                + "\n"
                                + "Client Brand : From Config : "
                                + avacConfig.clientBrandWhenSuccess;

                        context.getSource().sendFeedback(() -> Text.literal(out), false);

                        return 1;
                      }));

          // getCurrentModList
          dispatcher.register(
              literal("getCurrentModList")
                  .executes(
                      context -> {
                        gatherClientInformation gci = new gatherClientInformation();
                        avacPayload payload = gci.getClientInfo();
                        String out = "Current ModList: " + payload.mods();
                        context.getSource().sendFeedback(() -> Text.literal(out), false);
                        return 1;
                      }));
        });
  }
}
