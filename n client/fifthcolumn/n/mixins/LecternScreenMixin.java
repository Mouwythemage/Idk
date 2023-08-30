// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import java.util.Iterator;
import java.util.List;
import net.minecraft.class_2371;
import net.minecraft.class_1703;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.class_2813;
import net.minecraft.class_1713;
import meteordevelopment.meteorclient.mixin.ClientConnectionAccessor;
import net.minecraft.class_1799;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.class_1735;
import com.google.common.collect.Lists;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import meteordevelopment.meteorclient.systems.modules.Modules;
import fifthcolumn.n.modules.LecternCrash;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.class_2561;
import net.minecraft.class_3935;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.class_437;

@Mixin({ class_3935.class })
public class LecternScreenMixin extends class_437
{
    protected LecternScreenMixin(final class_2561 title) {
        super(title);
    }
    
    @Inject(method = { "init" }, at = { @At("TAIL") })
    public void n$addServerCrashButton(final CallbackInfo ci) {
        if (((LecternCrash)Modules.get().get((Class)LecternCrash.class)).isActive()) {
            this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Crash Server"), button -> {
                final class_1703 screenHandler = this.field_22787.field_1724.field_7512;
                final class_2371<class_1735> defaultedList = (class_2371<class_1735>)screenHandler.field_7761;
                final int i = defaultedList.size();
                final List<class_1799> list = (List<class_1799>)Lists.newArrayListWithCapacity(i);
                for (final class_1735 slot : defaultedList) {
                    list.add(slot.method_7677().method_7972());
                }
                final Int2ObjectMap<class_1799> int2ObjectMap = (Int2ObjectMap<class_1799>)new Int2ObjectOpenHashMap();
                for (int slot2 = 0; slot2 < i; ++slot2) {
                    final class_1799 itemStack = list.get(slot2);
                    final class_1799 itemStack2 = ((class_1735)defaultedList.get(slot2)).method_7677();
                    if (!class_1799.method_7973(itemStack, itemStack2)) {
                        int2ObjectMap.put(slot2, (Object)itemStack2.method_7972());
                    }
                }
                ((ClientConnectionAccessor)this.field_22787.method_1562().method_48296()).getChannel().writeAndFlush((Object)new class_2813(this.field_22787.field_1724.field_7512.field_7763, this.field_22787.field_1724.field_7512.method_37421(), 0, 0, class_1713.field_7794, this.field_22787.field_1724.field_7512.method_34255().method_7972(), (Int2ObjectMap)int2ObjectMap));
                this.field_22787.field_1724.method_7353(class_2561.method_30163("Crashing Server..."), false);
                button.field_22763 = false;
            }).method_46434(10, 10, 160, 20).method_46431());
        }
    }
}
