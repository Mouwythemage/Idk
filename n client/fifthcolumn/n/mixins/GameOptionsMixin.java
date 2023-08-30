// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import net.minecraft.class_2803;
import fifthcolumn.n.modules.StreamerMode;
import net.minecraft.class_2596;
import net.minecraft.class_315;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ class_315.class })
public class GameOptionsMixin
{
    @ModifyArg(method = { "sendClientSettings" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V"))
    private class_2596<?> n$forceDisableServerListing(final class_2596<?> packet) {
        if (StreamerMode.isStreaming()) {
            final class_2803 p = (class_2803)packet;
            return (class_2596<?>)new class_2803(p.comp_266(), p.comp_267(), p.comp_268(), p.comp_269(), p.comp_270(), p.comp_271(), p.comp_272(), false);
        }
        return packet;
    }
}
