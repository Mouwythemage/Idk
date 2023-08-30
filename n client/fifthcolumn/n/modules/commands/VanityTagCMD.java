// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.class_2172;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;

public class VanityTagCMD extends Command
{
    public VanityTagCMD() {
        super("column", "Sends discord link in chat", new String[0]);
    }
    
    public void build(final LiteralArgumentBuilder<class_2172> builder) {
        builder.executes(context -> {
            ChatUtils.sendPlayerMsg("https://discord.gg/thefifthcolumn");
            return 1;
        });
    }
}
