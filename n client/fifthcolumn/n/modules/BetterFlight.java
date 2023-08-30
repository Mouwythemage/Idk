// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import net.minecraft.class_2338;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import net.minecraft.class_2374;
import fifthcolumn.n.utils.BlockPosUtils;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.movement.Scaffold;
import net.minecraft.class_243;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_2848;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class BetterFlight extends Module
{
    private final SettingGroup sgGeneral;
    private final Setting<Double> speed;
    private final Setting<Double> maxSpeed;
    private final Setting<Boolean> velocitySpeed;
    private final Setting<AntiKickMode> antiKickModeSetting;
    private float speedDelta;
    
    public BetterFlight() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "Better Flight", "Fly like a motherfucker");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.speed = (Setting<Double>)this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("base speed")).description("Your speed when you start to fly.")).defaultValue(1.0).min(0.0).build());
        this.maxSpeed = (Setting<Double>)this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("maximum speed")).description("The maximum speed used for velocity speed")).defaultValue(5.0).min(0.0).max(10.0).build());
        this.velocitySpeed = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("velocity speed")).description("increases the velocity the longer you fly")).defaultValue((Object)true)).build());
        this.antiKickModeSetting = (Setting<AntiKickMode>)this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)new EnumSetting.Builder().name("anti-kick mode")).description("method for anti-kick")).defaultValue((Object)AntiKickMode.PACKET)).build());
        this.speedDelta = 0.0f;
    }
    
    @EventHandler
    private void onSendPacket(final PacketEvent.Send event) {
        if (event.packet instanceof class_2848) {
            event.cancel();
        }
    }
    
    public void onActivate() {
        super.onActivate();
        if (this.mc.field_1724.method_31549().field_7477) {
            return;
        }
        this.mc.field_1724.method_31549().field_7478 = true;
    }
    
    public void onDeactivate() {
        super.onDeactivate();
        if (this.mc.field_1724.method_31549().field_7477) {
            return;
        }
        this.mc.field_1724.method_31549().field_7478 = false;
    }
    
    @EventHandler
    private void onPostTick(final TickEvent.Post ignored) {
        if (!this.isActive()) {
            return;
        }
        if ((boolean)this.velocitySpeed.get() && this.isMoving()) {
            this.speedDelta += 0.1f;
        }
        if (!this.isMoving() && this.speedDelta > 0.0f) {
            this.speedDelta = 0.0f;
            this.mc.field_1724.method_18799(class_243.field_1353);
        }
        float speed;
        if (((Scaffold)Modules.get().get((Class)Scaffold.class)).isActive() || ((BuildPoop)Modules.get().get((Class)BuildPoop.class)).isActive()) {
            speed = 0.8f;
        }
        else if (this.velocitySpeed.get()) {
            speed = ((Double)this.speed.get()).floatValue() * this.speedDelta;
            final float maxSpeed = ((Double)this.maxSpeed.get()).floatValue();
            if (speed >= maxSpeed) {
                speed = maxSpeed;
            }
        }
        else {
            speed = ((Double)this.speed.get()).floatValue();
        }
        final class_243 antiKickVel = this.getAntiKickVec();
        this.mc.field_1724.method_18799(antiKickVel);
        final class_243 forward = new class_243(0.0, 0.0, (double)speed).method_1024(-(float)Math.toRadians(this.mc.field_1724.method_36454()));
        final class_243 strafe = forward.method_1024((float)Math.toRadians(90.0));
        if (this.mc.field_1690.field_1903.method_1434()) {
            if (speed == 0.0f) {
                this.mc.field_1724.method_18799(this.mc.field_1724.method_18798().method_1031(0.0, (double)((Double)this.speed.get()).floatValue(), 0.0));
            }
            else {
                this.mc.field_1724.method_18799(this.mc.field_1724.method_18798().method_1031(0.0, (double)speed, 0.0));
            }
        }
        if (this.mc.field_1690.field_1832.method_1434()) {
            if (speed == 0.0f) {
                this.mc.field_1724.method_18799(this.mc.field_1724.method_18798().method_1031(0.0, (double)(-((Double)this.speed.get()).floatValue()), 0.0));
            }
            else {
                this.mc.field_1724.method_18799(this.mc.field_1724.method_18798().method_1031(0.0, (double)(-speed), 0.0));
            }
        }
        if (this.mc.field_1690.field_1881.method_1434()) {
            this.mc.field_1724.method_18799(this.mc.field_1724.method_18798().method_1031(-forward.field_1352, 0.0, -forward.field_1350));
        }
        if (this.mc.field_1690.field_1894.method_1434()) {
            this.mc.field_1724.method_18799(this.mc.field_1724.method_18798().method_1031(forward.field_1352, 0.0, forward.field_1350));
        }
        if (this.mc.field_1690.field_1913.method_1434()) {
            this.mc.field_1724.method_18799(this.mc.field_1724.method_18798().method_1031(strafe.field_1352, 0.0, strafe.field_1350));
        }
        if (this.mc.field_1690.field_1849.method_1434()) {
            this.mc.field_1724.method_18799(this.mc.field_1724.method_18798().method_1031(-strafe.field_1352, 0.0, -strafe.field_1350));
        }
    }
    
    private class_243 getAntiKickVec() {
        class_243 class_243 = null;
        switch ((AntiKickMode)this.antiKickModeSetting.get()) {
            default: {
                throw new IncompatibleClassChangeError();
            }
            case NONE: {
                class_243 = net.minecraft.class_243.field_1353;
                break;
            }
            case FALL: {
                final class_243 position = this.mc.field_1724.method_19538().method_1031(0.0, -0.069, 0.0);
                final class_2338 blockPos = BlockPosUtils.from((class_2374)position);
                class_243 = ((this.mc.field_1724.field_6012 % 7 == 0 && this.mc.field_1687.method_8320(blockPos).method_45474()) ? net.minecraft.class_243.field_1353.method_1031(0.0, -0.069, 0.0) : net.minecraft.class_243.field_1353);
                break;
            }
            case BOB: {
                if (this.mc.field_1724.field_6012 % 40 == 0) {
                    final class_243 position = this.mc.field_1724.method_19538().method_1031(0.0, 0.15, 0.0);
                    if (this.mc.field_1687.method_8320(BlockPosUtils.from((class_2374)position)).method_45474()) {
                        class_243 = net.minecraft.class_243.field_1353.method_1031(0.0, 0.15, 0.0);
                        break;
                    }
                }
                else if (this.mc.field_1724.field_6012 % 20 == 0) {
                    final class_243 position = this.mc.field_1724.method_19538().method_1031(0.0, -0.15, 0.0);
                    if (this.mc.field_1687.method_8320(BlockPosUtils.from((class_2374)position)).method_45474()) {
                        class_243 = net.minecraft.class_243.field_1353.method_1031(0.0, -0.15, 0.0);
                        break;
                    }
                }
                class_243 = net.minecraft.class_243.field_1353;
                break;
            }
            case PACKET: {
                if (this.mc.field_1724.field_6012 % 20 == 0) {
                    this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() - 0.069, this.mc.field_1724.method_23321(), false));
                    this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() + 0.069, this.mc.field_1724.method_23321(), true));
                }
                class_243 = net.minecraft.class_243.field_1353;
                break;
            }
        }
        return class_243;
    }
    
    private boolean isMoving() {
        return this.mc.field_1690.field_1832.method_1434() || this.mc.field_1690.field_1881.method_1434() || this.mc.field_1690.field_1894.method_1434() || this.mc.field_1690.field_1913.method_1434() || this.mc.field_1690.field_1849.method_1434();
    }
    
    public enum AntiKickMode
    {
        NONE, 
        FALL, 
        BOB, 
        PACKET;
        
        private static /* synthetic */ AntiKickMode[] $values() {
            return new AntiKickMode[] { AntiKickMode.NONE, AntiKickMode.FALL, AntiKickMode.BOB, AntiKickMode.PACKET };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
