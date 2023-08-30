// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import fifthcolumn.n.copenheimer.CopeService;
import meteordevelopment.meteorclient.MeteorClient;
import fifthcolumn.n.NMod;
import net.minecraft.class_1657;
import net.minecraft.class_2960;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.class_742;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ class_742.class })
public abstract class AbstractClientPlayerEntityMixin
{
    @Inject(method = { "getCapeTexture" }, at = { @At("HEAD") }, cancellable = true)
    private void n$modifyCapeTexture(final CallbackInfoReturnable<class_2960> info) {
        final class_1657 entity = (class_1657)this;
        final CopeService copeService = NMod.getCopeService();
        if ((MeteorClient.mc.field_1724 != null && MeteorClient.mc.field_1724.method_5820().equals(entity.method_5820())) || copeService.griefers().stream().anyMatch(griefer -> entity.method_5820().equals(griefer.playerName))) {
            info.setReturnValue((Object)NMod.CAPE_TEXTURE);
        }
    }
}
