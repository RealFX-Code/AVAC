package rocks.realfx.avac;

import eu.midnightdust.lib.config.MidnightConfig;

public class avacConfig extends MidnightConfig {

	@Entry(category = "client")
	public static boolean enableAvAC = true;

	@Comment(category = "client", centered = true)
	public static Comment advancedComment;

	@Entry(category = "client")
	public static String clientBrandWhenSuccess = "AvACSuccess";

}
