// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.client.ui.collar;

import org.jetbrains.annotations.Nullable;
import net.minecraft.class_5481;
import net.minecraft.class_2583;
import net.minecraft.class_2561;
import net.minecraft.class_327;
import net.minecraft.class_342;

public class PasswordTextFieldWidget extends class_342
{
    public PasswordTextFieldWidget(final class_327 textRenderer, final int x, final int y, final int width, final int height, final class_2561 text) {
        super(textRenderer, x, y, width, height, text);
        this.method_1854((string, integer) -> class_5481.method_30747("*".repeat(string.length()), class_2583.field_24360));
    }
    
    public PasswordTextFieldWidget(final class_327 textRenderer, final int x, final int y, final int width, final int height, @Nullable final class_342 copyFrom, final class_2561 text) {
        super(textRenderer, x, y, width, height, copyFrom, text);
    }
}
