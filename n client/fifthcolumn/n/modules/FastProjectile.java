// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import meteordevelopment.meteorclient.systems.modules.player.AntiHunger;
import meteordevelopment.meteorclient.systems.modules.movement.NoFall;
import net.minecraft.class_1802;
import meteordevelopment.meteorclient.systems.modules.Modules;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.class_2828;
import net.minecraft.class_2596;
import net.minecraft.class_1297;
import net.minecraft.class_2848;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import net.minecraft.class_1792;
import java.util.List;
import net.minecraft.class_310;
import meteordevelopment.meteorclient.systems.modules.Module;

public class FastProjectile extends Module
{
    public static final class_310 mc;
    public static final List<class_1792> BOWS;
    private static final List<Module> CONFLICTING_MODULES;
    private final SettingGroup sgGeneral;
    public final Setting<Integer> packetFactor;
    
    public FastProjectile() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "FastProjectile", "Instakill with bows ;)");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.packetFactor = (Setting<Integer>)this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("packet-factor")).description("How many packets to send, more packets means more power.")).defaultValue((Object)100)).min(1).max(200).sliderRange(1, 200).build());
    }
    
    public boolean shouldEngage() {
        return this.isActive() && FastProjectile.BOWS.contains(FastProjectile.mc.field_1724.method_6047().method_7909());
    }
    
    public void engage() {
        final List<Module> modules = disengageConflictingModules();
        FastProjectile.mc.field_1724.field_3944.method_2883((class_2596)new class_2848((class_1297)FastProjectile.mc.field_1724, class_2848.class_2849.field_12981));
        for (int i = 0; i < (int)getModule(FastProjectile.class).settings.get("Packet Factor").get(); ++i) {
            FastProjectile.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(FastProjectile.mc.field_1724.method_23317(), FastProjectile.mc.field_1724.method_23318() - 1.0E-9, FastProjectile.mc.field_1724.method_23321(), true));
            FastProjectile.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(FastProjectile.mc.field_1724.method_23317(), FastProjectile.mc.field_1724.method_23318() + 1.0E-9, FastProjectile.mc.field_1724.method_23321(), false));
        }
        reengageModules(disengageConflictingModules());
    }
    
    public static List<Module> disengageConflictingModules() {
        final List<Module> modules = FastProjectile.CONFLICTING_MODULES.stream().filter(Module::isActive).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        modules.forEach(Module::toggle);
        return modules;
    }
    
    public static void reengageModules(final List<Module> modules) {
        modules.forEach(Module::toggle);
    }
    
    private static Module getModule(final Class module) {
        return Modules.get().get(module);
    }
    
    static {
        mc = class_310.method_1551();
        BOWS = List.of(class_1802.field_8102, class_1802.field_8399);
        CONFLICTING_MODULES = List.of(getModule(NoFall.class), getModule(AntiHunger.class));
    }
}
