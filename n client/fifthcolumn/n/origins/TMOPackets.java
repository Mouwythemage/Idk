// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.origins;

import net.minecraft.class_2960;

public class TMOPackets
{
    public static final String MODID = "toomanyorigins";
    public static final class_2960 HANDSHAKE;
    
    public static class_2960 identifier(final String path) {
        return new class_2960("toomanyorigins", path);
    }
    
    static {
        HANDSHAKE = identifier("handshake");
    }
}
