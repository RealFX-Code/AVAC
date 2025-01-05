package rocks.realfx.avac.common;

import net.minecraft.util.Identifier;

public class NetworkingConstants {
  public static final Identifier HIGHLIGHT_PACKET_ID =
      new Identifier(ModInfo.MOD_ID, "client_mod_list");
  public static final Identifier HANDSHAKE_PACKET = new Identifier(ModInfo.MOD_ID, "handshake");
}
