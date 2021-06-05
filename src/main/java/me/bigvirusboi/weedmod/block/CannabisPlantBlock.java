package me.bigvirusboi.weedmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class CannabisPlantBlock extends Block implements net.minecraftforge.common.IPlantable {
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
    protected static final VoxelShape[] SHAPE = new VoxelShape[] {
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),
            Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D),
    };

    public CannabisPlantBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, 0).with(TOP, false));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE[state.get(this.getAgeProperty())];
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!state.isValidPosition(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (worldIn.isAirBlock(pos.up())) {
            int i;
            for (i = 1; worldIn.getBlockState(pos.down(i)).matchesBlock(this); ++i) { }

            if (i < 3) {
                int j = state.get(AGE);
                if (j != 3) {
                    if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
                        if (j == 2) {
                            worldIn.setBlockState(pos.up(), this.getDefaultState());
                            worldIn.setBlockState(pos, state.with(AGE, 3), Constants.BlockFlags.BLOCK_UPDATE);
                        } else {
                            worldIn.setBlockState(pos, state.with(AGE, j + 1), Constants.BlockFlags.BLOCK_UPDATE);
                        }
                    }
                }
            } else if (i == 3) {
                int j = state.get(AGE);
                if (j != 3) {
                    if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
                        worldIn.setBlockState(pos, state.with(AGE, j + 1).with(TOP, j == 2), Constants.BlockFlags.BLOCK_UPDATE);
                    }
                }
            }
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.isValidPosition(worldIn, currentPos)) {
            worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos.down());
        if (blockstate.getBlock() == this) {
            return true;
        } else {
            return blockstate.matchesBlock(Blocks.COARSE_DIRT) || blockstate.matchesBlock(Blocks.GRASS_BLOCK);
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, TOP);
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.PLAINS;
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return getDefaultState();
    }
}
