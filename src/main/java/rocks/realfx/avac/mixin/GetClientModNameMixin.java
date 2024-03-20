package rocks.realfx.avac.mixin;

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
	private static @NotNull String getClientModName(@NotNull CallbackInfoReturnable<String> cir){
		cir.setReturnValue(avacConfig.customClientBrand);
		return avacConfig.customClientBrand;
	}
}
