// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import fifthcolumn.n.modules.StreamerMode;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ class_746.class })
public class ClientPlayerEntityMixin
{
    @Inject(method = { "getServerBrand" }, at = { @At("HEAD") }, cancellable = true)
    private void n$fakeServerBrand(final CallbackInfoReturnable<String> cir) {
        final String fakeBrand = StreamerMode.spoofServerBrand();
        if (!fakeBrand.isEmpty()) {
            cir.setReturnValue((Object)fakeBrand);
        }
    }
}
