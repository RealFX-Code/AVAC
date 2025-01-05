package rocks.realfx.avac.mixin.client;

import net.minecraft.client.ClientBrandRetriever;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rocks.realfx.avac.common.AvACConfig;
import rocks.realfx.avac.client.AvACState;

@Mixin(value = ClientBrandRetriever.class)
public class GetClientModNameMixin {

  @Inject(method = "getClientModName", at = @At("HEAD"), cancellable = true, remap = false)
  private static void getClientModName(@NotNull CallbackInfoReturnable<String> cir) {
    if (AvACState.clientSuccessfulValidation) {
      cir.setReturnValue(AvACConfig.clientBrandWhenSuccess);
    } else {
      cir.setReturnValue("Vanilla");
    }
    cir.cancel();
  }
}
