// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import meteordevelopment.meteorclient.settings.StringSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class OriginsModule extends Module
{
    private final SettingGroup sgGeneral;
    public final Setting<String> versionSetting;
    
    public OriginsModule() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "Origins compat", "You will need to set a version");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.versionSetting = (Setting<String>)this.sgGeneral.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)new StringSetting.Builder().name("version")).description("version of origins")).defaultValue((Object)"1.7.1")).build());
    }
    
    public int[] getSemVer() {
        String VERSION = (String)this.versionSetting.get();
        if (VERSION.contains("+")) {
            VERSION = VERSION.split("\\+")[0];
        }
        if (VERSION.contains("-")) {
            VERSION = VERSION.split("-")[0];
        }
        final String[] splitVersion = VERSION.split("\\.");
        final int[] semver = new int[splitVersion.length];
        for (int i = 0; i < semver.length; ++i) {
            semver[i] = Integer.parseInt(splitVersion[i]);
        }
        return semver;
    }
}
