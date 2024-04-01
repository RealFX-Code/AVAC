package rocks.realfx.avac.common;

import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.quiltmc.qsl.networking.api.PacketSender;
import rocks.realfx.avac.AvAC;

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

		for (String pack : resourcepacks){
			// simple xray check
			if(pack.toLowerCase().contains("xray")){
                if (env == SERVER) { // if handling server-side validation
					AvAC.LOGGER.error("{} FAILED VALIDATION SERVER-SIDED!", player.getProfileName());
                    player.networkHandler.disconnect(Text.of("You have to uninstall the resource pack: \"" + pack + "\" to play!"));
                }
                return false;
            }
		}

		return true;

	}

}
