// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.class_332;
import net.minecraft.class_2960;
import net.minecraft.class_8020;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ class_8020.class })
public class LogoDrawerMixin
{
    private static final class_2960 N_LOGO;
    
    @Redirect(method = { "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIFFIIII)V", ordinal = 0))
    public void redirectDrawLogo(final class_332 instance, final class_2960 texture, final int x, final int y, final float u, final float v, final int width, final int height, final int textureWidth, final int textureHeight) {
        instance.method_25290(LogoDrawerMixin.N_LOGO, x, y, u, v, width, 250, textureWidth, textureHeight);
    }
    
    static {
        N_LOGO = new class_2960("nc:title.png");
    }
}
