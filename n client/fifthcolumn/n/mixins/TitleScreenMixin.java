// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import org.slf4j.LoggerFactory;
import fifthcolumn.n.client.ui.copenheimer.servers.CopeMultiplayerScreen;
import meteordevelopment.meteorclient.MeteorClient;
import fifthcolumn.n.client.ui.collar.CollarLoginScreen;
import fifthcolumn.n.collar.CollarLogin;
import java.util.concurrent.ForkJoinPool;
import fifthcolumn.n.NMod;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.class_8519;
import org.slf4j.Logger;
import net.minecraft.class_442;
import org.spongepowered.asm.mixin.Mixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_437;

@Environment(EnvType.CLIENT)
@Mixin(value = { class_442.class }, priority = 1001)
public abstract class TitleScreenMixin extends class_437
{
    private static final Logger LOGGER;
    private static final int BG_AMT = 25;
    @Shadow
    @Nullable
    private class_8519 field_2586;
    private final class_2960 backgroundId;
    
    protected TitleScreenMixin(final class_2561 title) {
        super(title);
        this.backgroundId = new class_2960(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, ThreadLocalRandom.current().nextInt(1, 26)));
    }
    
    @Inject(method = { "init" }, at = { @At("HEAD") })
    private void n$modifySplashText(final CallbackInfo ci) {
        if (this.field_2586 == null) {
            this.field_2586 = new class_8519("Grief. Cope. Seethe. Repeat.");
        }
    }
    
    @Inject(method = { "initWidgetsNormal" }, at = { @At("TAIL") })
    private void n$addCopenheimerButton(final int y, final int spacingY, final CallbackInfo ci) {
        this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Copenheimer"), button -> {
            final CopeMultiplayerScreen multiplayerScreen = NMod.getOrCreateMultiplayerScreen(this);
            final class_2561 originalTitle;
            final CopeMultiplayerScreen copeMultiplayerScreen;
            class_437 class_437 = null;
            final class_437 screen;
            final class_2561 class_438;
            final class_437 class_439;
            ForkJoinPool.commonPool().submit(() -> {
                originalTitle = button.method_25369();
                button.field_22763 = false;
                button.method_25355(class_2561.method_30163("Logging in..."));
                if (CollarLogin.refreshSession()) {
                    class_437 = copeMultiplayerScreen;
                }
                else {
                    // new(fifthcolumn.n.client.ui.collar.CollarLoginScreen.class)
                    new CollarLoginScreen(copeMultiplayerScreen, this);
                }
                screen = class_437;
                MeteorClient.mc.execute(() -> {
                    try {
                        button.field_22763 = true;
                        button.method_25355(class_438);
                        this.field_22787.method_1507(class_439);
                    }
                    catch (Exception e) {
                        TitleScreenMixin.LOGGER.error("Could not set screen", (Throwable)e);
                    }
                });
            });
        }).method_46432(200).method_46434(this.field_22789 / 2 - 100, y + spacingY * 5, 200, 20).method_46431());
    }
    
    @Redirect(method = { "render" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/TitleScreen;PANORAMA_OVERLAY:Lnet/minecraft/util/Identifier;"))
    private class_2960 n$modifyPanoramaOverlay() {
        return this.backgroundId;
    }
    
    static {
        LOGGER = LoggerFactory.getLogger((Class)TitleScreenMixin.class);
    }
}
