// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import meteordevelopment.orbit.EventHandler;
import java.util.Iterator;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import fifthcolumn.n.copenheimer.CopeService;
import fifthcolumn.n.NMod;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class GrieferTracer extends Module
{
    private final SettingGroup sgGeneral;
    private final Setting<SettingColor> playersColor;
    
    public GrieferTracer() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "Griefer Tracer", "Tracers to fellow Griefers. Disabled on 2b2t.org.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.playersColor = (Setting<SettingColor>)this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)new ColorSetting.Builder().name("players-colors")).description("The griefers color.")).defaultValue(new SettingColor(205, 205, 205, 127)).build());
    }
    
    @EventHandler
    private void onRender(final Render3DEvent event) {
        for (final CopeService.Griefer entity : NMod.getCopeService().griefers()) {
            if (entity.location == null) {
                continue;
            }
            System.out.println(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Lfifthcolumn/n/copenheimer/CopeService$Position;)Ljava/lang/String;, entity.playerName, entity.location));
            final Color color = (Color)this.playersColor.get();
            final double x = entity.location.x;
            final double y = entity.location.y;
            final double z = entity.location.z;
            event.renderer.line(RenderUtils.center.field_1352, RenderUtils.center.field_1351, RenderUtils.center.field_1350, x, y, z, color);
        }
    }
}
