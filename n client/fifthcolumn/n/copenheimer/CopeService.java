// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.copenheimer;

import java.util.Objects;
import net.minecraft.class_5321;
import net.minecraft.class_1937;
import java.util.UUID;
import java.util.Random;
import fifthcolumn.n.modules.StreamerMode;
import java.util.Set;
import org.slf4j.LoggerFactory;
import meteordevelopment.meteorclient.systems.waypoints.Waypoint;
import net.minecraft.class_310;
import net.minecraft.class_155;
import net.minecraft.class_746;
import fifthcolumn.n.modules.LarpModule;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import meteordevelopment.meteorclient.utils.world.Dimension;
import meteordevelopment.meteorclient.systems.waypoints.Waypoints;
import fifthcolumn.n.modules.WaypointSync;
import java.util.Collections;
import net.minecraft.class_1657;
import fifthcolumn.n.NMod;
import fifthcolumn.n.modules.GrieferTracer;
import meteordevelopment.meteorclient.systems.modules.Modules;
import java.io.IOException;
import java.net.http.HttpResponse;
import fifthcolumn.n.collar.CollarLogin;
import java.time.Duration;
import java.net.URI;
import java.net.http.HttpRequest;
import fifthcolumn.n.events.GrieferUpdateEvent;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.mixin.MinecraftClientAccessor;
import fifthcolumn.n.modules.BanEvasion;
import com.google.gson.reflect.TypeToken;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import com.google.common.cache.CacheLoader;
import java.util.concurrent.TimeUnit;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Executors;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import net.minecraft.class_320;
import net.minecraft.class_642;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.Executor;
import java.net.http.HttpClient;
import com.google.gson.Gson;
import org.slf4j.Logger;

public final class CopeService
{
    private static final Logger LOGGER;
    private static final String BASE_URL = "http://cope.fifthcolumnmc.com/";
    private static final Long backgroundRefreshIntervalSeconds;
    private static final Gson GSON;
    private final HttpClient clientDelegate;
    public final FindServersRequest currentFindRequest;
    public final Executor executor;
    public AtomicBoolean loading;
    public final ScheduledExecutorService backgroundActiveExecutorService;
    private class_642 currentServer;
    private class_642 serverInfo;
    private class_320 defaultSession;
    private ScheduledFuture<?> backgroundActiveRefresh;
    private final ConcurrentHashMap<String, Griefer> griefers;
    private final LoadingCache<TranslateRequest, CompletableFuture<Optional<TranslateResponse>>> translationCache;
    
    public CopeService() {
        this.clientDelegate = HttpClient.newBuilder().build();
        this.currentFindRequest = defaultFindRequest();
        final Thread thread;
        this.executor = Executors.newFixedThreadPool(3, r -> {
            thread = new Thread(r);
            thread.setName("CopeService");
            return thread;
        });
        this.loading = new AtomicBoolean(false);
        this.backgroundActiveExecutorService = new ScheduledThreadPoolExecutor(1);
        this.griefers = new ConcurrentHashMap<String, Griefer>();
        this.translationCache = (LoadingCache<TranslateRequest, CompletableFuture<Optional<TranslateResponse>>>)CacheBuilder.newBuilder().maximumSize(1000L).expireAfterAccess(1L, TimeUnit.HOURS).build((CacheLoader)new CacheLoader<TranslateRequest, CompletableFuture<Optional<TranslateResponse>>>() {
            public CompletableFuture<Optional<TranslateResponse>> load(final TranslateRequest req) throws Exception {
                final CompletableFuture<Optional<TranslateResponse>> resp = new CompletableFuture<Optional<TranslateResponse>>();
                String content;
                Optional<TranslateResponse> translateResponse;
                final CompletableFuture<Optional<TranslateResponse>> completableFuture;
                CopeService.this.executor.execute(() -> {
                    try {
                        content = CopeService.this.post("http://cope.fifthcolumnmc.com/api/text/translate", req);
                        translateResponse = Optional.ofNullable(CopeService.GSON.fromJson(content, (Class)TranslateResponse.class));
                        completableFuture.complete(translateResponse);
                    }
                    catch (Throwable e) {
                        CopeService.LOGGER.error("CopeService Translate Error", e);
                        completableFuture.complete(Optional.empty());
                    }
                    return;
                });
                return resp;
            }
        });
    }
    
