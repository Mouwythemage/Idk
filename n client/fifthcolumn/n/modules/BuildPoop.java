// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import net.minecraft.class_2346;
import net.minecraft.class_2248;
import net.minecraft.class_1922;
import net.minecraft.class_1799;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
import meteordevelopment.meteorclient.events.meteor.MouseButtonEvent;
import meteordevelopment.meteorclient.mixin.PlayerMoveC2SPacketAccessor;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.class_2828;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import net.minecraft.class_2680;
import net.minecraft.class_243;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import java.util.Comparator;
import meteordevelopment.meteorclient.utils.world.BlockIterator;
import net.minecraft.class_3532;
import meteordevelopment.meteorclient.utils.Utils;
import net.minecraft.class_1747;
import net.minecraft.class_3965;
import meteordevelopment.meteorclient.utils.world.CardinalDirection;
import net.minecraft.class_2382;
import meteordevelopment.meteorclient.events.world.TickEvent;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Objects;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import net.minecraft.class_239;
import net.minecraft.class_2350;
import java.util.List;
import net.minecraft.class_2338;
import meteordevelopment.meteorclient.utils.misc.Pool;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class BuildPoop extends Module
{
    private final SettingGroup sgGeneral;
    private final SettingGroup sgRender;
    private final SettingGroup sgStairCase;
    private final Setting<PlaceMode> placeMode;
    private final Setting<Double> range;
    private final Setting<Integer> delay;
    private final Setting<Integer> maxBlocksPerTick;
    private final Setting<Boolean> swingHand;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> render;
    private final Setting<SettingColor> lineColor;
    private final Setting<SettingColor> sideColor;
    private final Setting<SettingColor> overlapColor;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<Integer> height;
    private final Setting<Boolean> reverse;
    private final Setting<Boolean> autoFly;
    private final Setting<Boolean> stopOnIntersect;
    private final Setting<Boolean> snapOnIntersect;
    private final Setting<Integer> snapDistance;
    private final Pool<class_2338.class_2339> blockPosPool;
    private final List<class_2338.class_2339> blocksList;
    private final Pool<RenderBlock> renderBlockPool;
    private final List<RenderBlock> renderBlocks;
    private final List<RenderBlock> renderOverlapBlocks;
    private final List<class_2338> stairsList;
    private class_2338 snapPos;
    private class_2350 snapDir;
    private class_2338 flyHereBlock;
    private class_239 hitResult;
    private boolean started;
    private int delay1;
    
    public BuildPoop() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "Build Poop", "Shit on the noobs");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.sgStairCase = this.settings.createGroup("StairCase");
        this.placeMode = (Setting<PlaceMode>)this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)new EnumSetting.Builder().name("place-mode")).description("How to place")).defaultValue((Object)PlaceMode.Floor)).build());
        this.range = (Setting<Double>)this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("range")).description("Custom range to place at.")).defaultValue(4.5).min(1.0).max(4.5).build());
        this.delay = (Setting<Integer>)this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("delay")).description("tick delay for placement. recommended 0 unless you are lagging")).defaultValue((Object)0)).min(0).max(2).build());
        this.maxBlocksPerTick = (Setting<Integer>)this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("max-blocks-per-tick")).description("blocks to place per tick")).defaultValue((Object)1)).min(1).max(2).build());
        this.swingHand = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("swing-hand")).description("Swing hand client side.")).defaultValue((Object)true)).build());
        this.rotate = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("rotate")).description("Spinny spinny")).defaultValue((Object)true)).build());
        this.render = (Setting<Boolean>)this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("render")).description("Renders a block overlay where the blocks will be placed.")).defaultValue((Object)true)).build());
        final SettingGroup sgRender = this.sgRender;
        final ColorSetting.Builder builder = (ColorSetting.Builder)((ColorSetting.Builder)new ColorSetting.Builder().name("line-color")).description("The color of the lines of the blocks being rendered.");
        final Setting<Boolean> render = this.render;
        Objects.requireNonNull(render);
        this.lineColor = (Setting<SettingColor>)sgRender.add((Setting)((ColorSetting.Builder)builder.visible(render::get)).defaultValue(new SettingColor(204, 0, 0, 255)).build());
        final SettingGroup sgRender2 = this.sgRender;
        final ColorSetting.Builder builder2 = (ColorSetting.Builder)((ColorSetting.Builder)new ColorSetting.Builder().name("side-color")).description("The color of the sides of the blocks being rendered.");
        final Setting<Boolean> render2 = this.render;
        Objects.requireNonNull(render2);
        this.sideColor = (Setting<SettingColor>)sgRender2.add((Setting)((ColorSetting.Builder)builder2.visible(render2::get)).defaultValue(new SettingColor(204, 0, 0, 10)).build());
        final SettingGroup sgRender3 = this.sgRender;
        final ColorSetting.Builder builder3 = (ColorSetting.Builder)((ColorSetting.Builder)new ColorSetting.Builder().name("overlap-color")).description("The color of the lines & sides of the blocks being rendered when overlapping a block");
        final Setting<Boolean> render3 = this.render;
        Objects.requireNonNull(render3);
        this.overlapColor = (Setting<SettingColor>)sgRender3.add((Setting)((ColorSetting.Builder)builder3.visible(render3::get)).defaultValue(new SettingColor(0, 204, 0, 50)).build());
        final SettingGroup sgRender4 = this.sgRender;
        final EnumSetting.Builder builder4 = (EnumSetting.Builder)((EnumSetting.Builder)new EnumSetting.Builder().name("shape-mode")).description("How the shapes are rendered.");
        final Setting<Boolean> render4 = this.render;
        Objects.requireNonNull(render4);
        this.shapeMode = (Setting<ShapeMode>)sgRender4.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)builder4.visible(render4::get)).defaultValue((Object)ShapeMode.Both)).build());
        this.height = (Setting<Integer>)this.sgStairCase.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("height")).description("Max height for each staircase placement")).defaultValue((Object)16)).min(2).sliderMax(200).build());
        this.reverse = (Setting<Boolean>)this.sgStairCase.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("reverse")).description("start from top when staircasing")).defaultValue((Object)false)).build());
        this.autoFly = (Setting<Boolean>)this.sgStairCase.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("Auto Fly")).description("fly while staircasing")).defaultValue((Object)false)).build());
        this.stopOnIntersect = (Setting<Boolean>)this.sgStairCase.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("Stop On Intersect")).description("Stairs will only go up to the first intersecting block and not past it")).defaultValue((Object)false)).build());
        this.snapOnIntersect = (Setting<Boolean>)this.sgStairCase.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("Snap On Intersect")).description("Staircase will snap to other staircases")).defaultValue((Object)false)).build());
        this.snapDistance = (Setting<Integer>)this.sgStairCase.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("Snap Distance")).description("The distance that snapping breaks at")).defaultValue((Object)3)).build());
        this.blockPosPool = (Pool<class_2338.class_2339>)new Pool(class_2338.class_2339::new);
        this.blocksList = new ArrayList<class_2338.class_2339>();
        this.renderBlockPool = (Pool<RenderBlock>)new Pool(RenderBlock::new);
        this.renderBlocks = new ArrayList<RenderBlock>();
        this.renderOverlapBlocks = new ArrayList<RenderBlock>();
        this.stairsList = new ArrayList<class_2338>();
        this.snapPos = null;
        this.snapDir = null;
        this.flyHereBlock = null;
        this.started = false;
        this.delay1 = 0;
    }
    
    public void onActivate() {
        super.onActivate();
        for (final RenderBlock renderBlock : this.renderBlocks) {
            this.renderBlockPool.free((Object)renderBlock);
        }
        this.renderBlocks.clear();
        this.started = false;
        this.stairsList.clear();
    }
    
    public void onDeactivate() {
        super.onDeactivate();
        for (final RenderBlock renderBlock : this.renderBlocks) {
            this.renderBlockPool.free((Object)renderBlock);
        }
        this.renderBlocks.clear();
        this.started = false;
        this.stairsList.clear();
    }
    
    @EventHandler
    private void onTickPre(final TickEvent.Pre event) {
        this.renderBlocks.forEach(RenderBlock::tick);
        this.renderOverlapBlocks.forEach(RenderBlock::tick);
        this.renderBlocks.removeIf(renderBlock -> renderBlock.ticks <= 0);
        this.renderOverlapBlocks.removeIf(renderBlock -> renderBlock.ticks <= 0);
        if (this.placeMode.get() == PlaceMode.StairCase) {
            for (final class_2338 pos : this.stairsList) {
                final class_2680 state = this.mc.field_1687.method_8320(pos);
                if (state.method_26215() || state.method_51176()) {
                    this.renderBlocks.add(((RenderBlock)this.renderBlockPool.get()).set(pos, 1));
                }
                else {
                    this.renderOverlapBlocks.add(((RenderBlock)this.renderBlockPool.get()).set(pos, 1));
                }
            }
            if (!this.started) {
                if (this.snapPos == null || this.stairsList.isEmpty() || !(boolean)this.snapOnIntersect.get() || this.snapDir != this.mc.field_1724.method_5735() || Math.sqrt(this.mc.method_1560().method_24515().method_10262((class_2382)this.snapPos)) > (int)this.snapDistance.get()) {
                    this.snapPos = null;
                    for (final CardinalDirection dir : CardinalDirection.values()) {
                        if (dir.toDirection() == this.mc.field_1724.method_5735()) {
                            this.stairsList.clear();
                            this.hitResult = this.mc.method_1560().method_5745((double)this.mc.field_1761.method_2904(), 0.0f, false);
                            if (!(this.hitResult instanceof class_3965)) {
                                break;
                            }
                            if (!(this.mc.field_1724.method_6047().method_7909() instanceof class_1747)) {
                                break;
                            }
                            final class_2338 startBlock = ((class_3965)this.hitResult).method_17777();
                            int x = 0;
                            int z = 0;
                            switch (dir) {
                                case South: {
                                    z = 1;
                                    break;
                                }
                                case East: {
                                    x = 1;
                                    break;
                                }
                                case West: {
                                    x = -1;
                                    break;
                                }
                                default: {
                                    z = -1;
                                    break;
                                }
                            }
                            for (int i = 1; i <= (int)this.height.get(); ++i) {
                                final class_2338 nextStair = new class_2338(startBlock.method_10263() + x * i, ((boolean)this.reverse.get()) ? (startBlock.method_10264() - i) : (startBlock.method_10264() + i), startBlock.method_10260() + z * i);
                                if (startBlock.method_10264() + i < 318) {
                                    this.stairsList.add(nextStair);
                                }
                                if (this.stopOnIntersect.get()) {
                                    final class_2680 state2 = this.mc.field_1687.method_8320(nextStair);
                                    if (!state2.method_26215() && !state2.method_51176()) {
                                        this.snapPos = this.mc.method_1560().method_24515();
                                        this.snapDir = this.mc.field_1724.method_5735();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else if (this.stairsList.isEmpty()) {
                this.started = false;
            }
        }
        final double pX = this.mc.field_1724.method_23317();
        final double pY = this.mc.field_1724.method_23318();
        final double pZ = this.mc.field_1724.method_23321();
        final double rangeSq = Math.pow((double)this.range.get(), 2.0);
        final double n;
        final double n2;
        int offset;
        BlockIterator.register((int)Math.ceil((double)this.range.get()), (int)Math.ceil((double)this.range.get()), (blockPos, blockState) -> {
            if (Utils.squaredDistance(n, n2, pZ, blockPos.method_10263() + 0.5, blockPos.method_10264() + 0.5, blockPos.method_10260() + 0.5) > rangeSq) {
                return;
            }
            else {
                if (this.placeMode.get() == PlaceMode.Floor) {
                    if (!this.mc.field_1687.method_8320(blockPos).method_45474()) {
                        return;
                    }
                    else {
                        offset = 1;
                        if (this.mc.field_1690.field_1832.method_1434()) {
                            offset = 2;
                            this.mc.field_1724.method_18799(this.mc.field_1724.method_18798().method_1031(0.0, -0.5, 0.0));
                        }
                        if (!blockPos.equals((Object)new class_2338(blockPos.method_10263(), class_3532.method_15357(n2 - offset), blockPos.method_10260()))) {
                            return;
                        }
                    }
                }
                else if (this.placeMode.get() == PlaceMode.StairCase) {
                    if (!this.mc.field_1687.method_8320(blockPos).method_45474()) {
                        this.stairsList.remove(blockPos);
                        return;
                    }
                    else if (!this.stairsList.contains(blockPos) || !this.started) {
                        return;
                    }
                }
                this.blocksList.add(((class_2338.class_2339)this.blockPosPool.get()).method_10101((class_2382)blockPos));
                return;
            }
        });
        int count;
        final Iterator<class_2338.class_2339> iterator2;
        class_2338.class_2339 block;
        FindItemResult item;
        final Iterator<class_2338.class_2339> iterator3;
        class_2338.class_2339 blockPos2;
        BlockIterator.after(() -> {
            this.blocksList.sort(Comparator.comparingDouble(value -> Utils.squaredDistance(pX, pY, pZ, value.method_10263() + 0.5, value.method_10264() + 0.5, value.method_10260() + 0.5)));
            if (this.blocksList.isEmpty()) {
                this.flyHereBlock = null;
            }
            else {
                count = 0;
                this.blocksList.iterator();
                while (iterator2.hasNext()) {
                    block = iterator2.next();
                    if (count >= (int)this.maxBlocksPerTick.get()) {
                        break;
                    }
                    else if (this.delay1 < (int)this.delay.get()) {
                        ++this.delay1;
                        break;
                    }
                    else {
                        this.delay1 = 0;
                        item = InvUtils.findInHotbar(itemStack -> this.validItem(itemStack, (class_2338)block));
                        if (!item.found() || item.getHand() == null) {
                            return;
                        }
                        else if (BlockUtils.place((class_2338)block, item, (boolean)this.rotate.get(), 50, (boolean)this.swingHand.get(), true)) {
                            this.flyHereBlock = new class_2338(block.method_10263(), block.method_10264() + 2, block.method_10260());
                            ++count;
                            this.renderBlocks.add(((RenderBlock)this.renderBlockPool.get()).set((class_2338)block, 20));
                            if ((boolean)this.autoFly.get() && this.placeMode.get() == PlaceMode.StairCase) {
                                this.mc.field_1724.method_18799(class_243.field_1353);
                                this.mc.field_1724.method_5814(this.flyHereBlock.method_10263() + 0.5, this.flyHereBlock.method_10264() + 0.5, this.flyHereBlock.method_10260() + 0.5);
                            }
                            else {
                                continue;
                            }
                        }
                        else {
                            continue;
                        }
                    }
                }
                this.blocksList.iterator();
                while (iterator3.hasNext()) {
                    blockPos2 = iterator3.next();
                    this.blockPosPool.free((Object)blockPos2);
                }
                this.blocksList.clear();
            }
        });
    }
    
    @EventHandler
    private void onSendPacket(final PacketEvent.Send event) {
        if (!(boolean)this.autoFly.get() || this.placeMode.get() != PlaceMode.StairCase || this.mc.field_1724.method_31549().field_7477 || !(event.packet instanceof class_2828) || Modules.get().isActive((Class)BetterFlight.class) || this.mc.field_1724.method_6128() || this.mc.field_1724.method_18798().field_1351 > -1.0E-4) {
            return;
        }
        ((PlayerMoveC2SPacketAccessor)event.packet).setOnGround(true);
    }
    
    @EventHandler
    private void onMouseButton(final MouseButtonEvent event) {
        if (this.started || !(this.mc.field_1724.method_6047().method_7909() instanceof class_1747) || this.mc.field_1755 != null || this.placeMode.get() != PlaceMode.StairCase || event.action != KeyAction.Press) {
            return;
        }
        if (event.button == 1) {
            event.setCancelled(this.started = true);
        }
        else if (event.button == 2) {
            if (this.reverse.get()) {
                this.reverse.set((Object)false);
            }
            else {
                this.reverse.set((Object)true);
            }
        }
    }
    
    @EventHandler
    private void onRender(final Render3DEvent event) {
        if (!(boolean)this.render.get()) {
            return;
        }
        this.renderBlocks.sort(Comparator.comparingInt(o -> -o.ticks));
        this.renderBlocks.forEach(renderBlock -> renderBlock.render(event, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get()));
        this.renderOverlapBlocks.sort(Comparator.comparingInt(o -> -o.ticks));
        this.renderOverlapBlocks.forEach(renderBlock -> renderBlock.render(event, (Color)this.overlapColor.get(), (Color)this.overlapColor.get(), (ShapeMode)this.shapeMode.get()));
    }
    
    private boolean validItem(final class_1799 itemStack, final class_2338 pos) {
        if (!(itemStack.method_7909() instanceof class_1747)) {
            return false;
        }
        final class_2248 block = ((class_1747)itemStack.method_7909()).method_7711();
        return class_2248.method_9614(block.method_9564().method_26220((class_1922)this.mc.field_1687, pos)) && (!(block instanceof class_2346) || !class_2346.method_10128(this.mc.field_1687.method_8320(pos)));
    }
    
    public static class RenderBlock
    {
        public class_2338.class_2339 pos;
        public int ticksMax;
        public int ticks;
        
        public RenderBlock() {
            this.pos = new class_2338.class_2339();
        }
        
        public RenderBlock set(final class_2338 blockPos, final int tick) {
            this.pos.method_10101((class_2382)blockPos);
            this.ticksMax = tick;
            this.ticks = tick;
            return this;
        }
        
        public void tick() {
            --this.ticks;
        }
        
        public void render(final Render3DEvent event, final Color sides, final Color lines, final ShapeMode shapeMode) {
            final double x1 = this.pos.method_10263() + (this.ticksMax - this.ticks) / (double)(this.ticksMax ^ 2 / this.ticks);
            final double y1 = this.pos.method_10264() + (this.ticksMax - this.ticks) / (double)(this.ticksMax ^ 2 / this.ticks);
            final double z1 = this.pos.method_10260() + (this.ticksMax - this.ticks) / (double)(this.ticksMax ^ 2 / this.ticks);
            final double x2 = this.pos.method_10263() + 1 - (this.ticksMax - this.ticks) / (double)(this.ticksMax ^ 2 / this.ticks);
            final double y2 = this.pos.method_10264() + 1 - (this.ticksMax - this.ticks) / (double)(this.ticksMax ^ 2 / this.ticks);
            final double z2 = this.pos.method_10260() + 1 - (this.ticksMax - this.ticks) / (double)(this.ticksMax ^ 2 / this.ticks);
            final int preSideA = sides.a;
            final int preLineA = lines.a;
            sides.a *= (int)(this.ticks / (double)this.ticksMax);
            lines.a *= (int)(this.ticks / (double)this.ticksMax);
            event.renderer.box(x1, y1, z1, x2, y2, z2, sides, lines, shapeMode, 0);
            sides.a = preSideA;
            lines.a = preLineA;
        }
    }
    
    public enum PlaceMode
    {
        Floor, 
        StairCase;
        
        private static /* synthetic */ PlaceMode[] $values() {
            return new PlaceMode[] { PlaceMode.Floor, PlaceMode.StairCase };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
