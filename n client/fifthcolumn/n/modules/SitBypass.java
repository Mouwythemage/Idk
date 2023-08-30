// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import net.minecraft.class_2487;
import net.minecraft.class_1937;
import net.minecraft.class_1297;
import net.minecraft.class_2378;
import net.minecraft.class_4048;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.class_1311;
import net.minecraft.class_7923;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import meteordevelopment.meteorclient.systems.modules.Modules;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.minecraft.class_2540;
import net.minecraft.class_635;
import net.minecraft.class_310;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.class_6344;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import net.minecraft.class_1299;
import net.minecraft.class_2960;
import meteordevelopment.meteorclient.systems.modules.Module;

public class SitBypass extends Module
{
    public static final class_2960 VERSION_CHECK;
    public static final class_1299<EntityImpl> SIT_ENTITY_TYPE;
    private final SettingGroup sgGeneral;
    public final Setting<Integer> versionSetting;
    
    public SitBypass() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "Sit compat", "The SIT plugin");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.versionSetting = (Setting<Integer>)this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("version")).description("version of sit")).defaultValue((Object)20)).max(100).build());
    }
    
    public static void init() {
        EntityRendererRegistry.register((class_1299)SitBypass.SIT_ENTITY_TYPE, class_6344::new);
        ClientLoginNetworking.registerGlobalReceiver(SitBypass.VERSION_CHECK, (client, handler, buf, listenerAdder) -> {
            final SitBypass sit = (SitBypass)Modules.get().get((Class)SitBypass.class);
            final class_2540 responseBuf = PacketByteBufs.create();
            responseBuf.writeInt((int)sit.versionSetting.get());
            return CompletableFuture.completedFuture(responseBuf);
        });
    }
    
    static {
        VERSION_CHECK = new class_2960("sit", "version_check");
        SIT_ENTITY_TYPE = (class_1299)class_2378.method_10230((class_2378)class_7923.field_41177, new class_2960("sit", "entity_sit"), (Object)FabricEntityTypeBuilder.create(class_1311.field_17715, EntityImpl::new).dimensions(class_4048.method_18385(0.001f, 0.001f)).build());
    }
    
    private static class EntityImpl extends class_1297
    {
        public EntityImpl(final class_1299<?> type, final class_1937 world) {
            super((class_1299)type, world);
        }
        
        protected void method_5693() {
        }
        
        protected void method_5749(final class_2487 nbt) {
        }
        
        protected void method_5652(final class_2487 nbt) {
        }
    }
}