    public List<Griefer> griefers() {
        return new ArrayList<Griefer>(this.griefers.values());
    }
    
    public void find(final BiConsumer<List<Server>, List<Server>> resultConsumer) {
        this.currentFindRequest.skip = 0;
        this.doFind(resultConsumer);
    }
    
    public void findMore(final BiConsumer<List<Server>, List<Server>> resultConsumer) {
        final FindServersRequest currentFindRequest = this.currentFindRequest;
        currentFindRequest.skip += this.currentFindRequest.limit;
        this.doFind(resultConsumer);
    }
    
    private void doFind(final BiConsumer<List<Server>, List<Server>> resultConsumer) {
        this.loading.set(true);
        String content;
        FindServersResponse servers;
        this.executor.execute(() -> {
            try {
                content = this.post("http://cope.fifthcolumnmc.com/api/servers/find", this.currentFindRequest);
                servers = (FindServersResponse)CopeService.GSON.fromJson(content, (Class)FindServersResponse.class);
                this.loading.set(false);
                resultConsumer.accept(servers.searchResult, servers.activeServers);
            }
            catch (Throwable e) {
                CopeService.LOGGER.error("CopeService Find Error", e);
                this.loading.set(false);
            }
        });
    }
    
    public void update(final UpdateServerRequest req, final Consumer<Server> serverConsumer) {
        String content;
        this.executor.execute(() -> {
            try {
                content = this.post("http://cope.fifthcolumnmc.com/api/servers/update", req);
                serverConsumer.accept((Server)CopeService.GSON.fromJson(content, (Class)Server.class));
            }
            catch (Throwable e) {
                CopeService.LOGGER.error("CopeService Update Error", e);
            }
        });
    }
    
    public void findHistoricalPlayers(final Consumer<List<ServerPlayer>> playersConsumer) {
        String content;
        this.createFindPlayersRequest().ifPresent(req -> this.executor.execute(() -> {
            try {
                content = this.post("http://cope.fifthcolumnmc.com/api/servers/findPlayers", req);
                playersConsumer.accept((List<ServerPlayer>)CopeService.GSON.fromJson(content, new TypeToken<ArrayList<ServerPlayer>>() {}.getType()));
            }
            catch (Throwable e) {
                CopeService.LOGGER.error("CopeService FindPlayers Error", e);
            }
        }));
    }
    
    public CompletableFuture<Optional<TranslateResponse>> translate(final TranslateRequest request) {
        return (CompletableFuture<Optional<TranslateResponse>>)this.translationCache.getUnchecked((Object)request);
    }
    
    public void getAccount(final Consumer<GetAccountResponse> consumer) {
        final String response;
        this.executor.execute(() -> {
            response = this.httpGet("http://cope.fifthcolumnmc.com/api/accounts/alt");
            consumer.accept((GetAccountResponse)CopeService.GSON.fromJson(response, (Class)GetAccountResponse.class));
        });
    }
    
