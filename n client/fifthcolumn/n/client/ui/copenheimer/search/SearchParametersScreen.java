// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.client.ui.copenheimer.search;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import net.minecraft.class_339;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import java.util.function.Supplier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_757;
import net.minecraft.class_289;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_2561;
import fifthcolumn.n.copenheimer.CopeService;
import net.minecraft.class_4286;
import net.minecraft.class_342;
import fifthcolumn.n.client.ui.copenheimer.servers.CopeMultiplayerScreen;
import net.minecraft.class_2960;
import net.minecraft.class_437;

public class SearchParametersScreen extends class_437
{
    public static final class_2960 OPTIONS_BACKGROUND_TEXTURE;
    private final CopeMultiplayerScreen parent;
    private class_342 serverNameWidget;
    private class_342 serverVersionWidget;
    private class_342 serverLangWidget;
    private class_4286 onlineWidget;
    private class_4286 crackedWidget;
    private final CopeService copeService;
    
    public SearchParametersScreen(final CopeMultiplayerScreen parent, final CopeService copeService) {
        super(class_2561.method_30163("Search Parameters"));
        this.parent = parent;
        this.copeService = copeService;
    }
    
    public void method_25393() {
        super.method_25393();
        this.serverNameWidget.method_1865();
        this.serverVersionWidget.method_1865();
        this.serverLangWidget.method_1865();
    }
    
