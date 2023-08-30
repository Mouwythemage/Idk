// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.client.ui.copenheimer.servers;

import net.minecraft.class_437;
import net.minecraft.class_327;
import net.minecraft.class_156;
import java.util.function.Function;
import java.util.Objects;
import net.minecraft.class_5481;
import net.minecraft.class_5348;
import com.google.common.base.MoreObjects;
import fifthcolumn.n.modules.StreamerMode;
import net.minecraft.class_155;
import net.minecraft.class_1044;
import net.minecraft.class_1047;
import meteordevelopment.meteorclient.MeteorClient;
import com.google.common.hash.Hashing;
import net.minecraft.class_1043;
import net.minecraft.class_642;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_124;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import net.minecraft.class_140;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.LoggerFactory;
import net.minecraft.class_364;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import java.util.function.Supplier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_757;
import net.minecraft.class_289;
import net.minecraft.class_332;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.class_350;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.class_310;
import java.util.concurrent.ForkJoinPool;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;
import net.minecraft.class_4280;

public class CopeServerListWidget extends class_4280<Entry>
{
    static final Logger LOGGER;
    static final ThreadPoolExecutor SERVER_PINGER_THREAD_POOL;
    static final class_2960 UNKNOWN_SERVER_TEXTURE;
    static final class_2960 SERVER_SELECTION_TEXTURE;
    static final class_2960 ICONS_TEXTURE;
    static final class_2561 LAN_SCANNING_TEXT;
    static final class_2561 CANNOT_RESOLVE_TEXT;
    static final class_2561 CANNOT_CONNECT_TEXT;
    static final class_2561 INCOMPATIBLE_TEXT;
    static final class_2561 NO_CONNECTION_TEXT;
    static final class_2561 PINGING_TEXT;
    private final CopeMultiplayerScreen screen;
    private static final List<ServerEntry> SERVERS;
    private static final List<String> hideServersAddress;
    private ForkJoinPool serverListPingPool;
    
    public CopeServerListWidget(final CopeMultiplayerScreen multiplayerScreen, final class_310 minecraftClient, final int i, final int j, final int k, final int l, final int m) {
        super(minecraftClient, i, j, k, l, m);
        this.screen = multiplayerScreen;
        this.serverListPingPool = new ForkJoinPool(1);
    }
    
    public List<ServerEntry> getServers() {
        synchronized (CopeServerListWidget.SERVERS) {
            return CopeServerListWidget.SERVERS;
        }
    }
    
    private void updateEntries() {
        synchronized (CopeServerListWidget.SERVERS) {
            this.method_25314((Collection)Collections.unmodifiableList((List<?>)CopeServerListWidget.SERVERS.stream().filter(s -> !CopeServerListWidget.hideServersAddress.contains(s.server.field_3761)).collect((Collector<? super Object, ?, List<? extends T>>)Collectors.toList())));
        }
    }
    
    public void setSelected(@Nullable final Entry entry) {
        super.method_25313((class_350.class_351)entry);
        this.screen.updateButtonActivationStates();
    }
    
    public void removeSelectedServerEntry() {
        final Optional<Entry> entry = Optional.ofNullable((Entry)this.method_25334());
        entry.ifPresent(e -> {
            CopeServerListWidget.hideServersAddress.add(e.server.field_3761);
            CopeServerListWidget.SERVERS.remove(e);
            this.method_25314((Collection)Collections.unmodifiableList((List<?>)CopeServerListWidget.SERVERS));
        });
    }
    
    public boolean method_25404(final int keyCode, final int scanCode, final int modifiers) {
        final Entry entry = (Entry)this.method_25334();
        return (entry != null) ? entry.method_25404(keyCode, scanCode, modifiers) : super.method_25404(keyCode, scanCode, modifiers);
    }
    
