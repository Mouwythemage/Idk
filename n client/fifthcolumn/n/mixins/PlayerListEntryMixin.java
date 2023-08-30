// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.List;
import java.util.function.Function;
import java.util.Objects;
import fifthcolumn.n.client.ProfileCache;
import java.util.function.Consumer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.Optional;
import java.util.Iterator;
import fifthcolumn.n.modules.StreamerMode;
import fifthcolumn.n.copenheimer.CopeService;
import fifthcolumn.n.NMod;
import java.util.UUID;
import meteordevelopment.meteorclient.systems.modules.Modules;
import fifthcolumn.n.modules.LarpModule;
import meteordevelopment.meteorclient.MeteorClient;
import net.minecraft.class_2561;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.jetbrains.annotations.Nullable;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.class_2960;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.Map;
import net.minecraft.class_640;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ class_640.class })
public abstract class PlayerListEntryMixin
{
    @Shadow
    @Final
    private Map<MinecraftProfileTexture.Type, class_2960> field_3742;
    @Shadow
    @Final
    private GameProfile field_3741;
    @Shadow
    @Nullable
    private String field_3745;
    
    @Inject(method = { "getDisplayName" }, at = { @At("RETURN") }, cancellable = true)
    private void n$modifyPlayerDisplayName(final CallbackInfoReturnable<class_2561> cir) {
        if (this.field_3741.getId().equals(MeteorClient.mc.field_1724.method_5667())) {
            final LarpModule larpModule = (LarpModule)Modules.get().get((Class)LarpModule.class);
            if (larpModule.isActive()) {
                final UUID uuid = UUID.fromString((String)larpModule.alias.get());
                NMod.profileCache.findByUUID(uuid).ifPresent(gameProfile -> cir.setReturnValue((Object)class_2561.method_30163(gameProfile.getName())));
            }
        }
        else {
            for (final CopeService.Griefer griefer : NMod.getCopeService().griefers()) {
                if (this.field_3741.getId().equals(griefer.playerId)) {
                    final Optional<GameProfile> profile = NMod.profileCache.findByUUID(griefer.playerId);
                    if (profile.isPresent()) {
                        cir.setReturnValue((Object)class_2561.method_30163(profile.get().getName()));
                        return;
                    }
                    continue;
                }
            }
            if (StreamerMode.isGenerifyNames()) {
                final String fakeName = NMod.genericNames.getName(this.field_3741.getId());
                cir.setReturnValue((Object)class_2561.method_30163(fakeName));
            }
        }
    }
    
    @Redirect(method = { "getSkinTexture" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/PlayerListEntry;loadTextures()V"))
    private void n$modifyPlayerSkinTexture(final class_640 instance) {
        synchronized (this) {
            if (this.field_3741.getId().equals(MeteorClient.mc.field_1724.method_5667())) {
                final LarpModule larpModule = (LarpModule)Modules.get().get((Class)LarpModule.class);
                if (larpModule.isActive()) {
                    final UUID larpUid = UUID.fromString((String)larpModule.alias.get());
                    NMod.profileCache.texture(larpUid).ifPresent(this::setSkinTexture);
                }
                else {
                    NMod.profileCache.texture(this.field_3741.getId()).ifPresent(this::setSkinTexture);
                }
            }
            else {
                final List<CopeService.Griefer> griefers = NMod.getCopeService().griefers();
                if (StreamerMode.isGenerifyNames()) {
                    this.field_3745 = "default";
                }
                for (final CopeService.Griefer griefer : griefers) {
                    if (this.field_3741.getId().equals(griefer.playerId)) {
                        final Optional<GameProfile> playerName = NMod.profileCache.findPlayerName(griefer.playerNameAlias);
                        final ProfileCache profileCache = NMod.profileCache;
                        Objects.requireNonNull(profileCache);
                        playerName.flatMap((Function<? super GameProfile, ? extends Optional<?>>)profileCache::texture).ifPresent((Consumer<? super Object>)this::setSkinTexture);
                    }
                }
            }
        }
    }
    
    private void setSkinTexture(final ProfileCache.TextureResult textureResult) {
        this.field_3742.put(textureResult.type, textureResult.id);
        if (textureResult.type == MinecraftProfileTexture.Type.SKIN) {
            final String modelName = textureResult.texture.getMetadata("model");
            this.field_3745 = ((modelName == null) ? "default" : modelName);
        }
    }
}
