// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.client;

import net.minecraft.class_2960;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.GameProfile;
import java.util.Optional;
import java.io.File;
import net.minecraft.class_310;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_3312;
import java.util.UUID;
import java.util.Map;

public class ProfileCache
{
    private final Map<UUID, TextureResult> textureMap;
    private final class_3312 cache;
    
    public ProfileCache() {
        this.textureMap = new ConcurrentHashMap<UUID, TextureResult>();
        this.cache = new class_3312(new YggdrasilAuthenticationService(Proxy.NO_PROXY).createProfileRepository(), new File(class_310.method_1551().field_1697, "larp-cache.json"));
    }
    
    public Optional<GameProfile> findPlayerName(final String name) {
        return (name == null) ? Optional.empty() : this.cache.method_14515(name);
    }
    
    public Optional<GameProfile> findByUUID(final UUID uuid) {
        return (Optional<GameProfile>)this.cache.method_14512(uuid);
    }
    
    public Optional<TextureResult> texture(final GameProfile larpProfile) {
        return this.texture(larpProfile.getId());
    }
    
    public Optional<TextureResult> texture(final UUID larpUid) {
        if (this.textureMap.containsKey(larpUid)) {
            return Optional.of(this.textureMap.get(larpUid));
        }
        class_310.method_1551().method_1582().method_4652(new GameProfile(larpUid, "a user"), (type, id, texture) -> this.textureMap.put(larpUid, new TextureResult(type, id, texture)), true);
        return Optional.empty();
    }
    
    public static class TextureResult
    {
        public final MinecraftProfileTexture.Type type;
        public final class_2960 id;
        public final MinecraftProfileTexture texture;
        
        public TextureResult(final MinecraftProfileTexture.Type type, final class_2960 id, final MinecraftProfileTexture texture) {
            this.type = type;
            this.id = id;
            this.texture = texture;
        }
    }
}
