package rocks.realfx.avac.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;
import rocks.realfx.avac.modInfo;

public class ModMenuInit implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> MidnightConfig.getScreen(parent, modInfo.MOD_ID);
	}

}
