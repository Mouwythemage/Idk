// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.class_2487;
import java.io.InputStream;
import org.slf4j.Logger;
import net.minecraft.class_4284;
import java.io.DataInput;
import net.minecraft.class_2507;
import java.io.DataInputStream;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.class_748;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import com.mojang.datafixers.DataFixer;
import net.minecraft.class_302;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ class_302.class })
public abstract class HotbarStorageMixin
{
    @Final
    @Shadow
    private DataFixer field_1648;
    @Final
    @Shadow
    private class_748[] field_1644;
    
    @Inject(method = { "load" }, at = { @At("HEAD") }, cancellable = true)
    private void n$loadBuiltInCreativeHotbar(final CallbackInfo cb) {
        final String HOTBAR_HOTBAR_NBT = "hotbar/hotbar.nbt";
        final Logger LOGGER = LoggerFactory.getLogger((Class)HotbarStorageMixin.class);
        try {
            final InputStream in = HotbarStorageMixin.class.getClassLoader().getResourceAsStream("hotbar/hotbar.nbt");
            if (in == null) {
                LOGGER.error("Could not find hotbar hotbar/hotbar.nbt");
                return;
            }
            class_2487 nbtComp = class_2507.method_10627((DataInput)new DataInputStream(in));
            if (nbtComp != null) {
                if (!nbtComp.method_10573("DataVersion", 99)) {
                    nbtComp.method_10569("DataVersion", 1343);
                }
                nbtComp = class_4284.field_19215.method_48130(this.field_1648, nbtComp, nbtComp.method_10550("DataVersion"));
                for (int i = 0; i < 9; ++i) {
                    this.field_1644[i].method_3152(nbtComp.method_10554(String.valueOf(i), 10));
                }
            }
        }
        catch (Exception var3) {
            LOGGER.error("Failed to load creative mode options", (Throwable)var3);
        }
        cb.cancel();
    }
}
