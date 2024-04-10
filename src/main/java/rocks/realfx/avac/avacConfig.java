package rocks.realfx.avac;

import java.util.List;
import com.google.common.collect.Lists;

import eu.midnightdust.lib.config.MidnightConfig;

public class avacConfig extends MidnightConfig {

	@Entry(category = "client")
	public static boolean enableAvAC = true;

	@Comment(category = "client", centered = true)
	public static Comment advancedComment;

	@Entry(category = "client")
	public static String clientBrandWhenSuccess = "AvACSuccess";

	/* SERVER CONFIG */

	// Ignore this really fucked up declaration please.
	// TODO: improve this shit fuck list thing
	@Entry(category = "server")
	public static List<String> allowedMods = Lists.newArrayList(
            "midnightlib",
            "avac",
            "modmenu",
            "quilted_fabric_api",
            "quilt_loader",
            "minecraft",
            "mixinextras",
            "java"
    );

}
