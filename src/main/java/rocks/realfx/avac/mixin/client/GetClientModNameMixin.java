package rocks.realfx.avac.mixin.client;

import net.minecraft.client.ClientBrandRetriever;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rocks.realfx.avac.avacConfig;

@Mixin(value = ClientBrandRetriever.class)
public class GetClientModNameMixin {

	@Inject(
		method="getClientModName",
		at = @At("HEAD"),
		cancellable = true,
		remap = false)
	private static void getClientModName(@NotNull CallbackInfoReturnable<String> cir){
		// TODO: return modified string after mod list check
		// TODO: eventual resource pack check
		cir.setReturnValue(avacConfig.teststring1);
		cir.cancel();
	}
}
