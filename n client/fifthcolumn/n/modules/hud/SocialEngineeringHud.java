// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules.hud;

import java.util.function.Supplier;
import meteordevelopment.meteorclient.systems.hud.Hud;
import java.util.stream.Stream;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Iterator;
import fifthcolumn.n.client.Input;
import java.util.Collection;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.settings.ColorSetting;
import fifthcolumn.n.NMod;
import java.util.ArrayList;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import net.minecraft.class_310;
import fifthcolumn.n.copenheimer.CopeService;
import java.util.List;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudElement;

public class SocialEngineeringHud extends HudElement
{
    public static final HudElementInfo<SocialEngineeringHud> INFO;
    private static final Color RED;
    private final List<CopeService.ServerPlayer> playerList;
    private final CopeService copeService;
    private final class_310 mc;
    private boolean isCracked;
    private final SettingGroup sgGeneral;
    private final Setting<SettingColor> textColor;
    
    public SocialEngineeringHud() {
        super((HudElementInfo)SocialEngineeringHud.INFO);
        this.playerList = new ArrayList<CopeService.ServerPlayer>();
        this.copeService = NMod.getCopeService();
        this.mc = class_310.method_1551();
        this.sgGeneral = this.settings.getDefaultGroup();
        this.textColor = (Setting<SettingColor>)this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)new ColorSetting.Builder().name("text-color")).description("A.")).defaultValue(new SettingColor()).build());
    }
    
    public void tick(final HudRenderer renderer) {
        super.tick(renderer);
        double width = renderer.textWidth("Players:");
        double height = renderer.textHeight();
        if (this.mc.field_1687 == null) {
            this.box.setSize(width, height);
            return;
        }
        synchronized (this.playerList) {
            for (final CopeService.ServerPlayer entity : this.playerList) {
                final String text = entity.name;
                width = Math.max(width, renderer.textWidth(text));
                height += renderer.textHeight() + 2.0;
            }
        }
        this.box.setSize(width, height);
        this.copeService.findHistoricalPlayers(serverPlayers -> {
            synchronized (this.playerList) {
                this.playerList.clear();
                this.playerList.addAll(this.filterList(serverPlayers));
                this.isCracked = this.playerList.stream().anyMatch(serverPlayer -> (serverPlayer.isValid != null && !serverPlayer.isValid) || !Input.isValidMinecraftUsername(serverPlayer.name));
            }
        });
    }
    
    private List<CopeService.ServerPlayer> filterList(final List<CopeService.ServerPlayer> list) {
        Stream<CopeService.ServerPlayer> playerListStream = list.stream();
        if (list.size() > 50) {
            playerListStream = playerListStream.filter(serverPlayer -> serverPlayer.isValid != null && serverPlayer.isValid);
        }
        return playerListStream.limit(100L).collect((Collector<? super CopeService.ServerPlayer, ?, List<CopeService.ServerPlayer>>)Collectors.toList());
    }
    
    public void render(final HudRenderer renderer) {
        double x = this.x;
        double y = this.y;
        renderer.text(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.isCracked ? "(cracked)" : ""), x, y, this.isCracked ? SocialEngineeringHud.RED : ((Color)this.textColor.get()), true);
        if (this.mc.field_1687 == null) {
            return;
        }
        synchronized (this.playerList) {
            for (final CopeService.ServerPlayer entity : this.playerList) {
                x = this.x;
                y += renderer.textHeight() + 2.0;
                final String text = entity.name;
                Color color;
                if (entity.isValid == null) {
                    color = Color.ORANGE;
                }
                else if (entity.isValid) {
                    color = Color.GREEN;
                }
                else {
                    color = Color.RED;
                }
                renderer.text(text, x, y, color, true);
            }
        }
    }
    
    static {
        INFO = new HudElementInfo(Hud.GROUP, "Social engineering", "Im friends with Chris", (Supplier)SocialEngineeringHud::new);
        RED = new Color(255, 0, 0);
    }
}
