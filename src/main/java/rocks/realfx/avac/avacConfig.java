package rocks.realfx.avac;

import eu.midnightdust.lib.config.MidnightConfig;

public class avacConfig extends MidnightConfig {

	@Entry(category = "client")
	public static boolean enable = true;

	@Entry(category = "client")
	public static String customClientBrand = "LClient";

	@Entry(category = "client")
	public static boolean OverrideVanillaModdedState = false;

	@Comment(category = "client")
	public static Comment comment1;

	@Entry(category = "client")
	public static boolean VanillaModdedState = false;

}

/*
public class avacConfig extends ReflectiveConfig {
	public static final avacConfig INSTANCE = QuiltConfig.create(avac.MOD_ID, avac.MOD_ID, avacConfig.class);

	@Comment("Report if the client is modded to the server")
	public final TrackedValue<Boolean> IsClientModded;

	@Comment("The \"Client Brand\" to announce to the server.")
	public final TrackedValue<String> ClientBrandName;

    public avacConfig() {
		// Initialize Defaults
        ClientBrandName = this.value("Vanilla(ClientParamEditor)");
        IsClientModded = this.value(true);
    }
}
*/
