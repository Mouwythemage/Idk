// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n;

import net.minecraft.class_4617;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.class_634;
import net.minecraft.class_642;
import meteordevelopment.meteorclient.MeteorClient;
import net.minecraft.class_437;
import net.minecraft.class_2378;
import net.minecraft.class_7923;
import fifthcolumn.n.collar.CollarLogin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.class_310;
import fifthcolumn.n.client.ui.copenheimer.servers.CopeMultiplayerScreen;
import fifthcolumn.n.client.ProfileCache;
import net.minecraft.class_3414;
import net.minecraft.class_2960;
import fifthcolumn.n.copenheimer.CopeService;
import java.util.regex.Pattern;
import net.fabricmc.api.ModInitializer;

public class NMod implements ModInitializer
{
    private static final Pattern STRIP_PATTERN;
    private static NMod INSTANCE;
    public static final CopeService copeService;
    public static final class_2960 CAPE_TEXTURE;
    public static final class_2960 cockSound;
    public static final class_2960 shotgunSound;
    public static class_3414 shotgunSoundEvent;
    public static class_3414 cockSoundEvent;
    public static ProfileCache profileCache;
    public static GenericNames genericNames;
    private CopeMultiplayerScreen multiplayerScreen;
    
    public void onInitialize() {
        final class_310 mc = class_310.method_1551();
        NMod.INSTANCE = new NMod();
        ClientPlayConnectionEvents.JOIN.register((Object)((handler, sender, client) -> {
            NMod.copeService.clearTranslations();
            NMod.copeService.startUpdating();
            NMod.copeService.setLastServerInfo(mc.method_1558());
        }));
        ClientPlayConnectionEvents.DISCONNECT.register((Object)((handler, client) -> {
            NMod.copeService.clearTranslations();
            NMod.copeService.stopUpdating();
            NMod.copeService.setDefaultSession();
            NMod.genericNames.clear();
        }));
        NMod.copeService.setDefaultSession(mc.method_1548());
        CollarLogin.refreshSession();
        class_2378.method_10230(class_7923.field_41172, NMod.shotgunSound, (Object)NMod.shotgunSoundEvent);
        class_2378.method_10230(class_7923.field_41172, NMod.cockSound, (Object)NMod.cockSoundEvent);
    }
    
    public static CopeService getCopeService() {
        return NMod.copeService;
    }
    
    public static CopeMultiplayerScreen getMultiplayerScreen() {
        return NMod.INSTANCE.multiplayerScreen;
    }
    
    public static CopeMultiplayerScreen getOrCreateMultiplayerScreen(final class_437 parent) {
        if (NMod.INSTANCE.multiplayerScreen == null) {
            NMod.INSTANCE.multiplayerScreen = new CopeMultiplayerScreen(parent, NMod.copeService);
        }
        return NMod.INSTANCE.multiplayerScreen;
    }
    
    public static void setMultiplayerScreen(final CopeMultiplayerScreen multiplayerScreen) {
        NMod.INSTANCE.multiplayerScreen = multiplayerScreen;
    }
    
    public static boolean is2b2t() {
        final class_642 serverEntry = MeteorClient.mc.method_1558();
        return serverEntry != null && serverEntry.field_3761.contains("2b2t.org");
    }
    
    static {
        STRIP_PATTERN = Pattern.compile("(?<!<@)[&§](?i)[0-9a-fklmnorx]");
        copeService = new CopeService();
        CAPE_TEXTURE = new class_2960("nc:cape.png");
        cockSound = new class_2960("nc:cock");
        shotgunSound = new class_2960("nc:shot");
        NMod.shotgunSoundEvent = class_3414.method_47908(NMod.shotgunSound);
        NMod.cockSoundEvent = class_3414.method_47908(NMod.cockSound);
        NMod.profileCache = new ProfileCache();
        NMod.genericNames = new GenericNames();
    }
    
    public static class GenericNames
    {
        private final Map<UUID, String> names;
        
        public GenericNames() {
            this.names = new HashMap<UUID, String>();
        }
        
        public String getName(final UUID uuid) {
            this.names.computeIfAbsent(uuid, k -> class_4617.method_23267(uuid));
            return this.names.get(uuid);
        }
        
        public void clear() {
            this.names.clear();
        }
    }
}
