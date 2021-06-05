package me.bigvirusboi.weedmod.block;

import me.bigvirusboi.weedmod.WeedMod;
import me.bigvirusboi.weedmod.init.ItemInit;
import me.bigvirusboi.weedmod.init.TileEntityInit;
import me.bigvirusboi.weedmod.tileentity.HeatedTableTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class HeatedTableBlock extends Block {
    public HeatedTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityInit.TABLE.get().create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (handIn == Hand.OFF_HAND) return ActionResultType.PASS;

        if (!worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof HeatedTableTileEntity) {
                ItemStack held = player.getHeldItem(handIn);
                HeatedTableTileEntity table = (HeatedTableTileEntity) tile;

                if (held.isEmpty()) {
                    if (!table.isEmpty()) {
                        player.setHeldItem(handIn, table.takeItems());
                        return ActionResultType.SUCCESS;
                    } else return ActionResultType.PASS;
                } else {
                    Item heldItem = held.getItem();
                    if (heldItem == ItemInit.CANNABIS_LEAF.get() || heldItem == ItemInit.CANNABIS_BUD.get()) {
                        ItemStack tableStack = table.getItems().get(0).copy();
                        int heldCount = held.getCount();
                        int tableCount = tableStack.getCount();
                        int totalCount = heldCount + tableCount;

                        if (tableStack.isEmpty()) {
                            player.setHeldItem(handIn, table.setItems(held, 0));
                            return ActionResultType.SUCCESS;
                        }

                        if (tableStack.getItem() == heldItem) {
                            if (totalCount > 64) {
                                // TODO This code block is a big nono
                                int back = tableCount - heldCount;
                                if (back < 0) back = -back;
                                player.setHeldItem(handIn, table.setItems(held, back));
                                WeedMod.LOGGER.debug(tableCount + " - " + heldCount + " = " + back);
                                // TODO This code block is a big nono
                            } else {
                                player.setHeldItem(handIn, table.setItems(held, 0));
                            }
                            return ActionResultType.SUCCESS;
                        }
                    }
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof HeatedTableTileEntity) {
                InventoryHelper.dropItems(worldIn, pos, ((HeatedTableTileEntity) tile).getItems());
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Stream.of(
                Block.makeCuboidShape(2, 0, 2, 4, 7, 4),
                Block.makeCuboidShape(12, 0, 12, 14, 7, 14),
                Block.makeCuboidShape(1, 7, 1, 15, 9, 15),
                Block.makeCuboidShape(12, 0, 2, 14, 7, 4),
                Block.makeCuboidShape(2, 0, 12, 4, 7, 14)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    }
}
