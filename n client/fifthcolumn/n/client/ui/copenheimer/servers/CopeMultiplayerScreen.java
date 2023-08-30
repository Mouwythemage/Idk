// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.client.ui.copenheimer.servers;

import fifthcolumn.n.client.ui.copenheimer.search.SearchParametersScreen;
import meteordevelopment.meteorclient.gui.GuiThemes;
import net.minecraft.class_320;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.function.Consumer;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import net.minecraft.class_412;
import net.minecraft.class_639;
import net.minecraft.class_642;
import meteordevelopment.meteorclient.MeteorClient;
import fifthcolumn.n.modules.StreamerMode;
import net.minecraft.class_332;
import java.time.Instant;
import fifthcolumn.n.NMod;
import net.minecraft.class_364;
import net.minecraft.class_6370;
import net.minecraft.class_2561;
import net.minecraft.class_4185;
import fifthcolumn.n.copenheimer.CopeService;
import net.minecraft.class_644;
import net.minecraft.class_437;

public class CopeMultiplayerScreen extends class_437
{
    private static final int BUTTON_WIDTH_TOP = 80;
    private static final int BUTTON_WIDTH_BOTTOM = 80;
    private static final int BUTTON_HEIGHT = 20;
    private final class_644 serverListPinger;
    private final class_437 parent;
    private final CopeService copeService;
    protected CopeServerListWidget serverListWidget;
    private ServerList serverList;
    private class_4185 buttonModded;
    private class_4185 buttonJoin;
    private class_4185 protectedButton;
    private class_4185 whiteListedButton;
    private class_4185 griefedButton;
    private class_4185 newAltButton;
    private boolean initialized;
    
    public CopeMultiplayerScreen(final class_437 screen, final CopeService copeService) {
        super(class_2561.method_30163("Copenheimer"));
        this.serverListPinger = new class_644();
        this.parent = screen;
        this.copeService = copeService;
    }
    
