package me.bigvirusboi.weedmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Random;

// TODO just override cropblock pls

public class CannabisPlantBlock extends Block implements IPlantable {
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
    protected static final VoxelShape[] SHAPE = new VoxelShape[] {
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D),
    };

    public CannabisPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(TOP, false));
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE[state.getValue(this.getAgeProperty())];
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random rand) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random rand) {
        if (level.isEmptyBlock(pos.above())) {
            int i;
            for (i = 1; level.getBlockState(pos.below(i)).is(this); ++i) { }

            if (i < 3) {
                int j = state.getValue(AGE);
                if (j != 3) {
                    if (ForgeHooks.onCropsGrowPre(level, pos, state, true)) {
                        if (j == 2) {
                            level.setBlockAndUpdate(pos.above(), this.defaultBlockState());
                            level.setBlock(pos, state.setValue(AGE, 3), 4);
                        } else {
                            level.setBlock(pos, state.setValue(AGE, j + 1), 4);
                        }
                    }
                }
            } else if (i == 3) {
                int j = state.getValue(AGE);
                if (j != 3) {
                    if (ForgeHooks.onCropsGrowPre(level, pos, state, true)) {
                        level.setBlock(pos, state.setValue(AGE, j + 1).setValue(TOP, j == 2), 4);
                    }
                }
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (!state.canSurvive(level, currentPos)) {
            level.scheduleTick(currentPos, this, 1);
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos.below());
        if (blockstate.getBlock() == this) {
            return true;
        } else {
            return blockstate.is(Blocks.COARSE_DIRT) || blockstate.is(Blocks.GRASS_BLOCK);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, TOP);
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.PLAINS;
    }

    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return defaultBlockState();
    }
}
