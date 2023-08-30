// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.events;

import fifthcolumn.n.copenheimer.CopeService;
import java.util.List;

public class GrieferUpdateEvent
{
    public final List<CopeService.Griefer> griefers;
    
    public GrieferUpdateEvent(final List<CopeService.Griefer> griefers) {
        this.griefers = griefers;
    }
}
