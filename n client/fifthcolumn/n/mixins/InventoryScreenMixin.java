// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import net.minecraft.class_1713;
import net.minecraft.class_1735;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import meteordevelopment.meteorclient.systems.modules.Modules;
import fifthcolumn.n.modules.InventoryDupe;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.class_1703;
import net.minecraft.class_2561;
import net.minecraft.class_1661;
import net.minecraft.class_490;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.class_1723;
import net.minecraft.class_485;

@Mixin({ class_490.class })
public abstract class InventoryScreenMixin extends class_485<class_1723>
{
    public InventoryScreenMixin(final class_1723 container, final class_1661 playerInventory, final class_2561 name) {
        super((class_1703)container, playerInventory, name);
    }
    
    @Inject(method = { "init" }, at = { @At("TAIL") })
    private void n$addInventoryDupeButton(final CallbackInfo ci) {
        if (((InventoryDupe)Modules.get().get((Class)InventoryDupe.class)).isActive()) {
            this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Dupe"), button -> this.dupe()).method_46434(this.field_2776 + 130, this.field_22790 / 2 - 24, 40, 20).method_46431());
        }
    }
    
    private void dupe() {
        if (((InventoryDupe)Modules.get().get((Class)InventoryDupe.class)).isActive()) {
            final class_1735 outputSlot = (class_1735)((class_1723)this.field_2797).field_7761.get(0);
            this.method_2383(outputSlot, outputSlot.field_7874, 0, class_1713.field_7795);
        }
    }
}
