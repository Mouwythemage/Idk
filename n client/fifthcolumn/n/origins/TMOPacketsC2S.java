// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.origins;

import net.minecraft.class_2540;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.class_3248;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;

public class TMOPacketsC2S
{
    public static void register() {
        ServerLoginConnectionEvents.QUERY_START.register((Object)TMOPacketsC2S::handshake);
        ServerLoginNetworking.registerGlobalReceiver(TMOPackets.HANDSHAKE, (server, handler, understood, buf, synchronizer, responseSender) -> {});
    }
    
    private static void handshake(final class_3248 serverLoginNetworkHandler, final MinecraftServer minecraftServer, final PacketSender packetSender, final ServerLoginNetworking.LoginSynchronizer loginSynchronizer) {
        packetSender.sendPacket(TMOPackets.HANDSHAKE, PacketByteBufs.empty());
    }
}
