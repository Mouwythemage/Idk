// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import net.minecraft.class_1657;
import net.minecraft.class_3419;
import fifthcolumn.n.NMod;
import meteordevelopment.meteorclient.events.entity.player.InteractItemEvent;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1799;
import net.minecraft.class_2487;
import net.minecraft.class_1802;
import net.minecraft.class_1268;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Module;

public class Gun extends Module
{
    public Gun() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "Gun", "my gun go");
    }
    
    @EventHandler
    private void onTick(final TickEvent.Pre event) {
        final class_1799 hand = this.mc.field_1724.method_5998(class_1268.field_5808);
        if (hand.method_7909() == class_1802.field_8102) {
            final class_2487 tag = hand.method_7985() ? hand.method_7969() : new class_2487();
            tag.method_10569("CustomModelData", 123);
        }
    }
    
    @EventHandler
    private void onInteractItem(final InteractItemEvent event) {
        if (this.mc.field_1687 != null && this.mc.field_1724.method_5998(class_1268.field_5808).method_7909() == class_1802.field_8102) {
            this.mc.field_1687.method_8396((class_1657)this.mc.field_1724, this.mc.field_1724.method_24515(), NMod.cockSoundEvent, class_3419.field_15246, 1.0f, 1.0f);
        }
    }
    
    public boolean shouldShoot() {
        return this.isActive() && this.mc.field_1724.method_6047().method_7909() == class_1802.field_8102;
    }
    
    public void shoot() {
        this.mc.field_1687.method_8396((class_1657)this.mc.field_1724, this.mc.field_1724.method_24515(), NMod.shotgunSoundEvent, class_3419.field_15246, 1.0f, 1.0f);
    }
}
