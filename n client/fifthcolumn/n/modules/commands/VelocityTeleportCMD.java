// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules.commands;

import meteordevelopment.meteorclient.systems.modules.player.AntiHunger;
import meteordevelopment.meteorclient.systems.modules.Modules;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.class_746;
import net.minecraft.class_1297;
import net.minecraft.class_2848;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import meteordevelopment.meteorclient.MeteorClient;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.class_2172;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.systems.modules.Module;
import java.util.List;
import meteordevelopment.meteorclient.commands.Command;

public class VelocityTeleportCMD extends Command
{
    public static final List<Module> CONFLICTING_MODULES;
    
    public VelocityTeleportCMD() {
        super("tp", "Jank op-less teleporting", new String[0]);
    }
    
    public void build(final LiteralArgumentBuilder<class_2172> builder) {
        builder.then(argument("x", (ArgumentType)DoubleArgumentType.doubleArg()).then(argument("y", (ArgumentType)DoubleArgumentType.doubleArg()).then(argument("z", (ArgumentType)DoubleArgumentType.doubleArg()).executes(this::runTeleport))));
    }
    
    private int runTeleport(final CommandContext<class_2172> context) {
        assert MeteorClient.mc.field_1724 != null;
        final double x = (double)context.getArgument("x", (Class)Double.class);
        final double y = (double)context.getArgument("y", (Class)Double.class);
        final double z = (double)context.getArgument("z", (Class)Double.class);
        System.out.println(invokedynamic(makeConcatWithConstants:(DDD)Ljava/lang/String;, x, y, z));
        final class_746 player = MeteorClient.mc.field_1724;
        final double selfX = player.method_23317();
        final double selfY = player.method_23318();
        final double selfZ = player.method_23321();
        final double distance = Math.pow(Math.pow(x - selfX, 2.0) + Math.pow(y - selfY, 2.0) + Math.pow(z - selfZ, 2.0), 0.5);
        final double packetsNeeded = Math.ceil(distance / 0.038 - 1.54);
        final float yaw = (float)(Math.atan2(x - selfX, z - selfZ) * 57.29577951308232);
        final float pitch = (float)Math.asin((y - selfY) / distance);
        System.out.println(invokedynamic(makeConcatWithConstants:(DD)Ljava/lang/String;, distance, packetsNeeded));
        final List<Module> modules = disengageConflictingModules();
        player.field_3944.method_2883((class_2596)new class_2828.class_2831(yaw, pitch, true));
        player.field_3944.method_2883((class_2596)new class_2848((class_1297)player, class_2848.class_2849.field_12981));
        for (double i = 0.0; i < packetsNeeded; ++i) {
            player.field_3944.method_2883((class_2596)new class_2828.class_2829(selfX, selfY - 1.0E-9, selfZ, true));
            player.field_3944.method_2883((class_2596)new class_2828.class_2829(selfX, selfY + 1.0E-9, selfZ, false));
        }
        player.field_3944.method_2883((class_2596)new class_2828.class_2829(x, y, z, false));
        reengageModules(modules);
        return 1;
    }
    
    public static List<Module> disengageConflictingModules() {
        final List<Module> modules = VelocityTeleportCMD.CONFLICTING_MODULES.stream().filter(Module::isActive).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        modules.forEach(Module::toggle);
        return modules;
    }
    
    public static void reengageModules(final List<Module> modules) {
        modules.forEach(Module::toggle);
    }
    
    private static Module getModule(final Class<? extends Module> module) {
        return Modules.get().get((Class)module);
    }
    
    static {
        CONFLICTING_MODULES = List.of(getModule((Class<? extends Module>)AntiHunger.class));
    }
}
