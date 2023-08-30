// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import net.minecraft.class_642;
import com.google.common.net.InetAddresses;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.Inet4Address;
import net.minecraft.class_8242;
import net.minecraft.class_2596;
import net.minecraft.class_2693;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import net.minecraft.class_3965;
import java.util.Iterator;
import net.minecraft.class_2818;
import net.minecraft.class_1923;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1297;
import net.minecraft.class_3959;
import net.minecraft.class_243;
import net.minecraft.class_2374;
import net.minecraft.class_2625;
import net.minecraft.class_2586;
import java.util.Map;
import java.time.temporal.TemporalAmount;
import meteordevelopment.meteorclient.events.world.TickEvent;
import org.apache.commons.lang3.StringUtils;
import net.minecraft.class_2877;
import net.minecraft.class_2338;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.mixin.AbstractSignEditScreenAccessor;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import java.util.Objects;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.StringSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import java.time.Duration;
import java.time.Instant;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class AutoSign extends Module
{
    private final SettingGroup sgGeneral;
    private final Setting<TextPreset> textPreset;
    private final Setting<String> signTextLine1;
    private final Setting<String> signTextLine2;
    private final Setting<String> signTextLine3;
    private final Setting<String> signTextLine4;
    private static final String ticketNumberReplace = "<ticketNumber>";
    private final Setting<Boolean> editSignAura;
    private final Setting<Double> distance;
    private final Setting<Integer> delay;
    private Instant lastEdit;
    private boolean interacting;
    private Instant interactTime;
    private Duration interactTimeout;
    
    public AutoSign() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "AutoSign", "Places bait signs to the 5c discord");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.textPreset = (Setting<TextPreset>)this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)new EnumSetting.Builder().name("Text Preset")).description("What do sign say?")).defaultValue((Object)TextPreset.FifthColumn)).build());
        this.signTextLine1 = (Setting<String>)this.sgGeneral.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)new StringSetting.Builder().name("Line 1")).description("The text to put on the sign line 1.")).defaultValue((Object)"Rekt by")).visible(() -> this.textPreset.get() == TextPreset.Custom)).build());
        this.signTextLine2 = (Setting<String>)this.sgGeneral.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)new StringSetting.Builder().name("Line 2")).description("The text to put on the sign line 2.")).defaultValue((Object)"discord.gg/")).visible(() -> this.textPreset.get() == TextPreset.Custom)).build());
        this.signTextLine3 = (Setting<String>)this.sgGeneral.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)new StringSetting.Builder().name("Line 3")).description("The text to put on the sign line 3.")).defaultValue((Object)"thefifthcolumn")).visible(() -> this.textPreset.get() == TextPreset.Custom)).build());
        this.signTextLine4 = (Setting<String>)this.sgGeneral.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)new StringSetting.Builder().name("Line 4")).description("The text to put on the sign line 4.")).defaultValue((Object)"#<ticketNumber>")).visible(() -> this.textPreset.get() == TextPreset.Custom)).build());
        this.editSignAura = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("Edit Sign Aura")).description("Automatically edits signs around you.")).defaultValue((Object)false)).build());
        final SettingGroup sgGeneral = this.sgGeneral;
        final DoubleSetting.Builder sliderMax = ((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("Distance")).description("The distance to search for signs.")).defaultValue(5.0).min(0.1).sliderMax(10.0);
        final Setting<Boolean> editSignAura = this.editSignAura;
        Objects.requireNonNull(editSignAura);
        this.distance = (Setting<Double>)sgGeneral.add((Setting)((DoubleSetting.Builder)sliderMax.visible(editSignAura::get)).build());
        final SettingGroup sgGeneral2 = this.sgGeneral;
        final IntSetting.Builder sliderMax2 = ((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("Delay")).description("The delay between editing signs.")).defaultValue((Object)100)).min(0).sliderMax(1000);
        final Setting<Boolean> editSignAura2 = this.editSignAura;
        Objects.requireNonNull(editSignAura2);
        this.delay = (Setting<Integer>)sgGeneral2.add((Setting)((IntSetting.Builder)sliderMax2.visible(editSignAura2::get)).build());
        this.lastEdit = Instant.EPOCH;
        this.interacting = false;
        this.interactTime = Instant.EPOCH;
        this.interactTimeout = Duration.ofMillis(500L);
    }
    
    @EventHandler
    public void onOpenScreen(final OpenScreenEvent event) {
        if (!this.isActive()) {
            return;
        }
        if (event.screen instanceof AbstractSignEditScreenAccessor) {
            event.cancel();
        }
    }
    
    private class_2877 getUpdateSignPacket(final class_2338 pos, final boolean front) {
        class_2877 packet = null;
        switch ((TextPreset)this.textPreset.get()) {
            case FifthColumn: {
                packet = new class_2877(pos, front, "Rekt by", "discord.gg/", "thefifthcolumn", invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.getTicketNumber()));
                break;
            }
            case Astral: {
                packet = new class_2877(pos, front, "Rekt by Astral", "discord.gg/", "e58M9R5TDA", invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.getTicketNumber()));
                break;
            }
            default: {
                packet = new class_2877(pos, front, this.replaceText((String)this.signTextLine1.get()), this.replaceText((String)this.signTextLine2.get()), this.replaceText((String)this.signTextLine3.get()), this.replaceText((String)this.signTextLine4.get()));
                break;
            }
        }
        return packet;
    }
    
    private String replaceText(final String text) {
        return StringUtils.replace(text, "<ticketNumber>", this.getTicketNumber());
    }
    
    @EventHandler
    public void onTick(final TickEvent.Post event) {
        if (this.mc.field_1724 == null) {
            return;
        }
        if (this.mc.field_1687 == null) {
            return;
        }
        if (!(boolean)this.editSignAura.get()) {
            return;
        }
        if (Instant.now().isBefore(this.lastEdit.plusMillis((int)this.delay.get()))) {
            return;
        }
        this.lastEdit = Instant.now();
        if (this.interacting && Instant.now().isBefore(this.interactTime.plus((TemporalAmount)this.interactTimeout))) {
            return;
        }
        final class_1923 playerChunkPos = this.mc.field_1724.method_31476();
        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                final class_2818 chunk = this.mc.field_1687.method_8497(playerChunkPos.field_9181 + x, playerChunkPos.field_9180 + z);
                final Map<class_2338, class_2586> blockEntities = (Map<class_2338, class_2586>)chunk.method_12214();
                for (final Map.Entry<class_2338, class_2586> entry : blockEntities.entrySet()) {
                    final class_2338 blockPos = entry.getKey();
                    final class_2586 blockEntity = entry.getValue();
                    if (blockEntity instanceof class_2625) {
                        final class_2625 signBlockEntity = (class_2625)blockEntity;
                        if (signBlockEntity.method_49855() || !blockPos.method_19769((class_2374)this.mc.field_1724.method_33571(), (double)this.distance.get()) || !this.shouldUpdateText(signBlockEntity)) {
                            continue;
                        }
                        final class_3959 raycastContext = new class_3959(new class_243(this.mc.field_1724.method_23317(), this.mc.field_1724.method_33571().field_1351, this.mc.field_1724.method_23321()), new class_243(blockPos.method_10263() + 0.5, blockPos.method_10264() + 0.5, blockPos.method_10260() + 0.5), class_3959.class_3960.field_17559, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724);
                        final class_3965 raycast = this.mc.field_1687.method_17742(raycastContext);
                        final boolean playerFacingFront = signBlockEntity.method_49834((class_1657)this.mc.field_1724);
                        if (this.isSignUnedited(signBlockEntity.method_49843(playerFacingFront))) {
                            this.mc.field_1761.method_2896(this.mc.field_1724, class_1268.field_5808, raycast);
                            this.interacting = false;
                            this.interactTime = Instant.now();
                            return;
                        }
                        continue;
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onPacketReceived(final PacketEvent.Receive event) {
        if (event.packet instanceof class_2693) {
            this.mc.field_1724.field_3944.method_2883((class_2596)this.getUpdateSignPacket(((class_2693)event.packet).method_11677(), ((class_2693)event.packet).method_49995()));
            this.interacting = false;
            event.cancel();
        }
    }
    
    private boolean shouldUpdateText(final class_2625 blockEntity) {
        return this.isSignUnedited(blockEntity.method_49843(true)) || this.isSignUnedited(blockEntity.method_49843(false));
    }
    
    private boolean isSignUnedited(final class_8242 signText) {
        return !signText.method_49859(0, false).getString().equals(this.replaceText((String)this.signTextLine1.get())) || !signText.method_49859(1, false).getString().equals(this.replaceText((String)this.signTextLine2.get())) || !signText.method_49859(2, false).getString().equals(this.replaceText((String)this.signTextLine3.get())) || !signText.method_49859(3, false).getString().equals(this.replaceText((String)this.signTextLine4.get()));
    }
    
    private String getTicketNumber() {
        final class_642 entry = this.mc.method_1558();
        if (entry == null || entry.field_3761 == null) {
            return (this.textPreset.get() == TextPreset.Astral) ? "Astral on top!" : "5C ON TOP";
        }
        final String ip = entry.field_3761.split(":")[0];
        Inet4Address address;
        try {
            address = (Inet4Address)InetAddress.getByName(ip);
        }
        catch (UnknownHostException e) {
            return StringUtils.abbreviate(this.mc.field_1724.method_5820(), 15);
        }
        return String.valueOf(InetAddresses.coerceToInteger((InetAddress)address));
    }
    
    public enum TextPreset
    {
        FifthColumn, 
        Astral, 
        Custom;
        
        private static /* synthetic */ TextPreset[] $values() {
            return new TextPreset[] { TextPreset.FifthColumn, TextPreset.Astral, TextPreset.Custom };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
