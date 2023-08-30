// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import net.minecraft.class_742;
import net.minecraft.class_2561;
import net.minecraft.class_124;
import java.util.function.Function;
import meteordevelopment.meteorclient.systems.friends.Friend;
import meteordevelopment.meteorclient.systems.friends.Friends;
import java.util.function.Consumer;
import fifthcolumn.n.copenheimer.CopeService;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import fifthcolumn.n.events.GrieferUpdateEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class AutoCutie extends Module
{
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> announcer;
    
    public AutoCutie() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "Auto Cutie", "Automatically adds all online griefers to meteor friends");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.announcer = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("Announcer")).description("Announces new friends in chat")).defaultValue((Object)true)).build());
    }
    
    @EventHandler
    private void onGrieferListUpdate(final GrieferUpdateEvent event) {
        this.addFriends(event.griefers);
    }
    
    private void addFriends(final List<CopeService.Griefer> griefers) {
        if (this.mc == null || this.mc.field_1724 == null || this.mc.field_1687 == null) {
            return;
        }
        griefers.stream().map(griefer -> griefer.playerName).filter(playerName -> !this.mc.field_1724.method_5820().equalsIgnoreCase(playerName)).forEach((Consumer<? super Object>)this::addFriend);
    }
    
    private void addFriend(final String playerName) {
        final Friends friends = Friends.get();
        if (this.mc == null || this.mc.field_1687 == null || this.mc.field_1724 == null) {
            return;
        }
        this.mc.field_1687.method_18456().stream().filter(player -> playerName.equalsIgnoreCase(player.method_5820())).findFirst().map(Friend::new).ifPresent(friend -> {
            if (friends.add(friend) && (boolean)this.announcer.get()) {
                this.mc.field_1724.method_7353(class_2561.method_30163(invokedynamic(makeConcatWithConstants:(Lnet/minecraft/class_124;Ljava/lang/String;)Ljava/lang/String;, class_124.field_1060, playerName)), true);
            }
        });
    }
}
