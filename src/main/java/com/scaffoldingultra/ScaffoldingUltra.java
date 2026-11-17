package com.scaffoldingultra;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
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
    }

    private static void add(String name, double[][] boxes) {
        RegistryObject<Block> block = BLOCKS.register(name, () -> new Part(boxes));
        PARTS.put(name, ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties())));
    }

    private static final RegistryObject<CreativeModeTab> TAB = TABS.register("construction", () ->
        CreativeModeTab.builder().title(Component.translatable("itemGroup.scaffolding_ultra"))
            .icon(() -> new ItemStack(PARTS.get("standard").get()))
            .displayItems((params, output) -> PARTS.values().forEach(item -> output.accept(item.get())))
            .build());

    public ScaffoldingUltra() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TABS.register(bus);
    }

    public static class Part extends Block {
        public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
        private final Map<Direction,VoxelShape> shapes = new java.util.EnumMap<>(Direction.class);
        public Part(double[][] boxes) {
            super(BlockBehaviour.Properties.of().strength(2.5f, 6f).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops());
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
        @Override public BlockState getStateForPlacement(BlockPlaceContext context){return defaultBlockState().setValue(FACING,context.getHorizontalDirection().getOpposite());}
        @Override protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context){return shapes.get(state.getValue(FACING));}
        @Override protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context){return getShape(state,level,pos,context);}
    }
}
