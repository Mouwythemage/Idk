// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1934;
import java.util.Iterator;
import net.minecraft.class_2596;
import net.minecraft.class_2703;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.systems.modules.Module;

public class GameModeNotifier extends Module
{
    public GameModeNotifier() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "Gamemode Notifier", "Alerts you when someone changes gamemode");
    }
    
    @EventHandler
    public void onPacket(final PacketEvent.Receive event) {
        if (this.mc.method_1562() == null) {
            return;
        }
        final class_2596 packet2 = event.packet;
        if (packet2 instanceof class_2703) {
            final class_2703 packet = (class_2703)packet2;
            for (final class_2703.class_2705 entry : packet.method_46329()) {
                for (final class_2703.class_5893 action : packet.method_46327()) {
                    if (action.equals((Object)class_2703.class_5893.field_29137) && !packet.method_46330().contains(entry)) {
                        final class_1934 newGameMode = entry.comp_1110();
                        final String player = this.mc.method_1562().method_2871(entry.comp_1106()).method_2966().getName();
                        super.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, player, newGameMode.method_8381()), new Object[0]);
                    }
                }
            }
        }
    }
}
