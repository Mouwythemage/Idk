// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.client;

import fifthcolumn.n.modules.SitBypass;
import fifthcolumn.n.origins.TMOPacketsC2S;
import fifthcolumn.n.origins.TMOPacketsS2C;
import fifthcolumn.n.origins.ModPacketsS2C;
import fifthcolumn.n.origins.ModPacketsC2S;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ClientModInitializer;

@Environment(EnvType.CLIENT)
public class NClient implements ClientModInitializer
{
    public void onInitializeClient() {
        ModPacketsC2S.register();
        ModPacketsS2C.register();
        TMOPacketsS2C.register();
        TMOPacketsC2S.register();
        SitBypass.init();
    }
}
