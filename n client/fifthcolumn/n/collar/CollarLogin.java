// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.collar;

import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.io.File;
import org.slf4j.LoggerFactory;
import com.collarmc.api.groups.http.CreateGroupTokenResponse;
import com.collarmc.api.authentication.AuthenticationService;
import com.collarmc.rest.RESTClient;
import java.io.IOException;
import net.minecraft.class_310;
import com.google.gson.Gson;
import org.slf4j.Logger;
import java.util.UUID;

public final class CollarLogin
{
    private static final UUID COPE_GROUP_ID;
    private static final Logger LOGGER;
    private static final Gson GSON;
    private static final class_310 mc;
    
    public static String getMembershipToken() {
        try {
            return CollarSettings.read().membershipToken;
        }
        catch (IOException e) {
            CollarLogin.LOGGER.error("Unable to read Collar group membership token", (Throwable)e);
            throw new IllegalStateException(e);
        }
    }
    
    public static boolean refreshSession() {
        final RESTClient client = createClient();
        CollarSettings settings;
        try {
            settings = CollarSettings.read();
        }
        catch (Throwable e) {
            CollarLogin.LOGGER.error("Unable to read Collar settings", e);
            return false;
        }
        final LoginResult loginResult = loginAndSave(settings.email, settings.password);
        if (loginResult.success) {
            return client.validateGroupMembershipToken(settings.membershipToken, CollarLogin.COPE_GROUP_ID).isPresent();
        }
        CollarLogin.LOGGER.error("Collar group membership validation unsuccessful");
        return false;
    }
    
    public static LoginResult loginAndSave(final String email, final String password) {
        final RESTClient client = createClient();
        final CollarSettings settings;
        return client.login(AuthenticationService.LoginRequest.emailAndPassword(email, password)).map(loginResponse -> loginResponse.token).map(token -> client.createGroupMembershipToken(token, CollarLogin.COPE_GROUP_ID).map(resp -> {
            settings = new CollarSettings();
            settings.email = email;
            settings.password = password;
            settings.membershipToken = resp.token;
            try {
                settings.save();
            }
            catch (IOException e) {
                CollarLogin.LOGGER.error("Could not save collar settings");
                return new LoginResult(false, e.getMessage());
            }
            return new LoginResult(true, null);
        }).orElse(new LoginResult(false, "Login failed"))).orElse(new LoginResult(false, "Login failed"));
    }
    
    private static RESTClient createClient() {
        return new RESTClient("https://api.collarmc.com");
    }
    
    static {
        COPE_GROUP_ID = UUID.fromString("fe2b0ae3-8984-414b-8a5f-e972736bb77c");
        LOGGER = LoggerFactory.getLogger((Class)CollarLogin.class);
        GSON = new Gson();
        mc = class_310.method_1551();
    }
    
    public static final class LoginResult
    {
        public final boolean success;
        public final String reason;
        
        public LoginResult(final boolean success, final String reason) {
            this.success = success;
            this.reason = reason;
        }
    }
    
    public static final class CollarSettings
    {
        public String email;
        public String password;
        public String membershipToken;
        
        public void save() throws IOException {
            final File file = new File(CollarLogin.mc.field_1697, "collar.json");
            final String contents = CollarLogin.GSON.toJson((Object)this);
            Files.writeString(file.toPath(), contents, new OpenOption[0]);
        }
        
        public static CollarSettings read() throws IOException {
            final File file = new File(CollarLogin.mc.field_1697, "collar.json");
            final String contents = Files.readString(file.toPath());
            return (CollarSettings)CollarLogin.GSON.fromJson(contents, (Class)CollarSettings.class);
        }
    }
}
