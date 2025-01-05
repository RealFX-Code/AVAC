package rocks.realfx.avac.client;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.text.Text;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import rocks.realfx.avac.common.AvACConfig;
import rocks.realfx.avac.common.AvACPayload;

import static net.minecraft.server.command.CommandManager.literal;

public class CommandRegistration {
  public static void register() {

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
                                + AvACConfig.clientBrandWhenSuccess;

                        context.getSource().sendFeedback(() -> Text.literal(out), false);

                        return 1;
                      }));

          // getCurrentModList
          dispatcher.register(
              literal("getCurrentModList")
                  .executes(
                      context -> {
                        GatherClientInformation gci = new GatherClientInformation();
                        AvACPayload payload = gci.get();
                        String out = "Current ModList: " + payload.mods();
                        context.getSource().sendFeedback(() -> Text.literal(out), false);
                        return 1;
                      }));
        });
  }
}