    protected void method_25426() {
        this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Search"), button -> this.saveAndClose()).method_46434(this.field_22789 / 2 - 100, this.field_22790 - 65, 200, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Cancel"), button -> this.method_25419()).method_46434(this.field_22789 / 2 - 100, this.field_22790 - 40, 200, 20).method_46431());
        final CopeService.FindServersRequest currentFindRequest = this.copeService.currentFindRequest;
        (this.serverNameWidget = new class_342(this.field_22793, this.field_22789 / 2 - 100, 116, 200, 20, class_2561.method_30163("Name like"))).method_1880(128);
        this.serverNameWidget.method_1852(currentFindRequest.hasName);
        this.serverNameWidget.method_1863(text -> this.onChange());
        this.method_25429((class_364)this.serverNameWidget);
        (this.serverLangWidget = new class_342(this.field_22793, this.field_22789 / 2 - 100, 160, 200, 20, class_2561.method_30163("Server lang"))).method_1880(2);
        this.serverLangWidget.method_1852(currentFindRequest.lang);
        this.serverLangWidget.method_1863(text -> this.onChange());
        this.method_25429((class_364)this.serverLangWidget);
        (this.serverVersionWidget = new class_342(this.field_22793, this.field_22789 / 2 - 100, 204, 200, 20, this.serverVersionWidget, class_2561.method_30163("Version like"))).method_1880(128);
        this.serverVersionWidget.method_1852(currentFindRequest.hasVersion);
        this.serverVersionWidget.method_1863(text -> this.onChange());
        this.method_25429((class_364)this.serverVersionWidget);
        this.method_25429((class_364)(this.onlineWidget = new class_4286(this.field_22789 / 2 - 100, 248, 200, 20, class_2561.method_30163("Players online"), checkboxState(currentFindRequest.playersOnline))));
        this.method_25429((class_364)(this.crackedWidget = new class_4286(this.field_22789 / 2 - 100, 292, 200, 20, class_2561.method_30163("Cracked"), checkboxState(currentFindRequest.isCracked))));
        this.method_48265((class_364)this.serverVersionWidget);
        this.onChange();
    }
    
    public void method_25394(final class_332 context, final int mouseX, final int mouseY, final float delta) {
        this.method_25420(context);
        context.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, 20, 16777215);
        context.method_27535(this.field_22793, class_2561.method_30163("Name"), this.field_22789 / 2 - 100, 100, 10526880);
        context.method_27535(this.field_22793, class_2561.method_30163("Language (en, ja, es, etc)"), this.field_22789 / 2 - 100, 145, 10526880);
        context.method_27535(this.field_22793, class_2561.method_30163("Version"), this.field_22789 / 2 - 100, 190, 10526880);
        this.serverNameWidget.method_25394(context, mouseX, mouseY, delta);
        this.serverVersionWidget.method_25394(context, mouseX, mouseY, delta);
        this.serverLangWidget.method_25394(context, mouseX, mouseY, delta);
        this.onlineWidget.method_25394(context, mouseX, mouseY, delta);
        this.crackedWidget.method_25394(context, mouseX, mouseY, delta);
        super.method_25394(context, mouseX, mouseY, delta);
    }
    
    public void method_25420(final class_332 context) {
        final float vOffset = 0.0f;
        final class_289 tessellator = class_289.method_1348();
        final class_287 bufferBuilder = tessellator.method_1349();
        RenderSystem.setShader((Supplier)class_757::method_34543);
        RenderSystem.setShaderTexture(0, SearchParametersScreen.OPTIONS_BACKGROUND_TEXTURE);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        final float f = 32.0f;
        bufferBuilder.method_1328(class_293.class_5596.field_27382, class_290.field_1575);
        bufferBuilder.method_22912(0.0, (double)this.field_22790, 0.0).method_22913(0.0f, this.field_22790 / 32.0f + vOffset).method_1336(64, 64, 64, 255).method_1344();
        bufferBuilder.method_22912((double)this.field_22789, (double)this.field_22790, 0.0).method_22913(this.field_22789 / 32.0f, this.field_22790 / 32.0f + vOffset).method_1336(64, 64, 64, 255).method_1344();
        bufferBuilder.method_22912((double)this.field_22789, 0.0, 0.0).method_22913(this.field_22789 / 32.0f, vOffset).method_1336(64, 64, 64, 255).method_1344();
        bufferBuilder.method_22912(0.0, 0.0, 0.0).method_22913(0.0f, vOffset).method_1336(64, 64, 64, 255).method_1344();
        tessellator.method_1350();
    }
    
    public boolean method_25404(final int keyCode, final int scanCode, final int modifiers) {
        final boolean focusedKeyPress = this.method_25399() != null && this.method_25399().method_25404(keyCode, scanCode, modifiers);
        return super.method_25404(keyCode, scanCode, modifiers) || focusedKeyPress;
    }
    
    public boolean method_25400(final char chr, final int modifiers) {
        final class_364 focused = this.method_25399();
        return focused != null && focused.method_25400(chr, modifiers);
    }
    
    public boolean method_25402(final double mouseX, final double mouseY, final int button) {
        if (this.clickedWidget(mouseX, mouseY, (class_339)this.serverNameWidget)) {
            this.serverVersionWidget.method_25365(false);
            this.serverLangWidget.method_25365(false);
            this.method_25395((class_364)this.serverNameWidget);
        }
        else if (this.clickedWidget(mouseX, mouseY, (class_339)this.serverVersionWidget)) {
            this.serverNameWidget.method_25365(false);
            this.serverLangWidget.method_25365(false);
            this.method_25395((class_364)this.serverVersionWidget);
        }
        else if (this.clickedWidget(mouseX, mouseY, (class_339)this.serverLangWidget)) {
            this.serverVersionWidget.method_25365(false);
            this.serverNameWidget.method_25365(false);
            this.method_25395((class_364)this.serverLangWidget);
        }
        return super.method_25402(mouseX, mouseY, button);
    }
    
    private boolean clickedWidget(final double mouseX, final double mouseY, final class_339 widget) {
        return mouseX >= widget.method_46426() && mouseY >= widget.method_46427() && mouseX < widget.method_46426() + widget.method_25368() && mouseY < widget.method_46427() + widget.method_25364();
    }
    
    public void method_25419() {
        if (this.field_22787 == null) {
            return;
        }
        this.field_22787.method_1507((class_437)this.parent);
    }
    
    private void saveAndClose() {
        if (this.field_22787 == null) {
            return;
        }
        this.onChange();
        this.copeService.currentFindRequest.skip = 0;
        this.parent.refreshList();
        this.field_22787.method_1507((class_437)this.parent);
    }
    
    private void onChange() {
        this.copeService.currentFindRequest.hasName = StringUtils.trimToNull(this.serverNameWidget.method_1882());
        this.copeService.currentFindRequest.hasVersion = StringUtils.trimToEmpty(this.serverVersionWidget.method_1882());
        this.copeService.currentFindRequest.playersOnline = this.onlineWidget.method_20372();
        this.copeService.currentFindRequest.isCracked = this.crackedWidget.method_20372();
        this.copeService.currentFindRequest.isWhitelisted = false;
        this.copeService.currentFindRequest.isModded = false;
        this.copeService.currentFindRequest.isProtected = false;
        if (!Objects.equals(this.serverLangWidget.method_1882(), "")) {
            this.copeService.currentFindRequest.lang = this.serverLangWidget.method_1882();
        }
        else {
            this.copeService.currentFindRequest.lang = null;
        }
    }
    
    private static boolean checkboxState(final Boolean property) {
        return property != null && property;
    }
    
    static {
        OPTIONS_BACKGROUND_TEXTURE = new class_2960("minecraft:textures/block/tnt_side.png");
    }
}
