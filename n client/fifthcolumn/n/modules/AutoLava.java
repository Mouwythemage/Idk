// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import meteordevelopment.meteorclient.utils.player.FindItemResult;
import net.minecraft.class_1268;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import net.minecraft.class_1802;
import net.minecraft.class_1792;
import meteordevelopment.orbit.EventHandler;
import java.util.Iterator;
import meteordevelopment.meteorclient.utils.player.Rotations;
import net.minecraft.class_243;
import net.minecraft.class_1657;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import net.minecraft.class_1297;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class AutoLava extends Module
{
    private final SettingGroup sgGeneral;
    private final Setting<Double> distance;
    private final Setting<Integer> tickInterval;
    private final Setting<Boolean> rotate;
    private class_1297 entity;
    private int ticks;
    
    public AutoLava() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "AutoLava", "do it");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.distance = (Setting<Double>)this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("distance")).description("how far")).min(0.0).max(4.5).defaultValue(4.5).build());
        this.tickInterval = (Setting<Integer>)this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("tick-interval")).defaultValue((Object)5)).build());
        this.rotate = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("rotate")).description("rot own u")).defaultValue((Object)true)).build());
        this.ticks = 0;
    }
    
    public void onDeactivate() {
        this.entity = null;
    }
    
    @EventHandler
    private void onTick(final TickEvent.Pre event) {
        this.entity = null;
        ++this.ticks;
        for (final class_1297 entity : this.mc.field_1687.method_18112()) {
            if (entity instanceof class_1657 && this.mc.field_1724.method_5739(entity) <= (double)this.distance.get() && entity != this.mc.field_1724) {
                if (entity.method_5771()) {
                    continue;
                }
                this.entity = entity;
                final class_243 go = new class_243(entity.field_6014 + (entity.method_23317() - entity.field_6014), entity.field_6036 + (entity.method_23318() - entity.field_6036), entity.field_5969 + (entity.method_23321() - entity.field_5969));
                Rotations.rotate(Rotations.getYaw(go), Rotations.getPitch(go), 100, this::placeLava);
                this.toggle();
            }
        }
    }
    
    private void placeLava() {
        final FindItemResult findItemResult = InvUtils.findInHotbar(new class_1792[] { class_1802.field_8187 });
        if (!findItemResult.found()) {
            this.error("No lava bucket found.", new Object[0]);
            this.toggle();
            return;
        }
        final int prevSlot = this.mc.field_1724.method_31548().field_7545;
        this.mc.field_1724.method_31548().field_7545 = findItemResult.slot();
        this.mc.field_1761.method_2919((class_1657)this.mc.field_1724, class_1268.field_5808);
        this.mc.field_1724.method_31548().field_7545 = prevSlot;
    }
}
