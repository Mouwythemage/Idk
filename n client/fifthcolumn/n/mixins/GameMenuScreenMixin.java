// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import net.minecraft.class_641;
import fifthcolumn.n.NMod;
import meteordevelopment.meteorclient.MeteorClient;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.class_642;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import fifthcolumn.n.copenheimer.CopeService;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.class_2561;
import net.minecraft.class_433;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.class_437;

@Mixin({ class_433.class })
public abstract class GameMenuScreenMixin extends class_437
{
    public GameMenuScreenMixin(final class_2561 text) {
        super(text);
    }
    
    @Inject(method = { "initWidgets" }, at = { @At("TAIL") })
    private void n$addCopeBookmark(final CallbackInfo info) {
        final class_642 entry = class_310.method_1551().method_1558();
        if (entry != null) {
            final String serverAddress = CopeService.Server.displayForServerAddress(entry.field_3761);
            this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, serverAddress)), button -> {
                final class_641 serverList = new class_641(MeteorClient.mc);
                serverList.method_2981();
                serverList.method_2988(entry, false);
                serverList.method_2987();
                class_310.method_1551().field_1774.method_1455(entry.field_3761);
            }).method_46434(this.field_22789 / 2 - 102, this.field_22790 / 4 + 144 - 16, 204, 20).method_46431());
        }
    }
    
    public boolean method_25404(final int keyCode, final int scanCode, final int modifiers) {
        if (class_437.method_25438(keyCode)) {
            final class_642 serverEntry = MeteorClient.mc.method_1558();
            if (serverEntry != null) {
                MeteorClient.mc.field_1774.method_1455(serverEntry.field_3761);
                return true;
            }
        }
        return super.method_25404(keyCode, scanCode, modifiers);
    }
    
    @Inject(method = { "disconnect" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V", shift = At.Shift.AFTER, ordinal = 2) }, cancellable = true)
    private void n$changeMultiplayerScreen(final CallbackInfo ci) {
        this.field_22787.method_1507((class_437)NMod.getMultiplayerScreen());
        ci.cancel();
    }
}
