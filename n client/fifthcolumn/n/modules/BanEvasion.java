// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.events.world.ServerConnectEndEvent;
import net.minecraft.class_310;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import net.minecraft.class_642;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class BanEvasion extends Module
{
    private final SettingGroup sgGeneral;
    public class_642 lastServer;
    private final Setting<Boolean> addSpacesToName;
    public final Setting<Boolean> evadeAndReconnect;
    
    public BanEvasion() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "Ban Evasion", "Options for evading bans");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.addSpacesToName = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("add spaces to name")).description("makes it easy to evade bans")).defaultValue((Object)false)).build());
        this.evadeAndReconnect = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("Toggle from evade and reconnect button")).description("makes it easy to evade bans")).defaultValue((Object)true)).build());
        MeteorClient.EVENT_BUS.subscribe((Object)new StaticListener());
    }
    
    public static boolean isSpacesToNameEnabled() {
        final BanEvasion banEvasion = (BanEvasion)Modules.get().get((Class)BanEvasion.class);
        return banEvasion != null && banEvasion.isActive() && (boolean)banEvasion.addSpacesToName.get();
    }
    
    private class StaticListener
    {
        @EventHandler
        private void onConnectToServer(final ServerConnectEndEvent event) {
            BanEvasion.this.lastServer = (BanEvasion.this.mc.method_1542() ? null : BanEvasion.this.mc.method_1558());
        }
    }
}