    public void setServers(final ServerList servers) {
        synchronized (CopeServerListWidget.SERVERS) {
            CopeServerListWidget.SERVERS.clear();
            this.screen.getServerListPinger().method_3004();
            this.serverListPingPool.shutdownNow();
            this.serverListPingPool = new ForkJoinPool(100);
            for (int i = 0; i < servers.size(); ++i) {
                CopeServerListWidget.SERVERS.add(new ServerEntry(this.screen, servers.get(i)));
            }
            this.updateEntries();
            new ArrayList(CopeServerListWidget.SERVERS).parallelStream().forEach(serverEntry -> this.serverListPingPool.submit(() -> {
                try {
                    this.screen.getServerListPinger().method_3003(serverEntry.server, () -> this.serverListPingPool.submit(this::updateEntries));
                }
                catch (UnknownHostException var2) {
                    serverEntry.server.field_3758 = -1L;
                    serverEntry.server.field_3757 = CopeServerListWidget.CANNOT_RESOLVE_TEXT;
                }
                catch (Exception var3) {
                    serverEntry.server.field_3758 = -1L;
                    serverEntry.server.field_3757 = CopeServerListWidget.CANNOT_CONNECT_TEXT;
                }
            }));
        }
    }
    
    protected int method_25329() {
        return super.method_25329() + 30;
    }
    
    public int method_25322() {
        return super.method_25322() + 85;
    }
    
    public boolean method_25370() {
        return this.screen.method_25399() == this;
    }
    
    public void method_25325(final class_332 context) {
        final float vOffset = 0.0f;
        final class_289 tessellator = class_289.method_1348();
        final class_287 bufferBuilder = tessellator.method_1349();
        RenderSystem.setShader((Supplier)class_757::method_34543);
        RenderSystem.setShaderTexture(0, CopeServerInfo.TNT_BLOCK_TEXTURE);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        bufferBuilder.method_1328(class_293.class_5596.field_27382, class_290.field_1575);
        bufferBuilder.method_22912(0.0, (double)this.field_22743, 0.0).method_22913(0.0f, this.field_22743 / 32.0f + vOffset).method_1336(64, 64, 64, 255).method_1344();
        bufferBuilder.method_22912((double)this.field_22742, (double)this.field_22743, 0.0).method_22913(this.field_22742 / 32.0f, this.field_22743 / 32.0f + vOffset).method_1336(64, 64, 64, 255).method_1344();
        bufferBuilder.method_22912((double)this.field_22742, 0.0, 0.0).method_22913(this.field_22742 / 32.0f, vOffset).method_1336(64, 64, 64, 255).method_1344();
        bufferBuilder.method_22912(0.0, 0.0, 0.0).method_22913(0.0f, vOffset).method_1336(64, 64, 64, 255).method_1344();
        tessellator.method_1350();
    }
    
