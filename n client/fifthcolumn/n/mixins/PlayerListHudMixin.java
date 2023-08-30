// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.mojang.authlib.GameProfile;
import fifthcolumn.n.NMod;
import java.util.UUID;
import java.util.Collection;
import java.util.ArrayList;
import fifthcolumn.n.modules.StreamerMode;
import java.util.List;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.class_640;
import java.util.Comparator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.class_310;
import net.minecraft.class_355;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ class_355.class })
public class PlayerListHudMixin
{
    @Shadow
    @Final
    private class_310 field_2155;
    @Shadow
    @Final
    private static Comparator<class_640> field_2156;
    
    @Inject(method = { "collectPlayerEntries()Ljava/util/List;" }, at = { @At("HEAD") }, cancellable = true)
    private void n$addFakePlayerListing(final CallbackInfoReturnable<List<class_640>> cir) {
        if (this.field_2155.field_1724 != null) {
            final int fakePlayers = StreamerMode.addFakePlayers();
            final List<class_640> players = new ArrayList<class_640>(this.field_2155.field_1724.field_3944.method_45732().stream().sorted(PlayerListHudMixin.field_2156).limit(80L - fakePlayers).toList());
            for (int i = 0; i < fakePlayers; ++i) {
                final UUID uuid = UUID.nameUUIDFromBytes(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, i + 1).getBytes());
                final GameProfile fakeProfile = new GameProfile(uuid, NMod.genericNames.getName(uuid));
                players.add(new class_640(fakeProfile, false));
            }
            cir.setReturnValue((Object)players);
        }
    }
}
