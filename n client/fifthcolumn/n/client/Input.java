// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.client;

import java.util.regex.Matcher;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import java.util.regex.Pattern;

public final class Input
{
    private static final Pattern STRIP_PATTERN;
    private static final Pattern ADD_UUID_PATTERN;
    private static final Pattern VALID_MC_NAME;
    
    public static String stripMinecraft(final String input) {
        if (input == null) {
            return "";
        }
        return StringUtils.trimToEmpty(Input.STRIP_PATTERN.matcher(input).replaceAll(""));
    }
    
    public static boolean isMinecraftFormatted(final String input) {
        return Input.STRIP_PATTERN.matcher(input).matches();
    }
    
    public static boolean isValidMinecraftUsername(final String input) {
        return !isMinecraftFormatted(input) && Input.VALID_MC_NAME.matcher(input).matches();
    }
    
    public static UUID parseUUID(final String possibleUUID) {
        try {
            return UUID.fromString(possibleUUID);
        }
        catch (IllegalArgumentException e) {
            final Matcher matcher = Input.ADD_UUID_PATTERN.matcher(possibleUUID);
            if (matcher.matches()) {
                return UUID.fromString(matcher.replaceAll("$1-$2-$3-$4-$5"));
            }
            throw e;
        }
    }
    
    static {
        STRIP_PATTERN = Pattern.compile("(?<!<@)[&§](?i)[0-9a-fklmnorx]");
        ADD_UUID_PATTERN = Pattern.compile("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)");
        VALID_MC_NAME = Pattern.compile("^\\w{3,16}$");
    }
}
