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
    }

    private static void add(String name, double[][] boxes) {
        RegistryObject<Block> block = BLOCKS.register(name, () -> new Part(boxes, name.startsWith("ladder")));
        PARTS.put(name, ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties())));
    }

    private static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new AssemblyTool(false));
    private static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", () -> new AssemblyTool(true));
    private static final RegistryObject<CreativeModeTab> TAB = TABS.register("construction", () ->
        CreativeModeTab.builder().title(Component.translatable("itemGroup.scaffolding_ultra"))
            .icon(() -> new ItemStack(PARTS.get("standard").get()))
            .displayItems((params, output) -> { PARTS.values().forEach(item -> output.accept(item.get())); output.accept(HAMMER.get()); output.accept(WRENCH.get()); })
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
