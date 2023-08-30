// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import java.util.Optional;
import fifthcolumn.n.modules.StreamerMode;
import fifthcolumn.n.modules.LarpModule;
import com.mojang.authlib.GameProfile;
import net.minecraft.class_2338;
import net.minecraft.class_1937;
import net.minecraft.class_1657;
import meteordevelopment.meteorclient.systems.modules.render.Nametags;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Nametags.class })
public abstract class NameTagMixin
{
    @ModifyVariable(method = { "renderNametagPlayer" }, at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private class_1657 n$modifyPlayerNametag(final class_1657 player) {
        return new class_1657(player.method_37908(), player.method_24515(), player.method_36454(), player.method_7334()) {
            public String method_5820() {
                Optional<String> playerEntityName = LarpModule.getPlayerEntityName(player);
                if (playerEntityName.isEmpty()) {
                    playerEntityName = StreamerMode.getPlayerEntityName(player);
                }
                return playerEntityName.orElse(player.method_5820());
            }
            
            public boolean method_7325() {
                return player.method_7325();
            }
            
            public boolean method_7337() {
                return player.method_7337();
            }
        };
    }
}
