// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.modules;

import net.minecraft.class_2382;
import net.minecraft.class_2769;
import net.minecraft.class_2281;
import net.minecraft.class_2745;
import net.minecraft.class_2680;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.events.entity.player.InteractBlockEvent;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_746;
import net.minecraft.class_636;
import net.minecraft.class_1263;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_3965;
import net.minecraft.class_1268;
import java.util.Comparator;
import meteordevelopment.meteorclient.utils.world.BlockIterator;
import net.minecraft.class_2246;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import net.minecraft.class_1707;
import net.minecraft.class_476;
import meteordevelopment.meteorclient.events.world.TickEvent;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Objects;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.ItemListSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import net.minecraft.class_2338;
import meteordevelopment.meteorclient.utils.misc.Pool;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import net.minecraft.class_1792;
import java.util.List;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class ChestStealerAura extends Module
{
    private final SettingGroup sgGeneral;
    private final SettingGroup sgRender;
    private final Setting<Double> range;
    private final Setting<List<class_1792>> items;
    private final Setting<Mode> mode;
    private final Setting<Boolean> render;
    private final Setting<SettingColor> lineColor;
    private final Setting<SettingColor> sideColor;
    private final Setting<ShapeMode> shapeMode;
    private final Pool<class_2338.class_2339> blockPosPool;
    private final List<class_2338.class_2339> blocksList;
    private final List<class_2338> openedChestList;
    private final Pool<RenderBlock> renderBlockPool;
    private final List<RenderBlock> renderBlocks;
    private class_2338 chestBlock;
    private int delay1;
    private int delay2;
    private int delay3;
    private boolean isChested;
    private String address;
    
    public ChestStealerAura() {
        super(NAddOn.FIFTH_COLUMN_CATEGORY, "ChestStealerAura", "steal2win");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.range = (Setting<Double>)this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("range")).description("range to steal")).defaultValue(4.5).min(1.0).max(6.0).build());
        this.items = (Setting<List<class_1792>>)this.sgGeneral.add((Setting)((ItemListSetting.Builder)((ItemListSetting.Builder)new ItemListSetting.Builder().name("items")).description("items to steal")).build());
        this.mode = (Setting<Mode>)this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)new EnumSetting.Builder().name("mode")).defaultValue((Object)Mode.Aura)).build());
        this.render = (Setting<Boolean>)this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("render")).description("shows what chests have been stolen from")).defaultValue((Object)true)).build());
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
        this.openedChestList = new ArrayList<class_2338>();
        this.renderBlockPool = (Pool<RenderBlock>)new Pool(RenderBlock::new);
        this.renderBlocks = new ArrayList<RenderBlock>();
        this.chestBlock = null;
        this.delay1 = 0;
        this.delay2 = 0;
        this.delay3 = 0;
        this.isChested = false;
        this.address = null;
    }
    
    public void onActivate() {
        super.onActivate();
        for (final RenderBlock renderBlock : this.renderBlocks) {
            this.renderBlockPool.free((Object)renderBlock);
        }
        this.renderBlocks.clear();
    }
    
    public void onDeactivate() {
        super.onDeactivate();
        for (final RenderBlock renderBlock : this.renderBlocks) {
            this.renderBlockPool.free((Object)renderBlock);
        }
        this.renderBlocks.clear();
    }
    
    @EventHandler
    private void onTickPre(final TickEvent.Pre event) {
        if (this.mc.method_1558() != null && !this.mc.method_1558().field_3761.equals(this.address)) {
            this.address = this.mc.method_1558().field_3761;
            this.openedChestList.clear();
        }
        this.renderBlocks.forEach(RenderBlock::tick);
        this.renderBlocks.removeIf(renderBlock -> renderBlock.ticks <= 0);
        for (final class_2338 c : this.openedChestList) {
            this.renderBlocks.add(((RenderBlock)this.renderBlockPool.get()).set(c, 1));
        }
        if (this.delay3 >= 1) {
            --this.delay3;
            return;
        }
        if (this.isChested) {
            if (!(this.mc.field_1755 instanceof class_476)) {
                this.isChested = false;
                this.mc.field_1724.method_3137();
                this.delay2 = 3;
                return;
            }
            final class_1707 container = (class_1707)((class_476)this.mc.field_1755).method_17577();
            final class_1263 inv = container.method_7629();
            if (!inv.method_5442() && inv.method_18862((Set)new HashSet((Collection)this.items.get()))) {
                for (int i = 0; i < inv.method_5439(); ++i) {
                    if (((List)this.items.get()).contains(inv.method_5438(i).method_7909())) {
                        InvUtils.quickSwap().slotId(i);
                        this.delay3 = 2;
                        return;
                    }
                }
                return;
            }
            this.isChested = false;
            this.openedChestList.add(this.chestBlock);
            final class_2350 dir = this.getDirectionToOtherChestHalf(this.mc.field_1687.method_8320(this.chestBlock));
            if (dir != null) {
                switch (dir) {
                    case field_11043: {
                        this.openedChestList.add(new class_2338(this.chestBlock.method_10263(), this.chestBlock.method_10264(), this.chestBlock.method_10260() - 1));
                        break;
                    }
                    case field_11035: {
                        this.openedChestList.add(new class_2338(this.chestBlock.method_10263(), this.chestBlock.method_10264(), this.chestBlock.method_10260() + 1));
                        break;
                    }
                    case field_11034: {
                        this.openedChestList.add(new class_2338(this.chestBlock.method_10263() + 1, this.chestBlock.method_10264(), this.chestBlock.method_10260()));
                        break;
                    }
                    case field_11039: {
                        this.openedChestList.add(new class_2338(this.chestBlock.method_10263() - 1, this.chestBlock.method_10264(), this.chestBlock.method_10260()));
                        break;
                    }
                }
            }
            this.mc.field_1724.method_3137();
            this.delay2 = 5;
        }
        else {
            if (this.delay2 >= 0) {
                --this.delay2;
                return;
            }
            if (this.mode.get() != Mode.Aura) {
                return;
            }
            final double pX = this.mc.field_1724.method_23317();
            final double pY = this.mc.field_1724.method_23318();
            final double pZ = this.mc.field_1724.method_23321();
            final double rangeSq = Math.pow((double)this.range.get(), 2.0);
            BlockIterator.register((int)Math.ceil((double)this.range.get()), (int)Math.ceil((double)this.range.get()), (blockPos, blockState) -> {
                if (Utils.squaredDistance(pX, pY, pZ, ((class_2338)blockPos).method_10263() + 0.5, ((class_2338)blockPos).method_10264() + 0.5, ((class_2338)blockPos).method_10260() + 0.5) > rangeSq || (this.mc.field_1687.method_8320((class_2338)blockPos).method_26204() != class_2246.field_10034 && this.mc.field_1687.method_8320((class_2338)blockPos).method_26204() != class_2246.field_16328) || this.openedChestList.contains(blockPos)) {
                    return;
                }
                else {
                    this.blocksList.add(((class_2338.class_2339)this.blockPosPool.get()).method_10101(blockPos));
                    return;
                }
            });
            int count;
            final Iterator<class_2338.class_2339> iterator2;
            class_2338.class_2339 block;
            class_636 field_1761;
            class_746 field_1762;
            class_1268 field_1763;
            final class_3965 class_3965;
            final Iterator<class_2338.class_2339> iterator3;
            class_2338.class_2339 blockPos2;
            BlockIterator.after(() -> {
                this.blocksList.sort(Comparator.comparingDouble(value -> Utils.squaredDistance(pX, pY, pZ, value.method_10263() + 0.5, value.method_10264() + 0.5, value.method_10260() + 0.5)));
                if (!this.blocksList.isEmpty()) {
                    count = 0;
                    this.blocksList.iterator();
                    while (iterator2.hasNext()) {
                        block = iterator2.next();
                        if (count >= 1) {
                            break;
                        }
                        else if (this.delay1 < 3) {
                            ++this.delay1;
                            break;
                        }
                        else {
                            this.delay1 = 0;
                            field_1761 = this.mc.field_1761;
                            field_1762 = this.mc.field_1724;
                            field_1763 = class_1268.field_5808;
                            new class_3965(new class_243(block.method_10263() + 0.5, block.method_10264() + 0.5, block.method_10260() + 0.5), class_2350.field_11036, (class_2338)block, true);
                            if (field_1761.method_2896(field_1762, field_1763, class_3965).method_23665()) {
                                ++count;
                                this.chestBlock = new class_2338(block.method_10263(), block.method_10264(), block.method_10260());
                                this.isChested = true;
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
    }
    
    @EventHandler
    private void onInteractBlock(final InteractBlockEvent event) {
        if (this.mode.get() != Mode.Manual) {
            return;
        }
        this.chestBlock = event.result.method_17777();
        this.isChested = true;
    }
    
    public WWidget getWidget(final GuiTheme theme) {
        final WButton button;
        final WButton butt = button = theme.button("Reset opened chests list");
        final List<class_2338> openedChestList = this.openedChestList;
        Objects.requireNonNull(openedChestList);
        button.action = openedChestList::clear;
        return (WWidget)butt;
    }
    
    @EventHandler
    private void onRender(final Render3DEvent event) {
        if (!(boolean)this.render.get()) {
            return;
        }
        this.renderBlocks.sort(Comparator.comparingInt(o -> -o.ticks));
        this.renderBlocks.forEach(renderBlock -> renderBlock.render(event, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get()));
    }
    
    private class_2350 getDirectionToOtherChestHalf(final class_2680 blockState) {
        class_2745 chestType;
        try {
            chestType = (class_2745)blockState.method_11654((class_2769)class_2281.field_10770);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
        return (chestType == class_2745.field_12569) ? null : class_2281.method_9758(blockState);
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
            final int preSideA = sides.a;
            final int preLineA = lines.a;
            sides.a *= (int)(this.ticks / (double)this.ticksMax);
            lines.a *= (int)(this.ticks / (double)this.ticksMax);
            event.renderer.box((class_2338)this.pos, sides, lines, shapeMode, 0);
            sides.a = preSideA;
            lines.a = preLineA;
        }
    }
    
    public enum Mode
    {
        Aura, 
        Manual;
        
        private static /* synthetic */ Mode[] $values() {
            return new Mode[] { Mode.Aura, Mode.Manual };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
