// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import java.util.function.Function;
import java.util.UUID;
import net.minecraft.class_1657;
import org.jetbrains.annotations.Nullable;
import com.mojang.authlib.GameProfile;
import java.util.Optional;
import java.util.Iterator;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.MeteorClient;
import org.apache.commons.lang3.StringUtils;
import fifthcolumn.n.copenheimer.CopeService;
import fifthcolumn.n.NMod;
import meteordevelopment.meteorclient.settings.StringSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class LarpModule extends Module
{
    private final SettingGroup sgGeneral;
    public final Setting<String> alias;
    public final Setting<String> aliasName;
    
    public LarpModule() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "Larping", "Make all griefers larp as another player");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.alias = (Setting<String>)this.sgGeneral.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)new StringSetting.Builder().name("player uid")).description("player uuid to larp as")).defaultValue((Object)"24f7eb09-ad9e-4e09-b58b-2b59259f171d")).build());
        this.aliasName = (Setting<String>)this.sgGeneral.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)new StringSetting.Builder().name("player name")).description("player name to larp as")).defaultValue((Object)"Joey_Coconut")).build());
    }
    
    @Nullable
    public static String modifyPlayerNameInstances(String text) {
        for (final CopeService.Griefer entity : NMod.getCopeService().griefers()) {
            if (entity.playerNameAlias != null) {
                final Optional<GameProfile> profile = NMod.profileCache.findPlayerName(entity.playerNameAlias);
                if (!profile.isPresent()) {
                    continue;
                }
                text = StringUtils.replace(text, entity.playerName, entity.playerNameAlias);
            }
        }
        if (MeteorClient.mc != null && MeteorClient.mc.field_1724 != null) {
            final LarpModule larpModule = (LarpModule)Modules.get().get((Class)LarpModule.class);
            if (larpModule.isActive()) {
                final String aliasName = (String)larpModule.aliasName.get();
                text = StringUtils.replace(text, MeteorClient.mc.field_1724.method_5820(), aliasName);
            }
        }
        return text;
    }
    
    public static Optional<String> getPlayerEntityName(final class_1657 player) {
        if (MeteorClient.mc.field_1724 != null && player.method_7334().getId().equals(MeteorClient.mc.field_1724.method_5667())) {
            final LarpModule larpModule = (LarpModule)Modules.get().get((Class)LarpModule.class);
            if (larpModule.isActive()) {
                final UUID uuid = UUID.fromString((String)larpModule.alias.get());
                final Optional<GameProfile> profile = NMod.profileCache.findByUUID(uuid);
                if (profile.isPresent()) {
                    return profile.map((Function<? super GameProfile, ? extends String>)GameProfile::getName);
                }
            }
        }
        else {
            for (final CopeService.Griefer griefer : NMod.getCopeService().griefers()) {
                if (player.method_7334().getId().equals(griefer.playerId)) {
                    final Optional<GameProfile> profile = NMod.profileCache.findByUUID(griefer.playerId);
                    if (profile.isPresent()) {
                        return profile.map((Function<? super GameProfile, ? extends String>)GameProfile::getName);
                    }
                    continue;
                }
            }
        }
        return Optional.empty();
    }
}
