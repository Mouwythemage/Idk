// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.class_642;
import meteordevelopment.meteorclient.MeteorClient;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.class_2172;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;

public class CopyIPCMD extends Command
{
    public CopyIPCMD() {
        super("ip", "Copies the current server IP to clipboard", new String[0]);
    }
    
    public void build(final LiteralArgumentBuilder<class_2172> builder) {
        builder.executes(context -> {
            final class_642 serverEntry = MeteorClient.mc.method_1558();
            MeteorClient.mc.field_1774.method_1455(serverEntry.field_3761);
            this.info("Copied server IP to clipboard!", new Object[0]);
            return 1;
        });
    }
}
