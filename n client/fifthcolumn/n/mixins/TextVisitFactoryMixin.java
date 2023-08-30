// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import fifthcolumn.n.modules.StreamerMode;
import fifthcolumn.n.modules.LarpModule;
import net.minecraft.class_5223;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ class_5223.class })
public class TextVisitFactoryMixin
{
    @ModifyVariable(method = { "visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z" }, at = @At("HEAD"), ordinal = 0, index = 0, argsOnly = true)
    private static String n$modifyAllPlayerNameInstances(String text) {
        text = LarpModule.modifyPlayerNameInstances(text);
        text = StreamerMode.anonymizePlayerNameInstances(text);
        return text;
    }
}
