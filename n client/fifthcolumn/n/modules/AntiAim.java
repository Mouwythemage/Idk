// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import meteordevelopment.meteorclient.utils.entity.Target;
import net.minecraft.class_1297;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.meteorclient.utils.entity.TargetUtils;
import meteordevelopment.meteorclient.utils.entity.SortPriority;
import net.minecraft.class_1802;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class AntiAim extends Module
{
    private final SettingGroup sgGeneral;
    private final Setting<Mode> mode;
    private final Setting<Integer> speed;
    private float lastYaw;
    
    public AntiAim() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "AntiAim", "Goofy rotations, interferes with some placement stuff");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = (Setting<Mode>)this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)new EnumSetting.Builder().name("mode")).defaultValue((Object)Mode.Spin)).build());
        this.speed = (Setting<Integer>)this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("speed")).visible(() -> this.mode.get() == Mode.Spin)).defaultValue((Object)5)).min(1).max(10).build());
    }
    
    public void onActivate() {
        if (this.mc.field_1724 != null) {
            this.lastYaw = this.mc.field_1724.method_36454();
        }
    }
    
    @EventHandler
    private void onTick(final TickEvent.Post event) {
        final class_1792 handStack = this.mc.field_1724.method_6047().method_7909();
        if (handStack == class_1802.field_8187 || handStack == class_1802.field_8705 || handStack == class_1802.field_8550) {
            return;
        }
        if (this.mode.get() == Mode.Stare) {
            final class_1657 target = TargetUtils.getPlayerTarget(9999.0, SortPriority.LowestDistance);
            if (target == null) {
                return;
            }
            Rotations.rotate(Rotations.getYaw((class_1297)target), Rotations.getPitch((class_1297)target, Target.Head));
        }
        else if (this.mode.get() == Mode.Spin) {
            this.lastYaw = (this.lastYaw + (int)this.speed.get() * 10) % 360.0f;
            Rotations.rotate((double)this.lastYaw, (double)this.mc.field_1724.method_36455());
        }
    }
    
    public enum Mode
    {
        Spin, 
        Stare;
        
        private static /* synthetic */ Mode[] $values() {
            return new Mode[] { Mode.Spin, Mode.Stare };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
