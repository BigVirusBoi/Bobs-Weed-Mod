package me.bigvirusboi.weedmod.block;

import me.bigvirusboi.weedmod.WeedMod;
import me.bigvirusboi.weedmod.block.entity.HeatedTableBlockEntity;
import me.bigvirusboi.weedmod.init.BlockEntityInit;
import me.bigvirusboi.weedmod.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class HeatedTableBlock extends BaseEntityBlock {
    public HeatedTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityInit.TABLE.get().create(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (hand == InteractionHand.OFF_HAND) return InteractionResult.PASS;

        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof HeatedTableBlockEntity table) {
                ItemStack held = player.getItemInHand(hand);

                if (held.isEmpty()) {
                    if (!table.isEmpty()) {
                        player.setItemInHand(hand, table.takeItems());
                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.PASS;
                } else {
                    Item heldItem = held.getItem();
                    if (heldItem == ItemInit.CANNABIS_LEAF.get() || heldItem == ItemInit.CANNABIS_BUD.get()) {
                        ItemStack tableStack = table.getItems().get(0).copy();
                        int heldCount = held.getCount();
                        int tableCount = tableStack.getCount();
                        int totalCount = heldCount + tableCount;

                        if (tableStack.isEmpty()) {
                            player.setItemInHand(hand, table.setItems(held, 0));
                            return InteractionResult.SUCCESS;
                        }

                        if (tableStack.getItem() == heldItem) {
                            if (totalCount > 64) {
                                // TODO This code block is a big nono
                                int back = tableCount - heldCount;
                                if (back < 0) back = -back;
                                player.setItemInHand(hand, table.setItems(held, back));
                                WeedMod.LOGGER.debug(tableCount + " - " + heldCount + " = " + back);
                                // TODO This code block is a big nono
                            } else {
                                player.setItemInHand(hand, table.setItems(held, 0));
                            }
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof HeatedTableBlockEntity table) {
                Containers.dropContents(level, pos, table.getItems());
            }
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, BlockEntityInit.TABLE.get(), HeatedTableBlockEntity::tick);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or(
                Block.box(2, 0, 2, 4, 7, 4),
                Block.box(12, 0, 12, 14, 7, 14),
                Block.box(1, 7, 1, 15, 9, 15),
                Block.box(12, 0, 2, 14, 7, 4),
                Block.box(2, 0, 12, 4, 7, 14)
        );
    }
}
