// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import net.minecraft.class_1802;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import fifthcolumn.n.modules.hud.SocialEngineeringHud;
import meteordevelopment.meteorclient.systems.hud.Hud;
import fifthcolumn.n.modules.commands.VelocityTeleportCMD;
import fifthcolumn.n.modules.commands.VanityTagCMD;
import meteordevelopment.meteorclient.commands.Command;
import meteordevelopment.meteorclient.commands.Commands;
import fifthcolumn.n.modules.commands.CopyIPCMD;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.addons.MeteorAddon;

public class NAddOn extends MeteorAddon
{
    public static final Category FIFTH_COLUMN_CATEGORY;
    
    public String getPackage() {
        return "fifthcolumn.n";
    }
    
    public void onInitialize() {
        final Modules modules = Modules.get();
        modules.add((Module)new AutoSign());
        modules.add((Module)new BanEvasion());
        modules.add((Module)new InventoryDupe());
        modules.add((Module)new BetterFlight());
        modules.add((Module)new StreamerMode());
        modules.add((Module)new BuildPoop());
        modules.add((Module)new AutoWither());
        modules.add((Module)new FastProjectile());
        modules.add((Module)new GrieferTracer());
        modules.add((Module)new WaypointSync());
        modules.add((Module)new AutoCutie());
        modules.add((Module)new LecternCrash());
        modules.add((Module)new AutoLava());
        modules.add((Module)new AntiAim());
        modules.add((Module)new Gun());
        modules.add((Module)new ChestStealerAura());
        modules.add((Module)new AutoTranslate());
        modules.add((Module)new OriginsModule());
        modules.add((Module)new SitBypass());
        modules.add((Module)new LarpModule());
        modules.add((Module)new GameModeNotifier());
        Commands.add((Command)new CopyIPCMD());
        Commands.add((Command)new VanityTagCMD());
        Commands.add((Command)new VelocityTeleportCMD());
        Hud.get().register((HudElementInfo)SocialEngineeringHud.INFO);
    }
    
    public void onRegisterCategories() {
        Modules.registerCategory(NAddOn.FIFTH_COLUMN_CATEGORY);
    }
    
    static {
        FIFTH_COLUMN_CATEGORY = new Category("5c", class_1802.field_8626.method_7854());
    }
}
