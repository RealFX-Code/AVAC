package rocks.realfx.avac.server;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.feature_flags.FeatureFlagBitSet;
import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.quiltmc.qsl.base.api.entrypoint.server.DedicatedServerModInitializer;
import org.quiltmc.qsl.networking.api.CustomPayloads;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import rocks.realfx.avac.AvAC;
import org.quiltmc.loader.api.ModContainer;
import rocks.realfx.avac.common.avacPayload;
import rocks.realfx.avac.common.NetworkingConstants;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class AvACServer implements DedicatedServerModInitializer {

	private final ServerPlayConnectionEvents.Join onPlayerJoinEvent = new onServerPlayerJoinEvent();

	private void handleTestPayload(
		MinecraftServer server,
		ServerPlayerEntity player,
		ServerPlayNetworkHandler handler,
		avacPayload payload,
		PacketSender<CustomPayload> responseSender)
	{
		AvAC.LOGGER.info("{}", payload.toString());

		List<String> resourcepacks = payload.rpacks();

		for (String pack : resourcepacks){
			if(pack.toLowerCase().contains("xray")){
                player.networkHandler.disconnect(Text.of("You have to uninstall the resource pack: \"" + pack + "\" to play!"));
			}
		}

    }

	@Override
	public void onInitializeServer(ModContainer mod) {
		AvAC.LOGGER.info("Registering player join event...");
		ServerPlayConnectionEvents.JOIN.register(onPlayerJoinEvent);

		CustomPayloads.registerC2SPayload(NetworkingConstants.HIGHLIGHT_PACKET_ID, avacPayload::new);
		ServerPlayNetworking.registerGlobalReceiver(
			NetworkingConstants.HIGHLIGHT_PACKET_ID,
			(ServerPlayNetworking.CustomChannelReceiver<avacPayload>) this::handleTestPayload
		);

	}

}
