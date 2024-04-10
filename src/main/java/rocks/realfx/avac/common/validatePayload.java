package rocks.realfx.avac.common;

import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.quiltmc.qsl.networking.api.PacketSender;
import rocks.realfx.avac.AvAC;
import rocks.realfx.avac.avacConfig;

import java.util.List;

import static rocks.realfx.avac.common.validatePayload.env.*;

public class validatePayload {
	public enum env {
		CLIENT,
		SERVER
	}

	public static boolean validatePayload(
		avacPayload payload,
		env env
	){
		return validatePayload(
			payload,
			env,
			null,
			null,
			null,
			null
		);
	}

	public static boolean validatePayload(
		avacPayload payload,
		env env,
        ServerPlayerEntity player,
		ServerPlayNetworkHandler handler,
		PacketSender<CustomPayload> responseSender,
        MinecraftServer server
	) {

		List<String> resourcepacks = payload.rpacks();

		for (String pack : resourcepacks) {
			// simple xray check
			if (pack.toLowerCase().contains("xray")) {
				if (env == SERVER) { // if handling server-side validation
					AvAC.LOGGER.warn("{} : Offending rpack : {}", player.getProfileName(), pack);
					AvAC.LOGGER.error("{} FAILED VALIDATION SERVER-SIDED!", player.getProfileName());
					// "You have to uninstall the resource pack: \"" + pack + "\" to play!"
					player.networkHandler.disconnect(Text.of("DM @tromsobadet on discord. Error code: 2"));
				}
				return false;
			}
		}

		for (String mod : payload.mods()) {
			if (!contains(avacConfig.allowedMods, mod)) {
				AvAC.LOGGER.warn("UNKNOWN MOD : " + mod);
			}
		}

		return true;

	}

	private static boolean contains(List<String> list, String pattern){
		for (String str : list) {
			if(str.equals(pattern)){
				return true;
			}
		}
		return false;
	}


}
