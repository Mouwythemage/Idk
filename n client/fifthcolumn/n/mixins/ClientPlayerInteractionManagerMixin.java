// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import fifthcolumn.n.modules.Gun;
import meteordevelopment.meteorclient.systems.modules.Modules;
import fifthcolumn.n.modules.FastProjectile;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.class_1657;
import net.minecraft.class_636;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ class_636.class })
public class ClientPlayerInteractionManagerMixin
{
    @Inject(at = { @At("HEAD") }, method = { "stopUsingItem" })
    private void n$onStopUsingItem(final class_1657 player, final CallbackInfo ci) {
        final FastProjectile fp = (FastProjectile)Modules.get().get((Class)FastProjectile.class);
        if (fp.shouldEngage()) {
            fp.engage();
        }
        final Gun gun = (Gun)Modules.get().get((Class)Gun.class);
        if (gun.shouldShoot()) {
            gun.shoot();
        }
    }
}
