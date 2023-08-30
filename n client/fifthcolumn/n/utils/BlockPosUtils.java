// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.utils;

import net.minecraft.class_2374;
import net.minecraft.class_2338;
import fifthcolumn.n.copenheimer.CopeService;

public class BlockPosUtils
{
    public static class_2338 from(final CopeService.Position vec) {
        return from(vec.x, vec.y, vec.z);
    }
    
    public static class_2338 from(final class_2374 vec) {
        return from(vec.method_10216(), vec.method_10214(), vec.method_10215());
    }
    
    public static class_2338 from(final double x, final double y, final double z) {
        return new class_2338((int)Math.floor(x), (int)Math.floor(y), (int)Math.floor(z));
    }
    
    public static class_2338 from(final float x, final float y, final float z) {
        return new class_2338((int)Math.floor(x), (int)Math.floor(y), (int)Math.floor(z));
    }
}
