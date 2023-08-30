// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.origins;

import fifthcolumn.n.modules.OriginsModule;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import java.util.concurrent.CompletableFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.function.Consumer;
import net.minecraft.class_2540;
import net.minecraft.class_635;
import net.minecraft.class_310;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;

public class TMOPacketsS2C
{
    @Environment(EnvType.CLIENT)
    public static void register() {
        ClientLoginNetworking.registerGlobalReceiver(TMOPackets.HANDSHAKE, TMOPacketsS2C::handleHandshake);
    }
    
    @Environment(EnvType.CLIENT)
    private static CompletableFuture<class_2540> handleHandshake(final class_310 minecraftClient, final class_635 clientLoginNetworkHandler, final class_2540 packetByteBuf, final Consumer<GenericFutureListener<? extends Future<? super Void>>> genericFutureListenerConsumer) {
        if (minecraftClient.method_1496()) {
            return CompletableFuture.completedFuture(PacketByteBufs.empty());
        }
        if (!Modules.get().isActive((Class)OriginsModule.class)) {
            return CompletableFuture.failedFuture(new Throwable("Origins module needs to be enabled and version set"));
        }
        final OriginsModule originsModule = (OriginsModule)Modules.get().get((Class)OriginsModule.class);
        final int[] semVer = originsModule.getSemVer();
        final class_2540 buf = PacketByteBufs.create();
        buf.writeInt(semVer.length);
        for (final int j : semVer) {
            buf.writeInt(j);
        }
        return CompletableFuture.completedFuture(buf);
    }
}
