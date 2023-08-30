// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import net.minecraft.class_2680;
import net.minecraft.class_1657;
import net.minecraft.class_1268;
import meteordevelopment.meteorclient.utils.player.Rotations;
import net.minecraft.class_1802;
import net.minecraft.class_2346;
import net.minecraft.class_1799;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
import meteordevelopment.meteorclient.events.meteor.MouseButtonEvent;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_2248;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import java.util.Comparator;
import meteordevelopment.meteorclient.utils.world.BlockIterator;
import net.minecraft.class_2382;
import net.minecraft.class_2246;
import net.minecraft.class_3965;
import net.minecraft.class_1747;
import meteordevelopment.meteorclient.utils.world.CardinalDirection;
import meteordevelopment.meteorclient.utils.entity.EntityUtils;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.Utils;
import net.minecraft.class_1299;
import net.minecraft.class_1297;
import meteordevelopment.meteorclient.events.world.TickEvent;
import java.util.Iterator;
import java.util.ArrayList;
import meteordevelopment.meteorclient.settings.EnumSetting;
import java.util.Objects;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import net.minecraft.class_239;
import java.util.List;
import net.minecraft.class_2338;
import meteordevelopment.meteorclient.utils.misc.Pool;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class AutoWither extends Module
{
    private final SettingGroup sgGeneral;
    private final SettingGroup sgRender;
    private final Setting<Double> range;
    private final Setting<Integer> delay;
    private final Setting<Boolean> nametag;
    private final Setting<Boolean> swingHand;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> render;
    private final Setting<SettingColor> lineColor;
    private final Setting<SettingColor> sideColor;
    private final Setting<ShapeMode> shapeMode;
    private final Pool<class_2338.class_2339> blockPosPool;
    private final List<class_2338.class_2339> blocksList;
    private final Pool<RenderBlock> renderBlockPool;
    private final List<RenderBlock> renderBlocks;
    private final List<class_2338> sandList;
    private final List<class_2338> headList;
    private class_239 hitResult;
    private boolean started;
    private boolean head;
    private int d;
    
    public AutoWither() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "AutoWither", "WITHER DEEZ!!!");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.range = (Setting<Double>)this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("range")).description("Custom range to place at.")).defaultValue(5.0).min(1.0).sliderMax(6.0).build());
        this.delay = (Setting<Integer>)this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("delay")).description("Delay")).defaultValue((Object)1)).min(0).sliderMax(5).build());
        this.nametag = (Setting<Boolean>)this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("nametag")).description("will nametag withers if present in hotbar")).defaultValue((Object)true)).build());
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
        final EnumSetting.Builder builder3 = (EnumSetting.Builder)((EnumSetting.Builder)new EnumSetting.Builder().name("shape-mode")).description("How the shapes are rendered.");
        final Setting<Boolean> render3 = this.render;
        Objects.requireNonNull(render3);
        this.shapeMode = (Setting<ShapeMode>)sgRender3.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)builder3.visible(render3::get)).defaultValue((Object)ShapeMode.Both)).build());
        this.blockPosPool = (Pool<class_2338.class_2339>)new Pool(class_2338.class_2339::new);
        this.blocksList = new ArrayList<class_2338.class_2339>();
        this.renderBlockPool = (Pool<RenderBlock>)new Pool(RenderBlock::new);
        this.renderBlocks = new ArrayList<RenderBlock>();
        this.sandList = new ArrayList<class_2338>();
        this.headList = new ArrayList<class_2338>();
        this.started = false;
        this.head = false;
        this.d = 0;
    }
    
    public void onActivate() {
        super.onActivate();
        for (final RenderBlock renderBlock : this.renderBlocks) {
            this.renderBlockPool.free((Object)renderBlock);
        }
        this.renderBlocks.clear();
        this.started = false;
        this.head = false;
        this.sandList.clear();
        this.headList.clear();
    }
    
    public void onDeactivate() {
        super.onDeactivate();
        for (final RenderBlock renderBlock : this.renderBlocks) {
            this.renderBlockPool.free((Object)renderBlock);
        }
        this.renderBlocks.clear();
        this.started = false;
        this.head = false;
        this.sandList.clear();
        this.headList.clear();
    }
    
    @EventHandler
    private void onTickPre(final TickEvent.Pre event) {
        final double pX = this.mc.field_1724.method_23317();
        final double pY = this.mc.field_1724.method_23318();
        final double pZ = this.mc.field_1724.method_23321();
        this.renderBlocks.forEach(RenderBlock::tick);
        this.renderBlocks.removeIf(renderBlock -> renderBlock.ticks <= 0);
        for (final class_2338 pos : this.sandList) {
            this.renderBlocks.add(((RenderBlock)this.renderBlockPool.get()).set(pos));
        }
        for (final class_2338 pos : this.headList) {
            this.renderBlocks.add(((RenderBlock)this.renderBlockPool.get()).set(pos));
        }
        if (this.nametag.get()) {
            for (final class_1297 entity : this.mc.field_1687.method_18112()) {
                if (entity.method_5864() == class_1299.field_6119 && Utils.distance(pX, pY, pZ, entity.method_23317(), entity.method_23318(), entity.method_23321()) <= (double)this.range.get()) {
                    if (entity.method_16914()) {
                        continue;
                    }
                    final FindItemResult item = InvUtils.findInHotbar(itemStack -> this.isNameTag(itemStack));
                    if (!item.found()) {
                        break;
                    }
                    if (item.getHand() == null) {
                        this.mc.field_1724.method_31548().field_7545 = item.slot();
                        return;
                    }
                    final String text = this.mc.field_1724.method_6047().method_7964().getString();
                    if (EntityUtils.getName(entity).equals(text)) {
                        continue;
                    }
                    this.interact(entity);
                    return;
                }
            }
        }
        if (!this.started) {
            for (final CardinalDirection dir : CardinalDirection.values()) {
                if (dir.toDirection() == this.mc.field_1724.method_5735()) {
                    this.sandList.clear();
                    this.headList.clear();
                    this.hitResult = this.mc.method_1560().method_5745((double)this.mc.field_1761.method_2904(), 0.0f, false);
                    if (!(this.mc.field_1724.method_6047().method_7909() instanceof class_1747)) {
                        break;
                    }
                    final class_2248 block = ((class_1747)this.mc.field_1724.method_6047().method_7909()).method_7711();
                    if (!(this.hitResult instanceof class_3965)) {
                        break;
                    }
                    if (block != class_2246.field_10114) {
                        break;
                    }
                    final class_2338 startBlock = ((class_3965)this.hitResult).method_17777();
                    int ns = 0;
                    int ew = 0;
                    switch (dir) {
                        case East:
                        case West: {
                            ns = 1;
                            break;
                        }
                        default: {
                            ew = 1;
                            break;
                        }
                    }
                    this.sandList.add(new class_2338(startBlock.method_10263() + 0, startBlock.method_10264() + 1, startBlock.method_10260() + 0));
                    this.sandList.add(new class_2338(startBlock.method_10263() + 0, startBlock.method_10264() + 2, startBlock.method_10260() + 0));
                    this.sandList.add(new class_2338(startBlock.method_10263() + ew, startBlock.method_10264() + 2, startBlock.method_10260() + ns));
                    this.sandList.add(new class_2338(startBlock.method_10263() - ew, startBlock.method_10264() + 2, startBlock.method_10260() - ns));
                    this.headList.add(new class_2338(startBlock.method_10263() + ew, startBlock.method_10264() + 3, startBlock.method_10260() + ns));
                    this.headList.add(new class_2338(startBlock.method_10263() - ew, startBlock.method_10264() + 3, startBlock.method_10260() - ns));
                    this.headList.add(new class_2338(startBlock.method_10263() + 0, startBlock.method_10264() + 3, startBlock.method_10260() + 0));
                    for (final class_2338 pos2 : this.sandList) {
                        if (!this.mc.field_1687.method_8320(pos2).method_45474()) {
                            this.sandList.clear();
                            this.headList.clear();
                            return;
                        }
                    }
                    for (final class_2338 pos2 : this.headList) {
                        if (!this.mc.field_1687.method_8320(pos2).method_45474()) {
                            this.sandList.clear();
                            this.headList.clear();
                            return;
                        }
                    }
                }
            }
        }
        else {
            if (this.sandList.isEmpty() && !this.head) {
                this.head = true;
            }
            if (this.headList.isEmpty()) {
                this.head = false;
                this.started = false;
                final FindItemResult item2 = InvUtils.findInHotbar(itemStack -> this.validItem(itemStack, new class_2338(0, 0, 0), false));
                if (!item2.found()) {
                    return;
                }
                if (item2.getHand() == null) {
                    this.mc.field_1724.method_31548().field_7545 = item2.slot();
                    return;
                }
            }
        }
        final double rangeSq = Math.pow((double)this.range.get(), 2.0);
        final double n;
        final double n2;
        final double n3;
        final double n4;
        BlockIterator.register((int)Math.ceil((double)this.range.get()), (int)Math.ceil((double)this.range.get()), (blockPos, blockState) -> {
            if (!this.started || Utils.squaredDistance(n, n2, n3, blockPos.method_10263() + 0.5, blockPos.method_10264() + 0.5, blockPos.method_10260() + 0.5) > n4 || (!this.sandList.contains(blockPos) && !this.headList.contains(blockPos))) {
                return;
            }
            else if (!this.mc.field_1687.method_8320(blockPos).method_45474()) {
                if (this.head && this.headList.contains(blockPos)) {
                    this.headList.remove(blockPos);
                }
                else if (this.sandList.contains(blockPos)) {
                    this.sandList.remove(blockPos);
                }
                return;
            }
            else {
                this.blocksList.add(((class_2338.class_2339)this.blockPosPool.get()).method_10101((class_2382)blockPos));
                return;
            }
        });
        final Iterator<class_2338.class_2339> iterator6;
        class_2338.class_2339 block2;
        boolean isHead;
        FindItemResult item3;
        final Iterator<class_2338.class_2339> iterator7;
        class_2338.class_2339 blockPos2;
        BlockIterator.after(() -> {
            this.blocksList.sort(Comparator.comparingDouble(value -> Utils.squaredDistance(pX, pY, pZ, value.method_10263() + 0.5, value.method_10264() + 0.5, value.method_10260() + 0.5)));
            if (!this.blocksList.isEmpty()) {
                this.blocksList.iterator();
                while (iterator6.hasNext()) {
                    block2 = iterator6.next();
                    isHead = this.headList.contains(block2);
                    item3 = InvUtils.findInHotbar(itemStack -> this.validItem(itemStack, (class_2338)block2, isHead));
                    if (!item3.found()) {
                        return;
                    }
                    else if (item3.getHand() == null) {
                        this.mc.field_1724.method_31548().field_7545 = item3.slot();
                        return;
                    }
                    else if (this.d > 0) {
                        --this.d;
                        return;
                    }
                    else {
                        this.d = (int)this.delay.get();
                        if (BlockUtils.place((class_2338)block2, item3, (boolean)this.rotate.get(), 50, (boolean)this.swingHand.get(), true)) {
                            this.renderBlocks.add(((RenderBlock)this.renderBlockPool.get()).set((class_2338)block2));
                            if (this.headList.size() == 1) {
                                this.headList.clear();
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            continue;
                        }
                    }
                }
                this.blocksList.iterator();
                while (iterator7.hasNext()) {
                    blockPos2 = iterator7.next();
                    this.blockPosPool.free((Object)blockPos2);
                }
                this.blocksList.clear();
            }
        });
    }
    
    @EventHandler
    private void onMouseButton(final MouseButtonEvent event) {
        if (!(this.mc.field_1724.method_6047().method_7909() instanceof class_1747) || this.mc.field_1755 != null || this.started || event.action != KeyAction.Press || event.button != 1 || ((class_1747)this.mc.field_1724.method_6047().method_7909()).method_7711() != class_2246.field_10114) {
            return;
        }
        event.setCancelled(this.started = true);
    }
    
    @EventHandler
    private void onRender(final Render3DEvent event) {
        if (!(boolean)this.render.get()) {
            return;
        }
        this.renderBlocks.sort(Comparator.comparingInt(o -> -o.ticks));
        this.renderBlocks.forEach(renderBlock -> renderBlock.render(event, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get()));
    }
    
    private boolean validItem(final class_1799 itemStack, final class_2338 pos, final boolean isHead) {
        if (!(itemStack.method_7909() instanceof class_1747)) {
            return false;
        }
        final class_2248 block = ((class_1747)itemStack.method_7909()).method_7711();
        if (isHead) {
            if (block != class_2246.field_10177) {
                return false;
            }
        }
        else if (block != class_2246.field_10114) {
            return false;
        }
        return !(block instanceof class_2346) || !class_2346.method_10128(this.mc.field_1687.method_8320(pos));
    }
    
    private boolean isNameTag(final class_1799 itemStack) {
        return itemStack.method_7909().equals(class_1802.field_8448);
    }
    
    private void interact(final class_1297 entity) {
        if (this.rotate.get()) {
            Rotations.rotate(Rotations.getYaw(entity), Rotations.getPitch(entity), -100, () -> this.mc.field_1761.method_2905((class_1657)this.mc.field_1724, entity, class_1268.field_5808));
        }
        else {
            this.mc.field_1761.method_2905((class_1657)this.mc.field_1724, entity, class_1268.field_5808);
        }
    }
    
    public static class RenderBlock
    {
        public class_2338.class_2339 pos;
        public int ticks;
        
        public RenderBlock() {
            this.pos = new class_2338.class_2339();
        }
        
        public RenderBlock set(final class_2338 blockPos) {
            this.pos.method_10101((class_2382)blockPos);
            this.ticks = 8;
            return this;
        }
        
        public void tick() {
            --this.ticks;
        }
        
        public void render(final Render3DEvent event, final Color sides, final Color lines, final ShapeMode shapeMode) {
            final int preSideA = sides.a;
            final int preLineA = lines.a;
            sides.a *= (int)(this.ticks / 8.0);
            lines.a *= (int)(this.ticks / 8.0);
            event.renderer.box((class_2338)this.pos, sides, lines, shapeMode, 0);
            sides.a = preSideA;
            lines.a = preLineA;
        }
    }
}
