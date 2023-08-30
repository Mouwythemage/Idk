// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import net.minecraft.class_412;
import net.minecraft.class_639;
import fifthcolumn.n.client.ui.copenheimer.servers.CopeMultiplayerScreen;
import net.minecraft.class_442;
import net.minecraft.class_320;
import fifthcolumn.n.copenheimer.CopeService;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.class_8021;
import fifthcolumn.n.modules.BanEvasion;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.class_7845;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.class_2561;
import org.spongepowered.asm.mixin.Unique;
import net.minecraft.class_4185;
import net.minecraft.class_419;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.class_437;

@Mixin({ class_419.class })
public abstract class DisconnectedScreenMixin extends class_437
{
    @Unique
    private class_4185 switchAltButton;
    
    protected DisconnectedScreenMixin(final class_2561 title) {
        super(title);
    }
    
    @Inject(method = { "init" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/GridWidget;refreshPositions()V") }, locals = LocalCapture.CAPTURE_FAILHARD)
    private void n$banEvasion(final CallbackInfo ci, final class_7845.class_7939 adder, final class_4185 buttonWidget) {
        if (Modules.get().isActive((Class)BanEvasion.class) && (boolean)((BanEvasion)Modules.get().get((Class)BanEvasion.class)).evadeAndReconnect.get()) {
            final class_4185 banEvadeButton = class_4185.method_46430(class_2561.method_30163("Evade ban and reconnect"), button -> {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: getfield        fifthcolumn/n/mixins/DisconnectedScreenMixin.switchAltButton:Lnet/minecraft/class_4185;
                //     4: iconst_0       
                //     5: putfield        net/minecraft/class_4185.field_22763:Z
                //     8: invokestatic    fifthcolumn/n/NMod.getCopeService:()Lfifthcolumn/n/copenheimer/CopeService;
                //    11: astore_2        /* copeService */
                //    12: aload_2         /* copeService */
                //    13: aload_0         /* this */
                //    14: aload_2         /* copeService */
                //    15: invokedynamic   BootstrapMethod #1, accept:(Lfifthcolumn/n/mixins/DisconnectedScreenMixin;Lfifthcolumn/n/copenheimer/CopeService;)Ljava/util/function/Consumer;
                //    20: invokevirtual   fifthcolumn/n/copenheimer/CopeService.useNewAlternateAccount:(Ljava/util/function/Consumer;)V
                //    23: return         
                //    MethodParameters:
                //  Name    Flags  
                //  ------  -----
                //  button  
                // 
                // The error that occurred was:
                // 
                // java.lang.IllegalStateException: Could not infer any expression.
                //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
                //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
                //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
                //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
                //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
                //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }).method_46431();
            adder.method_47612((class_8021)banEvadeButton);
        }
    }
}
