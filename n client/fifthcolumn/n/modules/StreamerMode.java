// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import java.util.Optional;
import net.minecraft.class_1657;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import fifthcolumn.n.NMod;
import net.minecraft.class_640;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_2596;
import net.minecraft.class_124;
import net.minecraft.class_2561;
import net.minecraft.class_7439;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.StringSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class StreamerMode extends Module
{
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> hideServerInfo;
    private final Setting<Boolean> hideAccount;
    private final Setting<Boolean> generifyPlayerNames;
    private final Setting<Integer> addFakePlayers;
    private final Setting<String> spoofServerBrand;
    public final Setting<Boolean> useRandomIpOffset;
    
    public StreamerMode() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "Streamer mode", "Hides sensitive info from stream viewers");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.hideServerInfo = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("hide server info")).defaultValue((Object)true)).build());
        this.hideAccount = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("hide Logged in account text")).defaultValue((Object)true)).build());
        this.generifyPlayerNames = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("use a generic name for non-griefers")).defaultValue((Object)true)).build());
        this.addFakePlayers = (Setting<Integer>)this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("add fake players to tablist")).sliderRange(0, 10).defaultValue((Object)5)).build());
        this.spoofServerBrand = (Setting<String>)this.sgGeneral.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)new StringSetting.Builder().name("spoof server brand")).description("Change server brand label in F3, blank to disable.")).defaultValue((Object)"Paper devs are ops")).build());
        this.useRandomIpOffset = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("use a random ip header offset")).defaultValue((Object)true)).build());
    }
    
    @EventHandler
    public void onMessage(final PacketEvent.Receive event) {
        final class_2596 packet2 = event.packet;
        if (packet2 instanceof class_7439) {
            final class_7439 packet = (class_7439)packet2;
            if (packet.comp_763().getString().contains("join") && isGenerifyNames()) {
                event.cancel();
                final class_2561 text = (class_2561)class_2561.method_43470("A player has joined the game").method_27692(class_124.field_1054);
                this.mc.field_1705.method_1743().method_1812(text);
            }
        }
    }
    
    public static boolean isStreaming() {
        return ((StreamerMode)Modules.get().get((Class)StreamerMode.class)).isActive();
    }
    
    public static boolean isHideServerInfoEnabled() {
        final StreamerMode streamerMode = (StreamerMode)Modules.get().get((Class)StreamerMode.class);
        return streamerMode != null && streamerMode.isActive() && (boolean)streamerMode.hideServerInfo.get();
    }
    
    public static boolean isHideAccountEnabled() {
        final StreamerMode streamerMode = (StreamerMode)Modules.get().get((Class)StreamerMode.class);
        return streamerMode != null && streamerMode.isActive() && (boolean)streamerMode.hideAccount.get();
    }
    
    public static boolean isGenerifyNames() {
        final StreamerMode streamerMode = (StreamerMode)Modules.get().get((Class)StreamerMode.class);
        return streamerMode != null && streamerMode.isActive() && (boolean)streamerMode.generifyPlayerNames.get();
    }
    
    public static int addFakePlayers() {
        final StreamerMode streamerMode = (StreamerMode)Modules.get().get((Class)StreamerMode.class);
        if (streamerMode != null && streamerMode.isActive()) {
            return (int)streamerMode.addFakePlayers.get();
        }
        return 0;
    }
    
    public static String spoofServerBrand() {
        return (String)((StreamerMode)Modules.get().get((Class)StreamerMode.class)).spoofServerBrand.get();
    }
    
    @Nullable
    public static String anonymizePlayerNameInstances(String text) {
        if (MeteorClient.mc != null && MeteorClient.mc.field_1724 != null && MeteorClient.mc.method_1562() != null && isGenerifyNames()) {
            for (final class_640 player : MeteorClient.mc.method_1562().method_2880()) {
                if (player.method_2966() != null) {
                    final String fakeName = NMod.genericNames.getName(player.method_2966().getId());
                    text = StringUtils.replace(text, player.method_2966().getName(), fakeName);
                    if (player.method_2971() == null) {
                        continue;
                    }
                    text = StringUtils.replace(text, player.method_2971().getString(), fakeName);
                }
            }
        }
        return text;
    }
    
    public static Optional<String> getPlayerEntityName(final class_1657 player) {
        if (isGenerifyNames() && MeteorClient.mc.method_1562() != null && !player.method_7334().getId().equals(MeteorClient.mc.field_1724.method_5667()) && MeteorClient.mc.method_1562() != null && isGenerifyNames()) {
            return Optional.of(NMod.genericNames.getName(player.method_7334().getId()));
        }
        return Optional.empty();
    }
}
