package rocks.realfx.avac.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.payload.CustomPayload;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import rocks.realfx.avac.AvAC;
import rocks.realfx.avac.common.avacPayload;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class onClientPlayerJoinEvent implements ClientPlayConnectionEvents.Join {

	// 30-03-2024
	// Stolen from: https://www.baeldung.com/java-list-directory-files#dir-stream
	public Set<String> listFiles(String dir) throws IOException {
		Set<String> fileSet = new HashSet<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
			for (Path path : stream) {
				if (!Files.isDirectory(path)) {
					fileSet.add(path.getFileName()
						.toString());
				}
			}
		}
		return fileSet;
	}

	@Override
	public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender<CustomPayload> sender, MinecraftClient client) {

		PacketByteBuf buf = PacketByteBufs.create();

		List<String> modIDs = new ArrayList<>();

		for (ModContainer modContainer : QuiltLoader.getAllMods()) {
			String modID = modContainer.metadata().id();

			// Stop cluttering from mod list
			boolean isNestedLib = modContainer.metadata().group().startsWith("org.quiltmc.qsl") ||
                    modContainer.metadata().group().startsWith("org.quiltmc.quilted-fabric-api");

			if(!isNestedLib)
			{
				modIDs.add(modID);
			}

		}

		List<String> rpacks = new ArrayList<>();

        try {
            Set<String> ResourcePacks = listFiles(String.valueOf(MinecraftClient.getInstance().getResourcePackDir()));
			for( String pack : ResourcePacks) {
				rpacks.add(pack.replaceAll(" ","_"));
			}
        } catch (IOException e) {
			AvAC.LOGGER.error("Could not validate resource packs!", e);
			rpacks.add(String.valueOf(e));
        }

        CustomPayload customPayload = new avacPayload(
			modIDs,
			rpacks
		);

		ClientPlayNetworking.getSender().sendPacket(ClientPlayNetworking.createC2SPacket(customPayload));
	}
}
