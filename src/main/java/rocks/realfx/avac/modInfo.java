package rocks.realfx.avac;

import org.quiltmc.config.api.annotations.Comment;

public class modInfo {
	static {
		MOD_ID = "avac";
		GROUP_ID = "rocks.realfx";
		MOD_DESCRIPTOR = GET_MOD_DESCRIPTOR();
	}

	@Comment("The ModID for this mod.")
	public static final String MOD_ID;

	@Comment("The GroupID for this mod")
	public static final String GROUP_ID;

	private static String GET_MOD_DESCRIPTOR(){
		return GROUP_ID + "." + MOD_ID;
	}

	public static final String MOD_DESCRIPTOR;

}
