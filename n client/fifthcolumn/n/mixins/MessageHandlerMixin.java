// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import meteordevelopment.orbit.ICancellable;
import meteordevelopment.meteorclient.MeteorClient;
import fifthcolumn.n.events.ReceiveMsgEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.class_2556;
import com.mojang.authlib.GameProfile;
import net.minecraft.class_7471;
import net.minecraft.class_7594;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ class_7594.class })
public class MessageHandlerMixin
{
    @Inject(method = { "onChatMessage" }, at = { @At("RETURN") }, cancellable = true)
    private void n$msgReceiveEvent(final class_7471 msg, final GameProfile sender, final class_2556.class_7602 p, final CallbackInfo ci) {
        if (((ReceiveMsgEvent)MeteorClient.EVENT_BUS.post((ICancellable)ReceiveMsgEvent.get(msg.method_46291(), sender.getName(), p))).isCancelled()) {
            ci.cancel();
        }
    }
}
