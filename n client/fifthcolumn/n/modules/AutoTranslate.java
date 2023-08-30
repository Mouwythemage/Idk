// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import meteordevelopment.meteorclient.systems.modules.misc.BetterChat;
import meteordevelopment.meteorclient.systems.modules.Modules;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import org.apache.commons.lang3.StringEscapeUtils;
import java.util.regex.Pattern;
import java.net.URLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import fifthcolumn.n.events.ReceiveMsgEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.events.game.SendMessageEvent;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import java.util.List;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class AutoTranslate extends Module
{
    private final SettingGroup sgGeneral;
    public static final List<Module> CONFLICTING_MODULES;
    public final Setting<Boolean> translateOut;
    public final Setting<Boolean> translateIn;
    public final Setting<Lang> serverLang;
    public final Setting<Lang> localLang;
    
    public AutoTranslate() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "AutoTranslate", "Google translate for block game");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.translateOut = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("Translate outgoing")).description("messages you send")).defaultValue((Object)false)).build());
        this.translateIn = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("Translate incoming")).description("messages you receive")).defaultValue((Object)true)).build());
        this.serverLang = (Setting<Lang>)this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)new EnumSetting.Builder().name("Server Language")).description("Language the server speaks")).defaultValue((Object)Lang.RUSSIAN)).build());
        this.localLang = (Setting<Lang>)this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)new EnumSetting.Builder().name("Local Language")).description("Language you speak")).defaultValue((Object)Lang.ENGLISH)).build());
    }
    
    @EventHandler
    private void onMessageSend(final SendMessageEvent event) {
        if (this.translateOut.get()) {
            final String translated = this.translate(event.message, ((Lang)this.localLang.get()).value, ((Lang)this.serverLang.get()).value);
            if (!translated.isEmpty()) {
                event.message = translated;
            }
            reengageModules(disengageConflictingModules());
        }
    }
    
    @EventHandler
    private void onMessageReceive(final ReceiveMsgEvent event) {
        if (this.translateIn.get()) {
            final String message = event.getMessage().getString();
            final String translated;
            final String s;
            final Thread translate = new Thread(() -> {
                translated = this.translate(message, ((Lang)this.serverLang.get()).value, ((Lang)this.localLang.get()).value);
                class_310.method_1551().execute(() -> {
                    if (!s.isEmpty()) {
                        this.mc.field_1705.method_1743().method_1812(class_2561.method_30163(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, event.getSender(), s)));
                    }
                    reengageModules(disengageConflictingModules());
                });
                return;
            });
            translate.start();
        }
    }
    
    public void onActivate() {
        super.onActivate();
    }
    
    public void onDeactivate() {
        super.onDeactivate();
    }
    
    public String translate(final String text, final String from, final String to) {
        final String translated = this.parseHTML(this.getHTML(text, from, to));
        return text.equalsIgnoreCase(translated) ? "" : translated;
    }
    
    private String getHTML(final String text, final String langFrom, final String langTo) {
        final URL url = this.createURL(text, langFrom, langTo);
        try {
            final URLConnection con = this.setupConnection(url);
            final InputStreamReader streamReader = new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8);
            final BufferedReader br = new BufferedReader(streamReader);
            try {
                final StringBuilder html = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    html.append(line).append("\n");
                }
                final String string = html.toString();
                br.close();
                return string;
            }
            catch (Throwable t) {
                try {
                    br.close();
                }
                catch (Throwable exception) {
                    t.addSuppressed(exception);
                }
                throw t;
            }
        }
        catch (IOException e) {
            return null;
        }
    }
    
    private String parseHTML(final String html) {
        final String regex = "class=\"result-container\">([^<]*)<\\/div>";
        final Pattern pattern = Pattern.compile(regex, 8);
        final Matcher matcher = pattern.matcher(html);
        matcher.find();
        final String match = matcher.group(1);
        if (match == null || match.isEmpty()) {
            return null;
        }
        return StringEscapeUtils.unescapeHtml4(match);
    }
    
    private URL createURL(final String text, final String langFrom, final String langTo) {
        try {
            final String encodedText = URLEncoder.encode(text.trim(), StandardCharsets.UTF_8);
            final String urlString = String.format("https://translate.google.com/m?hl=en&sl=%s&tl=%s&ie=UTF-8&prev=_m&q=%s", langFrom, langTo, encodedText);
            return new URL(urlString);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private URLConnection setupConnection(final URL url) throws IOException {
        final URLConnection connection = url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        return connection;
    }
    
    private static List<Module> disengageConflictingModules() {
        final List<Module> modules = AutoTranslate.CONFLICTING_MODULES.stream().filter(Module::isActive).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        modules.forEach(Module::toggle);
        return modules;
    }
    
    private static void reengageModules(final List<Module> modules) {
        modules.forEach(Module::toggle);
    }
    
    private static Module getModule(final Class module) {
        return Modules.get().get(module);
    }
    
    static {
        CONFLICTING_MODULES = List.of(getModule(BetterChat.class));
    }
    
    public enum Lang
    {
        AFRIKAANS("Afrikaans", "af"), 
        ARABIC("Arabic", "ar"), 
        CZECH("Czech", "cs"), 
        CHINESE_SIMPLIFIED("Chinese (simplified)", "zh-CN"), 
        CHINESE_TRADITIONAL("Chinese (traditional)", "zh-TW"), 
        DANISH("Danish", "da"), 
        DUTCH("Dutch", "nl"), 
        ENGLISH("English", "en"), 
        FINNISH("Finnish", "fi"), 
        FRENCH("French", "fr"), 
        GERMAN("German", "de"), 
        GREEK("Greek", "el"), 
        HINDI("Hindi", "hi"), 
        ITALIAN("Italian", "it"), 
        JAPANESE("Japanese", "ja"), 
        KOREAN("Korean", "ko"), 
        NORWEGIAN("Norwegian", "no"), 
        POLISH("Polish", "pl"), 
        PORTUGUESE("Portuguese", "pt"), 
        RUSSIAN("Russian", "ru"), 
        SPANISH("Spanish", "es"), 
        SWAHILI("Swahili", "sw"), 
        SWEDISH("Swedish", "sv"), 
        TURKISH("Turkish", "tr");
        
        private final String name;
        public final String value;
        
        private Lang(final String name, final String value) {
            this.name = name;
            this.value = value;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        private static /* synthetic */ Lang[] $values() {
            return new Lang[] { Lang.AFRIKAANS, Lang.ARABIC, Lang.CZECH, Lang.CHINESE_SIMPLIFIED, Lang.CHINESE_TRADITIONAL, Lang.DANISH, Lang.DUTCH, Lang.ENGLISH, Lang.FINNISH, Lang.FRENCH, Lang.GERMAN, Lang.GREEK, Lang.HINDI, Lang.ITALIAN, Lang.JAPANESE, Lang.KOREAN, Lang.NORWEGIAN, Lang.POLISH, Lang.PORTUGUESE, Lang.RUSSIAN, Lang.SPANISH, Lang.SWAHILI, Lang.SWEDISH, Lang.TURKISH };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