    protected void method_25426() {
        super.method_25426();
        if (this.field_22787 == null) {
            return;
        }
        if (this.initialized) {
            this.serverListWidget.method_25323(this.field_22789, this.field_22790, 32, this.field_22790 - 64);
        }
        else {
            this.initialized = true;
            this.serverList = new ServerList();
            (this.serverListWidget = new CopeServerListWidget(this, this.field_22787, this.field_22789, this.field_22790, 32, this.field_22790 - 64, 36)).setServers(this.serverList);
            this.refreshList();
            final class_6370 field_33745 = class_6370.field_33745;
        }
        final int buttonTopRowY = this.field_22790 - 52;
        final int buttonBottomRowY = this.field_22790 - 28;
        final int padding = 4;
        final int farLeftXTopRow = this.field_22789 / 2 - 210;
        final int farLeftXBottomRow = this.field_22789 / 2 - 210;
        this.method_25429((class_364)this.serverListWidget);
        this.buttonJoin = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471("selectServer.select"), button -> this.connect()).method_46434(farLeftXTopRow, buttonTopRowY, 80, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Update search"), button -> this.field_22787.method_1507((class_437)new SearchParametersScreen((CopeMultiplayerScreen)this.field_22787.field_1755, this.copeService))).method_46434(farLeftXTopRow + 4 + 80, buttonTopRowY, 80, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Hide Server"), button -> this.serverListWidget.removeSelectedServerEntry()).method_46434(farLeftXTopRow + 168, buttonTopRowY, 80, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Show More"), button -> {
            if (!this.copeService.loading.get()) {
                this.showMore();
            }
        }).method_46434(farLeftXTopRow + 252, buttonTopRowY, 80, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Refresh"), button -> {
            if (!this.copeService.loading.get()) {
                this.refreshList();
            }
        }).method_46434(farLeftXTopRow + 336, buttonTopRowY, 80, 20).method_46431());
        this.protectedButton = (class_4185)this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Protected"), button -> {
            final CopeService.UpdateServerRequest req = new CopeService.UpdateServerRequest();
            req.isProtected = true;
            this.serverListWidget.removeSelectedServerEntry();
            this.updateServer(req);
        }).method_46434(farLeftXBottomRow, buttonBottomRowY, 80, 20).method_46431());
        this.whiteListedButton = (class_4185)this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Whitelisted"), button -> {
            final CopeService.UpdateServerRequest req = new CopeService.UpdateServerRequest();
            req.isWhitelisted = true;
            this.serverListWidget.removeSelectedServerEntry();
            this.updateServer(req);
        }).method_46434(farLeftXBottomRow + 4 + 80, buttonBottomRowY, 80, 20).method_46431());
        this.method_37063((class_364)(this.buttonModded = class_4185.method_46430((class_2561)class_2561.method_43471("Modded"), button -> {
            final CopeService.UpdateServerRequest req = new CopeService.UpdateServerRequest();
            req.isModded = true;
            this.serverListWidget.removeSelectedServerEntry();
            this.updateServer(req);
        }).method_46434(farLeftXBottomRow + 168, buttonBottomRowY, 80, 20).method_46431()));
        this.method_37063((class_364)(this.griefedButton = class_4185.method_46430((class_2561)class_2561.method_43471("Griefed"), button -> {
            final CopeService.UpdateServerRequest req = new CopeService.UpdateServerRequest();
            req.isGriefed = true;
            this.serverListWidget.removeSelectedServerEntry();
            this.updateServer(req);
        }).method_46434(farLeftXBottomRow + 252, buttonBottomRowY, 80, 20).method_46431()));
        this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Cancel"), button -> {
            NMod.setMultiplayerScreen(null);
            this.field_22787.method_1507(this.parent);
        }).method_46434(farLeftXBottomRow + 336, buttonBottomRowY, 80, 20).method_46431());
        this.updateButtonActivationStates();
        final CopeService copeService = NMod.getCopeService();
        this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Proxies"), button -> this.field_22787.method_1507((class_437)GuiThemes.get().proxiesScreen())).method_46434(this.field_22789 - 75 - 3, 3, 75, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Accounts"), button -> this.field_22787.method_1507((class_437)GuiThemes.get().accountsScreen())).method_46434(this.field_22789 - 75 - 3 - 75 - 2, 3, 75, 20).method_46431());
        this.method_37063((class_364)(this.newAltButton = class_4185.method_46430(class_2561.method_30163("New alt"), button -> {
            this.newAltButton.field_22763 = false;
            copeService.useNewAlternateAccount(session -> MeteorClient.mc.execute(() -> this.newAltButton.field_22763 = true));
        }).method_46434(this.field_22789 - 75 - 3 - 150 - 2, 3, 75, 20).method_46431()));
        this.method_37063((class_364)class_4185.method_46430(class_2561.method_30163("Reset account"), button -> copeService.setDefaultSession()).method_46434(this.field_22789 - 75 - 3 - 230 - 2, 3, 80, 20).method_46431());
    }
    
    public CopeServerListWidget getServerListWidget() {
        return this.serverListWidget;
    }
    
    public void method_25393() {
        super.method_25393();
        this.serverListPinger.method_3000();
    }
    
    public void method_25432() {
        assert this.field_22787 != null;
        this.serverListPinger.method_3004();
    }
    
    public boolean method_25404(final int keyCode, final int scanCode, final int modifiers) {
        if (super.method_25404(keyCode, scanCode, modifiers)) {
            return true;
        }
        if (keyCode == 294) {
            this.refreshList();
            return true;
        }
        if (this.serverListWidget.method_25334() == null) {
            return false;
        }
        if (keyCode != 257 && keyCode != 335) {
            return this.serverListWidget.method_25404(keyCode, scanCode, modifiers);
        }
        this.connect();
        return true;
    }
    
    private String getLoadingText() {
        final long dotsCount = Instant.now().getEpochSecond() % 5L;
        final StringBuilder text = new StringBuilder("Loading");
        for (int i = 0; i <= dotsCount; ++i) {
            text.append(".");
        }
        return text.toString();
    }
    
    public void method_25394(final class_332 context, final int mouseX, final int mouseY, final float delta) {
        this.method_25420(context);
        try {
            this.serverListWidget.method_25394(context, mouseX, mouseY, delta);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        context.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, 20, 16777215);
        if (this.copeService.loading.get() && this.serverListWidget.getServers().isEmpty()) {
            context.method_25300(this.field_22793, this.getLoadingText(), this.field_22789 / 2, 50, 16777215);
        }
        super.method_25394(context, mouseX, mouseY, delta);
        if (!StreamerMode.isHideAccountEnabled()) {
            final String username = MeteorClient.mc.method_1548().method_1676();
            context.method_27535(this.field_22793, class_2561.method_30163(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, username)), 10, 10, 10526880);
        }
    }
    
    public void connect() {
        final CopeServerListWidget.Entry entry = (CopeServerListWidget.Entry)this.serverListWidget.method_25334();
        if (entry instanceof CopeServerListWidget.ServerEntry) {
            this.connect(((CopeServerListWidget.ServerEntry)entry).getServer());
        }
    }
    
    private void connect(final class_642 entry) {
        assert this.field_22787 != null;
        class_412.method_36877((class_437)this, this.field_22787, class_639.method_2950(entry.field_3761), entry, false);
    }
    
    public void select(final CopeServerListWidget.Entry entry) {
        this.serverListWidget.setSelected(entry);
        this.updateButtonActivationStates();
    }
    
    protected void updateButtonActivationStates() {
        this.buttonJoin.field_22763 = false;
        this.whiteListedButton.field_22763 = false;
        this.protectedButton.field_22763 = false;
        this.buttonModded.field_22763 = false;
        this.griefedButton.field_22763 = false;
        final CopeServerListWidget.Entry entry = (CopeServerListWidget.Entry)this.serverListWidget.method_25334();
        if (entry != null) {
            this.buttonJoin.field_22763 = true;
            if (entry instanceof CopeServerListWidget.ServerEntry) {
                this.whiteListedButton.field_22763 = true;
                this.protectedButton.field_22763 = true;
                this.buttonModded.field_22763 = true;
                this.griefedButton.field_22763 = true;
            }
        }
    }
    
    public void refreshList() {
        final CopeServerListWidget serverListWidget = this.getServerListWidget();
        if (serverListWidget != null) {
            serverListWidget.setServers(new ServerList());
            this.copeService.currentFindRequest.skip = 0;
            this.copeService.find(this::setCopeServers);
        }
    }
    
    public void showMore() {
        final CopeServerListWidget serverListWidget = this.getServerListWidget();
        if (serverListWidget != null) {
            this.copeService.findMore(this::addCopeServers);
        }
    }
    
    private void updateServer(final CopeService.UpdateServerRequest req) {
        final CopeServerListWidget.ServerEntry entry;
        ForkJoinPool.commonPool().submit(() -> {
            entry = (CopeServerListWidget.ServerEntry)this.serverListWidget.method_25334();
            if (entry != null) {
                req.server = entry.getServer().field_3761;
                this.copeService.update(req, server -> {});
            }
        });
    }
    
    private void setCopeServers(final List<CopeService.Server> servers, final List<CopeService.Server> activeServers) {
        this.serverList = new ServerList();
        this.mapServers(servers, activeServers);
        this.serverListWidget.setSelected(null);
        this.serverListWidget.setServers(this.serverList);
    }
    
    private void addCopeServers(final List<CopeService.Server> servers, final List<CopeService.Server> activeServers) {
        this.mapServers(servers, activeServers);
    }
    
    private void mapServers(final List<CopeService.Server> servers, final List<CopeService.Server> activeServers) {
        StringJoiner joiner;
        final StringJoiner obj;
        final Stream<Object> stream;
        this.addServers(activeServers, server -> {
            if (server.griefers != null && !server.griefers.isEmpty()) {
                joiner = new StringJoiner(",");
                server.griefers.stream().map(griefer -> StreamerMode.isStreaming() ? griefer.playerNameAlias : griefer.profileName);
                Objects.requireNonNull(obj);
                stream.forEach((Consumer<? super Object>)obj::add);
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/util/StringJoiner;)Ljava/lang/String;, server.displayServerAddress(), joiner);
            }
            else {
                return server.displayServerAddress();
            }
        });
        this.addServers(servers, CopeService.Server::displayServerAddress);
    }
    
    private void addServers(final List<CopeService.Server> servers, final Function<CopeService.Server, String> name) {
        servers.stream().map(found -> new CopeServerInfo(name.apply(found), found)).forEach(serverInfo -> this.serverList.add(serverInfo));
        this.serverListWidget.setServers(this.serverList);
    }
    
    public class_644 getServerListPinger() {
        return this.serverListPinger;
    }
}
