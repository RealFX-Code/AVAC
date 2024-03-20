package rocks.realfx.avac.mixin;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.ModStatus;
import org.spongepowered.asm.mixin.Mixin;
import rocks.realfx.avac.avacConfig;

@Mixin(ModStatus.class)
public class IsModdedMixin {

	@Inject(
		method = "isModded",
		at = @At("HEAD"),
		cancellable = true)
	public boolean isModded(@NotNull CallbackInfoReturnable<Boolean> cir){
		if(avacConfig.OverrideVanillaModdedState){
			cir.setReturnValue(avacConfig.VanillaModdedState);
		}
		cir.cancel();
		return false;
    }
}

