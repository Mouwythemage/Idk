// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.client.ui.collar;

import meteordevelopment.meteorclient.MeteorClient;
import fifthcolumn.n.collar.CollarLogin;
import java.util.concurrent.ForkJoinPool;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import java.util.function.Supplier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_757;
import net.minecraft.class_289;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_2561;
import net.minecraft.class_4185;
import net.minecraft.class_342;
import fifthcolumn.n.client.ui.copenheimer.servers.CopeMultiplayerScreen;
import net.minecraft.class_2960;
import net.minecraft.class_437;

public class CollarLoginScreen extends class_437
{
    public static final class_2960 OPTIONS_BACKGROUND_TEXTURE;
    private final CopeMultiplayerScreen multiplayerScreen;
    private final class_437 titleScreen;
    private class_342 collarEmailWidget;
    private PasswordTextFieldWidget collarPasswordWidget;
    private class_4185 doneButton;
    private String errorMessage;
    
    public CollarLoginScreen(final CopeMultiplayerScreen copeMultiplayerScreen, final class_437 titleScreen) {
        super(class_2561.method_30163("Login to collarmc.com"));
        this.multiplayerScreen = copeMultiplayerScreen;
        this.titleScreen = titleScreen;
    }
    
    public void method_25393() {
        super.method_25393();
        if (this.collarEmailWidget.method_25370()) {
            this.collarEmailWidget.method_1865();
        }
        if (this.collarPasswordWidget.method_25370()) {
            this.collarPasswordWidget.method_1865();
        }
    }
    
    protected void method_25426() {
        this.doneButton = (class_4185)this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Login"), button -> this.tryToSaveAndClose()).method_46434(this.field_22789 / 2 - 100, this.field_22790 - 65, 200, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Cancel"), button -> this.method_25419()).method_46434(this.field_22789 / 2 - 100, this.field_22790 - 40, 200, 20).method_46431());
        (this.collarEmailWidget = new class_342(this.field_22793, this.field_22789 / 2 - 100, 116, 200, 20, class_2561.method_30163("email"))).method_1880(128);
        this.collarEmailWidget.method_25365(true);
        this.collarEmailWidget.method_1863(s -> this.onChange());
        this.method_25429((class_364)this.collarEmailWidget);
        (this.collarPasswordWidget = new PasswordTextFieldWidget(this.field_22793, this.field_22789 / 2 - 100, 160, 200, 20, class_2561.method_30163("password"))).method_1880(128);
        this.collarPasswordWidget.method_1863(s -> this.onChange());
        this.method_25429((class_364)this.collarPasswordWidget);
        this.method_48265((class_364)this.collarEmailWidget);
    }
    
    public void method_25394(final class_332 context, final int mouseX, final int mouseY, final float delta) {
        this.method_25420(context);
        context.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, 20, 16777215);
        context.method_27535(this.field_22793, class_2561.method_30163("Email address"), this.field_22789 / 2 - 100, 100, 10526880);
        context.method_27535(this.field_22793, class_2561.method_30163("Password"), this.field_22789 / 2 - 100, 145, 10526880);
        if (this.errorMessage != null) {
            context.method_27535(this.field_22793, class_2561.method_30163(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.errorMessage)), this.field_22789 / 2 - 100, 200, 10526880);
        }
        this.collarEmailWidget.method_25394(context, mouseX, mouseY, delta);
        this.collarPasswordWidget.method_25394(context, mouseX, mouseY, delta);
        super.method_25394(context, mouseX, mouseY, delta);
    }
    
    public void method_25420(final class_332 context) {
        final float vOffset = 0.0f;
        final class_289 tessellator = class_289.method_1348();
        final class_287 bufferBuilder = tessellator.method_1349();
        RenderSystem.setShader((Supplier)class_757::method_34543);
        RenderSystem.setShaderTexture(0, CollarLoginScreen.OPTIONS_BACKGROUND_TEXTURE);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        bufferBuilder.method_1328(class_293.class_5596.field_27382, class_290.field_1575);
        bufferBuilder.method_22912(0.0, (double)this.field_22790, 0.0).method_22913(0.0f, this.field_22790 / 32.0f + vOffset).method_1336(64, 64, 64, 255).method_1344();
        bufferBuilder.method_22912((double)this.field_22789, (double)this.field_22790, 0.0).method_22913(this.field_22789 / 32.0f, this.field_22790 / 32.0f + vOffset).method_1336(64, 64, 64, 255).method_1344();
        bufferBuilder.method_22912((double)this.field_22789, 0.0, 0.0).method_22913(this.field_22789 / 32.0f, vOffset).method_1336(64, 64, 64, 255).method_1344();
        bufferBuilder.method_22912(0.0, 0.0, 0.0).method_22913(0.0f, vOffset).method_1336(64, 64, 64, 255).method_1344();
        tessellator.method_1350();
    }
    
    private void onChange() {
        this.errorMessage = null;
    }
    
    public void method_25419() {
        if (this.field_22787 == null) {
            return;
        }
        this.field_22787.method_1507(this.titleScreen);
    }
    
    private void tryToSaveAndClose() {
        if (this.field_22787 == null) {
            return;
        }
        final class_2561 originalText = this.doneButton.method_25369();
        this.doneButton.field_22763 = false;
        this.doneButton.method_25355(class_2561.method_30163("Logging in..."));
        final CollarLogin.LoginResult result;
        final CollarLogin.LoginResult loginResult;
        final class_2561 class_2561;
        ForkJoinPool.commonPool().submit(() -> {
            result = CollarLogin.loginAndSave(this.collarEmailWidget.method_1882(), this.collarPasswordWidget.method_1882());
            MeteorClient.mc.execute(() -> {
                if (loginResult.success) {
                    this.field_22787.method_1507((class_437)this.multiplayerScreen);
                }
                else {
                    this.errorMessage = loginResult.reason;
                }
                this.doneButton.method_25355(class_2561);
                this.doneButton.field_22763 = true;
            });
        });
    }
    
    static {
        OPTIONS_BACKGROUND_TEXTURE = new class_2960("minecraft:textures/block/tnt_side.png");
    }
}
