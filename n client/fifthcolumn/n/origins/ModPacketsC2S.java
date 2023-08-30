// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.origins;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import fifthcolumn.n.modules.OriginsModule;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.class_3248;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;

public class ModPacketsC2S
{
    public static void register() {
        ServerLoginConnectionEvents.QUERY_START.register((Object)ModPacketsC2S::handshake);
    }
    
    private static void handshake(final class_3248 serverLoginNetworkHandler, final MinecraftServer minecraftServer, final PacketSender packetSender, final ServerLoginNetworking.LoginSynchronizer loginSync) {
        if (Modules.get().isActive((Class)OriginsModule.class)) {
            packetSender.sendPacket(ModPackets.HANDSHAKE, PacketByteBufs.empty());
        }
    }
}
