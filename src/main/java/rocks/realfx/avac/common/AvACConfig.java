package rocks.realfx.avac.common;

import java.util.List;
import com.google.common.collect.Lists;

import eu.midnightdust.lib.config.MidnightConfig;

public class AvACConfig extends MidnightConfig {

  /* CLIENT CONFIG */

  @Entry(category = "client")
  public static boolean enableAvAC = true;

  @Comment(category = "client", centered = true)
  public static Comment advancedComment;

  @Entry(category = "client")
  public static String clientBrandWhenSuccess = "AvACSuccess";

  /* SERVER CONFIG */

  @Entry(category = "server")
  public static List<String> allowedMods =
      Lists.newArrayList(
          "midnightlib",
          "avac",
          "modmenu",
          "quilted_fabric_api",
          "quilt_loader",
          "minecraft",
          "mixinextras",
          "placeholder-api",
          "java");
}
