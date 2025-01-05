package rocks.realfx.avac.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;
import rocks.realfx.avac.common.ModInfo;

public class ModMenuInit implements ModMenuApi {
  @Override
  public ConfigScreenFactory<?> getModConfigScreenFactory() {
    return parent -> MidnightConfig.getScreen(parent, ModInfo.MOD_ID);
  }
}
