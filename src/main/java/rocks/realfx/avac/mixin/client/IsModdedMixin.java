package rocks.realfx.avac.mixin.client;

import net.minecraft.util.ModStatus;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rocks.realfx.avac.avacConfig;

@Mixin(ModStatus.class)
public class IsModdedMixin {

	@Inject(
		method = "isModded",
		at = @At("HEAD"),
		cancellable = true)
	public void isModded(@NotNull CallbackInfoReturnable<Boolean> cir){
		if(avacConfig.testval1){
			cir.setReturnValue(avacConfig.testval2);
		}
		cir.cancel();
	}
}