    public void useNewAlternateAccount(final Consumer<class_320> sessionConsumer) {
        final String username;
        final class_320 session;
        this.getAccount(resp -> {
            username = (BanEvasion.isSpacesToNameEnabled() ? invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, resp.username) : resp.username);
            session = new class_320(username, resp.uuid, resp.token, (Optional)Optional.empty(), (Optional)Optional.empty(), class_320.class_321.field_34962);
            ((MinecraftClientAccessor)MeteorClient.mc).setSession(session);
            MeteorClient.mc.method_1539().clear();
            MeteorClient.mc.execute(() -> sessionConsumer.accept(session));
        });
    }
    
    private void setActive(final ActiveServerRequest req) {
        try {
            final String body = this.post("http://cope.fifthcolumnmc.com/api/servers/active", req);
            final ActiveServerResponse resp = (ActiveServerResponse)CopeService.GSON.fromJson(body, (Class)ActiveServerResponse.class);
            resp.griefers.forEach(griefer -> this.griefers.put(griefer.profileName, griefer));
            MeteorClient.EVENT_BUS.post((Object)new GrieferUpdateEvent(resp.griefers));
        }
        catch (Throwable e) {
            CopeService.LOGGER.error("CopeService Active Server Error", e);
        }
    }
    
    private String post(final String url, final Object req) {
        final String body = CopeService.GSON.toJson(req);
        final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(20L)).header("x-collar-membership", CollarLogin.getMembershipToken()).headers("x-player-name", MeteorClient.mc.method_1548().method_1676()).POST(HttpRequest.BodyPublishers.ofString(body)).build();
        return this.execute(request);
    }
    
    private String httpGet(final String url) {
        final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(20L)).header("x-collar-membership", CollarLogin.getMembershipToken()).headers("x-player-name", MeteorClient.mc.method_1548().method_1676()).GET().build();
        return this.execute(request);
    }
    
    private String execute(final HttpRequest request) {
        HttpResponse<String> response;
        try {
            response = this.clientDelegate.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException ex2) {
            final Exception ex;
            final Exception e = ex;
            throw new IllegalStateException(e);
        }
        final int code = response.statusCode();
        if (code != 200) {
            throw new RuntimeException(invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, code, (String)response.body()));
        }
        return response.body();
    }
    
    public void backgroundActiveServerUpdate() {
        if (MeteorClient.mc == null) {
            return;
        }
        final class_642 serverEntry = MeteorClient.mc.method_1558();
        if (serverEntry == null) {
            return;
        }
        final class_746 player = MeteorClient.mc.field_1724;
        if (player == null) {
            return;
        }
        Position location = null;
        final Modules modules = Modules.get();
        if (modules.isActive((Class)GrieferTracer.class) && !NMod.is2b2t()) {
            location = Position.from((class_1657)player);
        }
        List<Waypoint> waypoints = Collections.emptyList();
        if (modules.isActive((Class)WaypointSync.class) && !NMod.is2b2t()) {
            final Waypoint newWaypoint;
            waypoints = Waypoints.get().waypoints.values().stream().map(waypoint -> {
                newWaypoint = new Waypoint();
                newWaypoint.name = (String)waypoint.name.get();
                newWaypoint.position = new Position();
                newWaypoint.position.x = waypoint.getPos().method_10263();
                newWaypoint.position.y = waypoint.getPos().method_10264();
                newWaypoint.position.z = waypoint.getPos().method_10260();
                if (waypoint.dimension.get() == Dimension.Overworld) {
                    newWaypoint.position.dimension = "OVERWORLD";
                }
                else if (waypoint.dimension.get() == Dimension.End) {
                    newWaypoint.position.dimension = "END";
                }
                else if (waypoint.dimension.get() == Dimension.Nether) {
                    newWaypoint.position.dimension = "NETHER";
                }
                return newWaypoint;
            }).collect((Collector<? super Object, ?, List<Waypoint>>)Collectors.toList());
        }
        final String playerNameAlias = modules.isActive((Class)LarpModule.class) ? ((String)((LarpModule)modules.get((Class)LarpModule.class)).aliasName.get()) : null;
        try {
            this.setActive(new ActiveServerRequest(serverEntry.field_3761, player.method_5820(), MeteorClient.mc.field_1724.method_5667(), playerNameAlias, location, waypoints));
        }
        catch (Exception e) {
            CopeService.LOGGER.error("Active server request failed.", (Throwable)e);
        }
    }
    
    private static FindServersRequest defaultFindRequest() {
        final FindServersRequest request = new FindServersRequest();
        request.hasName = null;
        request.hasVersion = class_155.method_16673().method_48019();
        request.playersOnline = true;
        request.isModded = false;
        request.isProtected = false;
        request.isWhitelisted = false;
        request.isGriefed = false;
        request.isCracked = false;
        request.skip = 0;
        request.limit = 100;
        request.lang = null;
        request.multiProtocol = false;
        request.orderBy = "ONLINE_PLAYERS";
        return request;
    }
    
    public synchronized void startUpdating() {
        if (this.backgroundActiveRefresh == null) {
            this.backgroundActiveRefresh = this.backgroundActiveExecutorService.scheduleAtFixedRate(this::backgroundActiveServerUpdate, 0L, CopeService.backgroundRefreshIntervalSeconds, TimeUnit.SECONDS);
        }
    }
    
    public synchronized void stopUpdating() {
        if (this.backgroundActiveRefresh == null) {
            return;
        }
        this.backgroundActiveRefresh.cancel(true);
        this.backgroundActiveRefresh = null;
    }
    
    public Optional<FindPlayersRequest> createFindPlayersRequest() {
        final class_642 currentServerEntry = MeteorClient.mc.method_1558();
        if (currentServerEntry == null) {
            return Optional.empty();
        }
        if (currentServerEntry.equals(this.currentServer)) {
            return Optional.empty();
        }
        this.currentServer = currentServerEntry;
        final FindPlayersRequest request = new FindPlayersRequest();
        request.serverAddress = currentServerEntry.field_3761;
        return Optional.of(request);
    }
    
    public void clearTranslations() {
        this.translationCache.invalidateAll();
    }
    
    public void setLastServerInfo(final class_642 currentServerEntry) {
        this.serverInfo = currentServerEntry;
    }
    
    public class_642 getLastServerInfo() {
        return this.serverInfo;
    }
    
    public void setDefaultSession(final class_320 session) {
        this.defaultSession = session;
    }
    
    public void setDefaultSession() {
        if (this.defaultSession == null) {
            return;
        }
        final class_310 mc = class_310.method_1551();
        ((MinecraftClientAccessor)mc).setSession(this.defaultSession);
    }
    
    public class_320 getDefaultSession() {
        return this.defaultSession;
    }
    
    static {
        LOGGER = LoggerFactory.getLogger((Class)CopeService.class);
        backgroundRefreshIntervalSeconds = 5L;
        GSON = new Gson();
    }
    
    public static final class FindServersRequest
    {
        public String hasName;
        public String hasVersion;
        public Boolean playersOnline;
        public Boolean isCracked;
        public Boolean isWhitelisted;
        public Boolean isModded;
        public Boolean isProtected;
        public Boolean isGriefed;
        public Integer skip;
        public Integer limit;
        public String lang;
        public Boolean multiProtocol;
        public String orderBy;
    }
    
    public static class FindServersResponse
    {
        public List<Server> activeServers;
        public List<Server> searchResult;
    }
    
    public static final class UpdateServerRequest
    {
        public String server;
        public Boolean isWhitelisted;
        public Boolean isProtected;
        public Boolean isModded;
        public Boolean isGriefed;
    }
    
    public static final class Server
    {
        public String serverAddress;
        public String description;
        public String icon;
        public Set<Griefer> griefers;
        
        public String displayServerAddress() {
            return displayForServerAddress(this.serverAddress);
        }
        
        public static String displayForServerAddress(final String serverAddress) {
            final StreamerMode streamerMode = (StreamerMode)Modules.get().get((Class)StreamerMode.class);
            if (streamerMode != null && streamerMode.isActive()) {
                try {
                    final int ipOffset = streamerMode.useRandomIpOffset.get() ? new Random().nextInt(1, 254) : 0;
                    final int ipHeader = (Integer.parseInt(serverAddress.substring(0, serverAddress.indexOf("."))) + ipOffset) % 255;
                    return invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, ipHeader);
                }
                catch (NumberFormatException | StringIndexOutOfBoundsException ex2) {
                    final RuntimeException ex;
                    final RuntimeException ignored = ex;
                    return "Server";
                }
            }
            return serverAddress;
        }
        
        public String displayDescription() {
            if (StreamerMode.isHideServerInfoEnabled()) {
                return "No peeking ;)";
            }
            if (this.description == null) {
                return "";
            }
            return this.description.replaceAll("[\ud83c\udf00-\ud83d\ude4f]|[\ud83d\ude80-\ud83d\udeff]", "");
        }
        
        public Optional<String> iconData() {
            if (this.icon != null && this.icon.startsWith("data:image/png;base64,")) {
                return Optional.of(this.icon.substring("data:image/png;base64,".length()));
            }
            return Optional.empty();
        }
    }
    
    public static final class Griefer
    {
        public String profileName;
        public String playerName;
        public UUID playerId;
        public String playerNameAlias;
        public String serverAddress;
        public Position location;
        public List<Waypoint> waypoints;
    }
    
    public static final class Position
    {
        public static final String OVERWORLD = "OVERWORLD";
        public static final String END = "END";
        public static final String NETHER = "NETHER";
        public double x;
        public double y;
        public double z;
        public String dimension;
        
        public static Position from(final class_1657 player) {
            final Position location = new Position();
            location.x = player.method_19538().field_1352;
            location.y = player.method_19538().field_1351;
            location.z = player.method_19538().field_1350;
            final class_5321<class_1937> world = (class_5321<class_1937>)player.method_37908().method_27983();
            String dimension;
            if (world == class_1937.field_25179) {
                dimension = "OVERWORLD";
            }
            else if (world == class_1937.field_25181) {
                dimension = "END";
            }
            else if (world == class_1937.field_25180) {
                dimension = "NETHER";
            }
            else {
                dimension = "OVERWORLD";
            }
            location.dimension = dimension;
            return location;
        }
        
        @Override
        public String toString() {
            return invokedynamic(makeConcatWithConstants:(DDDLjava/lang/String;)Ljava/lang/String;, this.x, this.y, this.z, this.dimension);
        }
    }
    
    public static final class Waypoint
    {
        public String name;
        public Position position;
    }
    
    public static final class ServerPlayer
    {
        public String name;
        public Boolean isValid;
    }
    
    public static final class FindPlayersRequest
    {
        public String serverAddress;
    }
    
    public static class ActiveServerRequest
    {
        public final String server;
        public final String playerName;
        public final UUID playerId;
        public final String playerNameAlias;
        public final Position location;
        public final List<Waypoint> waypoints;
        
        public ActiveServerRequest(final String server, final String playerName, final UUID playerId, final String playerNameAlias, final Position position, final List<Waypoint> waypoints) {
            this.server = server;
            this.playerName = playerName;
            this.playerId = playerId;
            this.playerNameAlias = playerNameAlias;
            this.location = position;
            this.waypoints = waypoints;
        }
    }
    
    public static class ActiveServerResponse
    {
        public List<Griefer> griefers;
    }
    
    public static final class TranslateRequest
    {
        public long uniqueId;
        public String text;
        public String sourceLang;
        public String targetLang;
        
        public TranslateRequest(final long uniqueId, final String text, final String sourceLang, final String targetLang) {
            this.uniqueId = uniqueId;
            this.text = text;
            this.sourceLang = sourceLang;
            this.targetLang = targetLang;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final TranslateRequest that = (TranslateRequest)o;
            return this.uniqueId == that.uniqueId && Objects.equals(this.text, that.text) && Objects.equals(this.sourceLang, that.sourceLang) && Objects.equals(this.targetLang, that.targetLang);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.uniqueId, this.text, this.sourceLang, this.targetLang);
        }
    }
    
    public static final class TranslateResponse
    {
        public String text;
        public String lang;
    }
    
    public static final class GetAccountResponse
    {
        public String username;
        public String uuid;
        public String token;
    }
}