    static {
        LOGGER = LoggerFactory.getLogger((Class)CopeServerListWidget.class);
        SERVERS = new CopyOnWriteArrayList<ServerEntry>();
        hideServersAddress = new CopyOnWriteArrayList<String>();
        SERVER_PINGER_THREAD_POOL = new ScheduledThreadPoolExecutor(25, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new class_140(CopeServerListWidget.LOGGER)).build());
        UNKNOWN_SERVER_TEXTURE = new class_2960("textures/misc/unknown_server.png");
        SERVER_SELECTION_TEXTURE = new class_2960("textures/gui/server_selection.png");
        ICONS_TEXTURE = new class_2960("textures/gui/icons.png");
        LAN_SCANNING_TEXT = (class_2561)class_2561.method_43471("lanServer.scanning");
        CANNOT_RESOLVE_TEXT = (class_2561)class_2561.method_43471("multiplayer.status.cannot_resolve").method_27692(class_124.field_1079);
        CANNOT_CONNECT_TEXT = (class_2561)class_2561.method_43471("multiplayer.status.cannot_connect").method_27692(class_124.field_1079);
        INCOMPATIBLE_TEXT = (class_2561)class_2561.method_43471("multiplayer.status.incompatible");
        NO_CONNECTION_TEXT = (class_2561)class_2561.method_43471("multiplayer.status.no_connection");
        PINGING_TEXT = (class_2561)class_2561.method_43471("multiplayer.status.pinging");
    }
    
    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends class_4280.class_4281<Entry>
    {
    }
    
    @Environment(EnvType.CLIENT)
    public class ServerEntry extends Entry
    {
        private final CopeMultiplayerScreen screen;
        private final class_642 server;
        private final class_2960 iconTextureId;
        @Nullable
        private class_1043 icon;
        private long time;
        
        protected ServerEntry(final CopeMultiplayerScreen multiplayerScreen, final class_642 serverInfo) {
            this.screen = multiplayerScreen;
            this.server = serverInfo;
            this.iconTextureId = new class_2960(invokedynamic(makeConcatWithConstants:(Lcom/google/common/hash/HashCode;)Ljava/lang/String;, Hashing.sha256().hashUnencodedChars((CharSequence)serverInfo.field_3761)));
            final class_1044 abstractTexture = MeteorClient.mc.method_1531().method_34590(this.iconTextureId, (class_1044)class_1047.method_4540());
            if (abstractTexture != class_1047.method_4540() && abstractTexture instanceof class_1043) {
                this.icon = (class_1043)abstractTexture;
            }
        }
        
        public void method_25343(final class_332 context, final int index, final int y, final int x, final int entryWidth, final int entryHeight, final int mouseX, final int mouseY, final boolean hovered, final float tickDelta) {
            synchronized (CopeServerListWidget.SERVERS) {
                if (!this.server.field_3754) {
                    this.server.field_3754 = true;
                    this.server.field_3758 = -2L;
                    this.server.field_3757 = (class_2561)class_2561.method_43473();
                    this.server.field_3753 = (class_2561)class_2561.method_43473();
                }
                final boolean bl = this.server.field_3756 != class_155.method_16673().method_48020();
                final class_327 textRenderer = MeteorClient.mc.field_1772;
                context.method_51433(textRenderer, this.server.field_3752, x + 32 + 3, y + 1, 16777215, false);
                final class_2561 labelText = (class_2561)(StreamerMode.isHideServerInfoEnabled() ? class_2561.method_30163("No peeking ;)") : MoreObjects.firstNonNull((Object)this.server.field_3757, (Object)class_2561.method_43473()));
                final List<class_5481> list = (List<class_5481>)MeteorClient.mc.field_1772.method_1728((class_5348)labelText, entryWidth - 32 - 2);
                for (int i = 0; i < Math.min(list.size(), 2); ++i) {
                    final class_5481 orderedText = list.get(i);
                    final int textX = x + 32 + 3;
                    final int textY = y + 12 + 9 * i;
                    Objects.requireNonNull(MeteorClient.mc.field_1772);
                    context.method_51430(textRenderer, orderedText, textX, textY, 8421504, false);
                }
                class_2561 text;
                if (bl) {
                    text = (class_2561)this.server.field_3760.method_27661().method_27692(class_124.field_1061);
                }
                else if (StreamerMode.isHideServerInfoEnabled() && this.server.field_41861 != null) {
                    final int players = (this.server.field_41861.comp_1280() > 0) ? (this.server.field_41861.comp_1280() + StreamerMode.addFakePlayers()) : (this.server.field_41861.comp_1279() % (StreamerMode.addFakePlayers() + 1));
                    text = class_2561.method_30163(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, players));
                }
                else {
                    text = this.server.field_3753;
                }
                final int j = MeteorClient.mc.field_1772.method_27525((class_5348)text);
                context.method_51439(textRenderer, text, x + entryWidth - j - 15 - 2, y + 1, 8421504, false);
                int k = 0;
                int s;
                class_2561 text2;
                List<class_5481> list2;
                if (bl) {
                    s = 5;
                    text2 = CopeServerListWidget.INCOMPATIBLE_TEXT;
                    list2 = ((this.server.field_3762 != null) ? this.server.field_3762.stream().map(class_2561::method_30937).toList() : List.of());
                }
                else if (this.server.field_3754 && this.server.field_3758 != -2L) {
                    if (this.server.field_3758 < 0L) {
                        s = 5;
                    }
                    else if (this.server.field_3758 < 150L) {
                        s = 0;
                    }
                    else if (this.server.field_3758 < 300L) {
                        s = 1;
                    }
                    else if (this.server.field_3758 < 600L) {
                        s = 2;
                    }
                    else if (this.server.field_3758 < 1000L) {
                        s = 3;
                    }
                    else {
                        s = 4;
                    }
                    if (this.server.field_3758 < 0L) {
                        text2 = CopeServerListWidget.NO_CONNECTION_TEXT;
                        list2 = Collections.emptyList();
                    }
                    else {
                        text2 = (class_2561)class_2561.method_43469("multiplayer.status.ping", new Object[] { this.server.field_3758 });
                        list2 = ((this.server.field_3762 != null) ? this.server.field_3762.stream().map(class_2561::method_30937).toList() : List.of());
                    }
                }
                else {
                    k = 1;
                    s = (int)(class_156.method_658() / 100L + index * 2L & 0x7L);
                    if (s > 4) {
                        s = 8 - s;
                    }
                    text2 = CopeServerListWidget.PINGING_TEXT;
                    list2 = Collections.emptyList();
                }
                if (StreamerMode.isHideServerInfoEnabled()) {
                    list2 = Collections.emptyList();
                }
                RenderSystem.setShader((Supplier)class_757::method_34542);
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.setShaderTexture(0, CopeServerListWidget.ICONS_TEXTURE);
                context.method_25290(CopeServerListWidget.ICONS_TEXTURE, x + entryWidth - 15, y, (float)(k * 10), (float)(176 + s * 8), 10, 8, 256, 256);
                final byte[] bytes = this.server.method_49306();
                this.server.method_49305(bytes);
                final class_642 server = this.server;
                Label_0913: {
                    if (server instanceof CopeServerInfo) {
                        final CopeServerInfo copeServer = (CopeServerInfo)server;
                        if (copeServer.isGriefing()) {
                            this.draw(context, x, y, CopeServerInfo.TNT_BLOCK_TEXTURE);
                            break Label_0913;
                        }
                    }
                    if (this.icon == null) {
                        this.draw(context, x, y, CopeServerListWidget.UNKNOWN_SERVER_TEXTURE);
                    }
                    else {
                        this.draw(context, x, y, this.iconTextureId);
                    }
                }
                final int t = mouseX - x;
                final int u = mouseY - y;
                if (t >= entryWidth - 15 && t <= entryWidth - 5 && u >= 0 && u <= 8) {
                    this.screen.method_47414((List)List.of(text2.method_30937()));
                }
                else if (t >= entryWidth - j - 15 - 2 && t <= entryWidth - 15 - 2 && u >= 0 && u <= 8) {
                    this.screen.method_47414((List)list2);
                }
                if (hovered) {
                    RenderSystem.setShaderTexture(0, CopeServerListWidget.SERVER_SELECTION_TEXTURE);
                    context.method_25294(x, y, x + 32, y + 32, -1601138544);
                    RenderSystem.setShader((Supplier)class_757::method_34542);
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                    final int v = mouseX - x;
                    if (this.canConnect()) {
                        if (v < 32 && v > 16) {
                            context.method_25290(CopeServerListWidget.SERVER_SELECTION_TEXTURE, x, y, 0.0f, 32.0f, 32, 32, 256, 256);
                        }
                        else {
                            context.method_25290(CopeServerListWidget.SERVER_SELECTION_TEXTURE, x, y, 0.0f, 0.0f, 32, 32, 256, 256);
                        }
                    }
                }
            }
        }
        
        protected void draw(final class_332 context, final int x, final int y, final class_2960 textureId) {
            RenderSystem.setShaderTexture(0, textureId);
            RenderSystem.enableBlend();
            context.method_25290(textureId, x, y, 0.0f, 0.0f, 32, 32, 32, 32);
            RenderSystem.disableBlend();
        }
        
        private boolean canConnect() {
            return true;
        }
        
        public boolean method_25402(final double mouseX, final double mouseY, final int button) {
            final double d = mouseX - CopeServerListWidget.this.method_25342();
            if (d <= 32.0 && d < 32.0 && d > 16.0 && this.canConnect()) {
                this.screen.select(this);
                this.screen.connect();
                return true;
            }
            this.screen.select(this);
            if (class_156.method_658() - this.time < 250L) {
                this.screen.connect();
            }
            this.time = class_156.method_658();
            return false;
        }
        
        public boolean method_25404(final int keyCode, final int scanCode, final int modifiers) {
            if (class_437.method_25438(keyCode)) {
                MeteorClient.mc.field_1774.method_1455(this.server.field_3761);
                return true;
            }
            return super.method_25404(keyCode, scanCode, modifiers);
        }
        
        public class_642 getServer() {
            return this.server;
        }
        
        public class_2561 method_37006() {
            return (class_2561)class_2561.method_43469("narrator.select", new Object[] { this.server.field_3752 });
        }
    }
}
