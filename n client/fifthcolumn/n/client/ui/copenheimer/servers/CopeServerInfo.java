// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.client.ui.copenheimer.servers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import net.minecraft.class_2561;
import fifthcolumn.n.copenheimer.CopeService;
import net.minecraft.class_2960;
import net.minecraft.class_642;

public final class CopeServerInfo extends class_642
{
    public static final class_2960 TNT_BLOCK_TEXTURE;
    private final CopeService.Server server;
    
    public CopeServerInfo(final String name, final CopeService.Server server) {
        super(name, server.serverAddress, false);
        this.server = server;
        this.field_3754 = true;
        this.field_3753 = class_2561.method_30163("Updating...");
        this.field_3762 = new ArrayList();
        this.field_3757 = class_2561.method_30163(server.displayDescription());
        server.iconData().ifPresent(s -> this.method_49305(this.toString().getBytes(StandardCharsets.UTF_8)));
    }
    
    public boolean isGriefing() {
        return !this.server.griefers.isEmpty();
    }
    
    static {
        TNT_BLOCK_TEXTURE = new class_2960("minecraft:textures/block/tnt_side.png");
    }
}
