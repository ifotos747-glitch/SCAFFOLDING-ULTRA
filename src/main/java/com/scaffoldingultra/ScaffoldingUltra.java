package com.scaffoldingultra;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ArrayDeque;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;

@Mod(ScaffoldingUltra.ID)
public class ScaffoldingUltra {
    public static final String ID = "scaffolding_ultra";
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ID);
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ID);
    private static final Map<String, RegistryObject<Item>> PARTS = new LinkedHashMap<>();

    static {
        add("standard", new double[][] {{6,0,6,10,16,10},{3,2,3,13,3,13},{3,8,3,13,9,13},{3,14,3,13,15,13}});
        add("ledger", new double[][] {{0,7,6,16,9,10},{0,5,5,2,11,11},{14,5,5,16,11,11}});
        add("brace", new double[][] {{0,0,7,4,4,9},{3,3,7,7,7,9},{6,6,7,10,10,9},{9,9,7,13,13,9},{12,12,7,16,16,9}});
        add("deck", new double[][] {{0,0,0,16,3,16}});
        add("base_jack", new double[][] {{2,0,2,14,2,14},{6,2,6,10,12,10},{4,5,4,12,7,12}});
        add("ladder", new double[][] {{2,0,6,4,16,9},{12,0,6,14,16,9},{4,2,6,12,4,9},{4,6,6,12,8,9},{4,10,6,12,12,9},{4,14,6,12,16,9}});
        add("guardrail", new double[][] {{0,5,7,16,7,9},{0,13,7,16,15,9},{0,0,6,2,16,10},{14,0,6,16,16,10}});
        add("stairs", new double[][] {{0,0,0,16,4,4},{0,0,4,16,8,8},{0,0,8,16,12,12},{0,0,12,16,16,16}});
        add("standard_half", new double[][] {{6,0,6,10,8,10},{3,2,3,13,3,13}});
        add("standard_top", new double[][] {{6,0,6,10,16,10},{3,2,3,13,3,13}});
        add("standard_heavy", new double[][] {{5,0,5,11,16,11},{2,3,2,14,5,14},{2,11,2,14,13,14}});
        add("standard_extension", new double[][] {{7,0,7,9,16,9},{5,0,5,11,4,11}});
        add("ledger_half", new double[][] {{0,7,6,8,9,10}});
        add("ledger_double", new double[][] {{0,7,5,16,9,7},{0,7,9,16,9,11}});
        add("ledger_heavy", new double[][] {{0,6,5,16,10,11}});
        add("ledger_end", new double[][] {{0,7,6,16,9,10},{12,4,4,16,12,12}});
        add("brace_reverse", new double[][] {{12,0,7,16,4,9},{9,3,7,13,7,9},{6,6,7,10,10,9},{3,9,7,7,13,9},{0,12,7,4,16,9}});
        add("brace_cross", new double[][] {{0,0,6,4,4,8},{12,0,8,16,4,10},{3,3,6,7,7,8},{9,3,8,13,7,10},{6,6,6,10,10,8},{6,6,8,10,10,10},{9,9,6,13,13,8},{3,9,8,7,13,10},{12,12,6,16,16,8},{0,12,8,4,16,10}});
        add("deck_half", new double[][] {{0,0,0,16,3,8}});
        add("deck_wood", new double[][] {{0,0,0,16,3,16}});
        add("deck_hatch", new double[][] {{0,0,0,4,3,16},{12,0,0,16,3,16},{4,0,0,12,3,4},{4,0,12,12,3,16}});
        add("base_high", new double[][] {{2,0,2,14,2,14},{6,2,6,10,16,10},{4,9,4,12,11,12}});
        add("base_low", new double[][] {{2,0,2,14,2,14},{6,2,6,10,7,10}});
        add("ladder_wide", new double[][] {{0,0,6,2,16,9},{14,0,6,16,16,9},{2,2,6,14,4,9},{2,6,6,14,8,9},{2,10,6,14,12,9},{2,14,6,14,16,9}});
        add("ladder_narrow", new double[][] {{4,0,6,6,16,9},{10,0,6,12,16,9},{6,2,6,10,4,9},{6,6,6,10,8,9},{6,10,6,10,12,9},{6,14,6,10,16,9}});
        add("guardrail_single", new double[][] {{0,13,7,16,15,9}});
        add("toe_board", new double[][] {{0,0,7,16,4,9}});
        add("frame", new double[][] {{0,0,6,3,16,10},{13,0,6,16,16,10},{0,12,6,16,15,10}});
        add("frame_cross", new double[][] {{0,0,6,3,16,10},{13,0,6,16,16,10},{0,12,6,16,15,10},{0,0,7,4,4,9},{3,3,7,7,7,9},{6,6,7,10,10,9},{9,9,7,13,13,9},{12,12,7,16,16,9}});
        add("wheel", new double[][] {{5,0,5,11,2,11},{3,2,4,13,8,12},{5,8,5,11,13,11}});
        add("wheel_locked", new double[][] {{5,0,5,11,2,11},{3,2,4,13,8,12},{5,8,5,11,13,11},{10,8,4,15,10,8}});
        add("safety_net", new double[][] {{0,0,7,1,16,8},{3.5,0,7,4.5,16,8},{7,0,7,8,16,8},{10.5,0,7,11.5,16,8},{14,0,7,15,16,8},{0,0,7,16,1,8},{0,3.5,7,16,4.5,8},{0,7,7,16,8,8},{0,10.5,7,16,11.5,8},{0,14,7,16,15,8}});
        add("coupler", new double[][] {{3,6,6,13,10,10},{6,3,5,10,13,11}});
        add("standard_2", new double[][] {{6,0,6,10,16,10},{3,2,3,13,3,13},{3,8,3,13,9,13},{3,14,3,13,15,13}});
        add("standard_3", new double[][] {{6,0,6,10,16,10},{3,2,3,13,3,13},{3,8,3,13,9,13},{3,14,3,13,15,13}});
        add("standard_4", new double[][] {{6,0,6,10,16,10},{3,2,3,13,3,13},{3,8,3,13,9,13},{3,14,3,13,15,13}});
        add("ledger_2", new double[][] {{0,7,6,16,9,10},{0,5,5,2,11,11},{14,5,5,16,11,11}});
        add("ledger_3", new double[][] {{0,7,6,16,9,10},{0,5,5,2,11,11},{14,5,5,16,11,11}});
        add("ledger_4", new double[][] {{0,7,6,16,9,10},{0,5,5,2,11,11},{14,5,5,16,11,11}});
        add("deck_2", new double[][] {{0,0,0,16,3,16}});
        add("deck_3", new double[][] {{0,0,0,16,3,16}});
        add("deck_4", new double[][] {{0,0,0,16,3,16}});
        add("anchor_wall", new double[][] {{0,5,6,16,11,10},{0,3,3,3,13,13}});
        add("anchor_heavy", new double[][] {{0,4,4,16,12,12},{0,1,1,3,15,15}});
        add("suspension_rod", new double[][] {{7,0,7,9,16,9},{4,13,4,12,16,12}});
        add("suspension_chain", new double[][] {{6,0,6,10,4,10},{7,4,6,9,8,10},{6,8,6,10,12,10},{7,12,6,9,16,10}});
        add("suspension_beam", new double[][] {{0,12,5,16,16,11},{1,0,7,3,12,9},{13,0,7,15,12,9}});
        add("hanging_deck", new double[][] {{0,0,0,16,3,16},{0,3,0,2,16,2},{14,3,14,16,16,16}});
        add("mobile_base", new double[][] {{0,5,0,16,8,16},{1,0,1,5,5,5},{11,0,11,15,5,15}});
        add("outrigger", new double[][] {{0,1,6,16,4,10},{12,0,3,16,2,13}});
        add("wheel_heavy", new double[][] {{4,0,4,12,10,12},{5,10,5,11,16,11}});
        add("crane_base", new double[][] {{0,0,0,16,5,16},{3,5,3,13,16,13}});
        add("crane_mast", new double[][] {{1,0,1,4,16,4},{12,0,1,15,16,4},{1,0,12,4,16,15},{12,0,12,15,16,15},{1,1,1,15,3,15},{1,13,1,15,15,15}});
        add("crane_jib", new double[][] {{0,4,2,16,6,4},{0,4,12,16,6,14},{0,12,7,16,14,9},{0,5,3,2,13,13},{14,5,3,16,13,13}});
        add("crane_counterweight", new double[][] {{1,2,1,15,14,15}});
        add("crane_hook", new double[][] {{7,7,7,9,16,9},{5,3,6,11,7,10},{9,3,6,11,9,10}});
        add("crane_cabin", new double[][] {{1,0,1,15,3,15},{1,13,1,15,16,15},{1,3,1,4,13,4},{12,3,12,15,13,15}});
        add("storage_rack", new double[][] {{1,0,1,3,16,3},{13,0,13,15,16,15},{0,1,0,16,3,16},{0,8,0,16,10,16}});
        add("material_crate", new double[][] {{0,0,0,16,12,16}});
        add("steel_pallet", new double[][] {{0,2,0,16,4,16},{0,0,0,3,2,16},{13,0,0,16,2,16}});
        add("wood_pallet", new double[][] {{0,2,0,16,4,16},{0,0,0,3,2,16},{13,0,0,16,2,16}});
        add("beam_i", new double[][] {{0,2,3,16,4,13},{0,4,7,16,12,9},{0,12,3,16,14,13}});
        add("beam_box", new double[][] {{0,4,4,16,12,12}});
        add("frame_tall", new double[][] {{1,0,5,4,16,11},{12,0,5,15,16,11},{1,13,5,15,16,11}});
        add("frame_end", new double[][] {{1,0,6,4,16,10},{12,0,6,15,16,10},{1,6,6,15,9,10}});
        add("deck_grating", new double[][] {{0,0,0,16,3,1},{0,0,2,16,3,3},{0,0,4,16,3,5},{0,0,6,16,3,7},{0,0,8,16,3,9},{0,0,10,16,3,11},{0,0,12,16,3,13},{0,0,14,16,3,15}});
        add("deck_ramp", new double[][] {{0,0,0,16,2,2},{0,0,2,16,4,4},{0,0,4,16,6,6},{0,0,6,16,8,8},{0,0,8,16,10,10},{0,0,10,16,12,12},{0,0,12,16,14,14},{0,0,14,16,16,16}});
        add("barrier", new double[][] {{0,0,5,16,3,11},{0,9,6,16,13,10},{1,3,6,3,9,10},{13,3,6,15,9,10}});
        add("net_dense", new double[][] {{0,0,7,0.5,16,8},{0,0,7,16,0.5,8},{2,0,7,2.5,16,8},{0,2,7,16,2.5,8},{4,0,7,4.5,16,8},{0,4,7,16,4.5,8},{6,0,7,6.5,16,8},{0,6,7,16,6.5,8},{8,0,7,8.5,16,8},{0,8,7,16,8.5,8},{10,0,7,10.5,16,8},{0,10,7,16,10.5,8},{12,0,7,12.5,16,8},{0,12,7,16,12.5,8},{14,0,7,14.5,16,8},{0,14,7,16,14.5,8}});
        add("roof_panel", new double[][] {{0,13,0,16,16,16},{1,0,1,3,13,3},{13,0,13,15,13,15}});
    }

    private static void add(String name, double[][] boxes) {
        RegistryObject<Block> block = BLOCKS.register(name, () -> new Part(boxes, name.startsWith("ladder")));
        PARTS.put(name, ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties())));
    }

    private static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new AssemblyTool(false));
    private static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", () -> new AssemblyTool(true));
    private static final RegistryObject<Item> BUILDER = ITEMS.register("builder", () -> new IndustrialTool(0));
    private static final RegistryObject<Item> INSPECTOR = ITEMS.register("inspector", () -> new IndustrialTool(1));
    private static final RegistryObject<Item> CRANE = ITEMS.register("crane_controller", () -> new IndustrialTool(2));
    private static final RegistryObject<Item> MOVER = ITEMS.register("mobile_controller", () -> new IndustrialTool(3));
    private static final RegistryObject<CreativeModeTab> TAB = TABS.register("construction", () ->
        CreativeModeTab.builder().title(Component.translatable("itemGroup.scaffolding_ultra"))
            .icon(() -> new ItemStack(PARTS.get("standard").get()))
            .displayItems((params, output) -> { PARTS.values().forEach(item -> output.accept(item.get())); output.accept(HAMMER.get()); output.accept(WRENCH.get()); output.accept(BUILDER.get()); output.accept(INSPECTOR.get()); output.accept(CRANE.get()); output.accept(MOVER.get()); })
            .build());

    public ScaffoldingUltra() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TABS.register(bus);
    }

    public static class AssemblyTool extends Item {
        private final boolean rotate;
        public AssemblyTool(boolean rotate) { super(new Item.Properties().stacksTo(1)); this.rotate = rotate; }
        @Override public InteractionResult useOn(UseOnContext context) {
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            BlockState state = level.getBlockState(pos);
            if (!(state.getBlock() instanceof Part)) return InteractionResult.PASS;
            if (!level.isClientSide) {
                if (rotate) level.setBlock(pos, state.setValue(Part.FACING,state.getValue(Part.FACING).getClockWise()),3);
                else level.destroyBlock(pos,true,context.getPlayer());
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }
    // Explicit, bounded assembly operations. No forced chunk loads.
    private static boolean valid(Level level, Player player, BlockPos pos) {
        return level.isInWorldBounds(pos) && level.hasChunkAt(pos) && level.mayInteract(player,pos) && player.mayBuild();
    }
    private static int inventoryCount(Player p, Item item) {
        int total=0;
        for(int i=0;i<p.getInventory().getContainerSize();i++){
            ItemStack stack=p.getInventory().getItem(i);
            if(stack.is(item)) total+=stack.getCount();
        }
        return total;
    }
    private static void consume(Player p, Item item, int count) {
        if(p.getAbilities().instabuild) return;
        for(int i=0;i<p.getInventory().getContainerSize() && count>0;i++){
            ItemStack stack=p.getInventory().getItem(i);
            if(stack.is(item)){int n=Math.min(count,stack.getCount()); stack.shrink(n); count-=n;}
        }
    }
    private static ArrayList<BlockPos> component(Level level, BlockPos start) {
        var seen=new HashSet<BlockPos>();
        var queue=new ArrayDeque<BlockPos>();
        var result=new ArrayList<BlockPos>();
        queue.add(start); seen.add(start);
        while(!queue.isEmpty() && result.size()<129){
            BlockPos pos=queue.remove();
            if(!level.hasChunkAt(pos) || !(level.getBlockState(pos).getBlock() instanceof Part)) continue;
            result.add(pos);
            for(Direction d:Direction.values()){BlockPos next=pos.relative(d); if(seen.add(next)) queue.add(next);}
        }
        return result;
    }
    private static boolean solidSupport(Level level, BlockPos pos, Direction face) {
        if(!level.hasChunkAt(pos)) return false;
        BlockState state=level.getBlockState(pos);
        return !(state.getBlock() instanceof Part) && state.isFaceSturdy(level,pos,face);
    }
    public static class IndustrialTool extends Item {
        private final int mode;
        public IndustrialTool(int mode){super(new Item.Properties().stacksTo(1));this.mode=mode;}
        @Override public InteractionResult useOn(UseOnContext ctx) {
            Level level=ctx.getLevel(); Player p=ctx.getPlayer(); BlockPos start=ctx.getClickedPos();
            if(p==null || !valid(level,p,start)) return InteractionResult.PASS;
            if(level.isClientSide) return InteractionResult.SUCCESS;
            BlockState state=level.getBlockState(start);
            if(mode==0){
                ItemStack held=p.getOffhandItem();
                if(!(held.getItem() instanceof BlockItem bi) || !(bi.getBlock() instanceof Part)){
                    p.displayClientMessage(Component.literal("Hold a scaffold block in your offhand."),true);return InteractionResult.SUCCESS;
                }
                String id=ForgeRegistries.BLOCKS.getKey(bi.getBlock()).getPath();
                int length=id.matches(".*_[234]") ? Integer.parseInt(id.substring(id.length()-1)) : 3;
                Direction direction=id.startsWith("standard") ? Direction.UP : p.getDirection();
                BlockPos origin=start.relative(ctx.getClickedFace());
                BlockState part=bi.getBlock().defaultBlockState().setValue(Part.FACING,direction.getAxis().isVertical()?p.getDirection():direction.getClockWise());
                for(int i=0;i<length;i++){BlockPos pos=origin.relative(direction,i);if(!valid(level,p,pos)||!level.getBlockState(pos).isAir()){p.displayClientMessage(Component.literal("Assembly blocked."),true);return InteractionResult.SUCCESS;}}
                if(!p.getAbilities().instabuild && inventoryCount(p,bi)<length){p.displayClientMessage(Component.literal("Need "+length+" matching parts."),true);return InteractionResult.SUCCESS;}
                for(int i=0;i<length;i++) level.setBlock(origin.relative(direction,i),part,3);
                consume(p,bi,length);p.displayClientMessage(Component.literal("Assembled "+length+" parts."),true);
                return InteractionResult.SUCCESS;
            }
            if(!(state.getBlock() instanceof Part)) return InteractionResult.PASS;
            var blocks=component(level,start);
            if(blocks.size()>128){p.displayClientMessage(Component.literal("Maximum 128 connected blocks."),true);return InteractionResult.SUCCESS;}
            if(mode==1){
                boolean supported=false;
                for(BlockPos pos:blocks){
                    if(solidSupport(level,pos.below(),Direction.UP)) supported=true;
                    String id=ForgeRegistries.BLOCKS.getKey(level.getBlockState(pos).getBlock()).getPath();
                    if(id.startsWith("anchor")) for(Direction d:Direction.Plane.HORIZONTAL) if(solidSupport(level,pos.relative(d),d.getOpposite())) supported=true;
                    if(id.startsWith("suspension")) if(solidSupport(level,pos.above(),Direction.DOWN)) supported=true;
                }
                p.displayClientMessage(Component.literal("Inspection: "+blocks.size()+" parts | "+(supported?"SUPPORTED":"UNSUPPORTED")),false);
                return InteractionResult.SUCCESS;
            }
            Direction shift=mode==2?(p.isShiftKeyDown()?Direction.DOWN:Direction.UP):p.getDirection();
            String startId=ForgeRegistries.BLOCKS.getKey(state.getBlock()).getPath();
            if(mode==2 && !startId.equals("crane_hook")){p.displayClientMessage(Component.literal("Click a crane hook."),true);return InteractionResult.SUCCESS;}
            if(mode==3 && !startId.equals("mobile_base")){p.displayClientMessage(Component.literal("Click a mobile base."),true);return InteractionResult.SUCCESS;}
            if(mode==3 && blocks.stream().anyMatch(pos -> ForgeRegistries.BLOCKS.getKey(level.getBlockState(pos).getBlock()).getPath().equals("wheel_locked"))){p.displayClientMessage(Component.literal("Release locked wheels first."),true);return InteractionResult.SUCCESS;}
            var source=new HashSet<>(blocks);
            var states=new LinkedHashMap<BlockPos,BlockState>();
            for(BlockPos pos:blocks){
                BlockPos dest=pos.relative(shift);
                if(!valid(level,p,pos)||!valid(level,p,dest)||(!source.contains(dest)&&!level.getBlockState(dest).isAir())){p.displayClientMessage(Component.literal("Movement blocked."),true);return InteractionResult.SUCCESS;}
                states.put(dest,level.getBlockState(pos));
            }
            for(BlockPos pos:blocks) level.setBlock(pos,Blocks.AIR.defaultBlockState(),2);
            states.forEach((pos,part)->level.setBlock(pos,part,3));
            p.displayClientMessage(Component.literal("Moved "+blocks.size()+" parts."),true);
            return InteractionResult.SUCCESS;
        }
    }
    public static class Part extends Block {
        public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
        private final Map<Direction,VoxelShape> shapes = new java.util.EnumMap<>(Direction.class);
        private final boolean climbable;
        public Part(double[][] boxes, boolean climbable) {
            super(BlockBehaviour.Properties.of().strength(2.5f, 6f).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops());
            this.climbable = climbable;
            registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                VoxelShape shape = Shapes.empty();
                for (double[] b : boxes) {
                    double x1=b[0], z1=b[2], x2=b[3], z2=b[5];
                    int turns = switch(facing) { case EAST -> 1; case SOUTH -> 2; case WEST -> 3; default -> 0; };
                    for(int t=0;t<turns;t++){
                        double nx1=16-z2, nz1=x1, nx2=16-z1, nz2=x2;
                        x1=nx1; z1=nz1; x2=nx2; z2=nz2;
                    }
                    shape=Shapes.or(shape,Block.box(x1,b[1],z1,x2,b[4],z2));
                }
                shapes.put(facing,shape);
            }
        }
        @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState> builder){builder.add(FACING);}
        @Override public BlockState getStateForPlacement(BlockPlaceContext context){BlockState neighbor = context.getLevel().getBlockState(context.getClickedPos().relative(context.getClickedFace().getOpposite()));
            Direction facing = neighbor.getBlock() instanceof Part && !context.isSecondaryUseActive() ? neighbor.getValue(FACING) : context.getHorizontalDirection().getOpposite();
            return defaultBlockState().setValue(FACING,facing);}
        @Override protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context){return shapes.get(state.getValue(FACING));}
        @Override protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context){return climbable ? Shapes.empty() : getShape(state,level,pos,context);}
    }
}
