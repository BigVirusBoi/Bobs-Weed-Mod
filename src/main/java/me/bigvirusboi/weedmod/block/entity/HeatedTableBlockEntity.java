package me.bigvirusboi.weedmod.block.entity;

import me.bigvirusboi.weedmod.init.BlockEntityInit;
import me.bigvirusboi.weedmod.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;
import java.util.Set;

public class HeatedTableBlockEntity extends RandomizableContainerBlockEntity {
    private final Random rand = new Random();
    protected NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    protected int TICKS_ON_TABLE = 0;

    public HeatedTableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.TABLE.get(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(1, ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items);
        }
    }

    @Override
    protected Component getDefaultName() {
        return null;
    }

    @Override
    protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
        return null;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public void setItems(NonNullList<ItemStack> itemsIn) {
        this.items = itemsIn;
    }

    public ItemStack setItems(ItemStack stack, int giveBack) {
        ItemStack tableContent = items.get(0).copy();
        ItemStack handItem = stack.copy();
        int tableCount = tableContent.getCount();
        int heldCount = handItem.getCount();
        int totalCount = heldCount + tableCount;

        if (giveBack != 0) {
            ItemStack toPut = tableContent.copy();
            toPut.setCount(64);
            putItems(toPut);
            return new ItemStack(tableContent.getItem(), giveBack);
        } else {
            if (isEmpty()) {
                putItems(handItem);
            } else {
                putItems(new ItemStack(tableContent.getItem(), totalCount));
            }
            return ItemStack.EMPTY;
        }
    }

    private void putItems(ItemStack stack) {
        items.set(0, stack.copy());
        setChanged();
    }

    public ItemStack takeItems() {
        ItemStack stack = items.get(0).copy();
        items.set(0, ItemStack.EMPTY);
        setChanged();
        return stack;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.items.get(0).isEmpty();
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public void startOpen(Player player) {}

    @Override
    public void stopOpen(Player player) {}

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        Item item = stack.getItem();
        return item == ItemInit.CANNABIS_LEAF.get() || item == ItemInit.CANNABIS_BUD.get();
    }



    @Override
    public int countItem(Item item) {
        return super.countItem(item);
    }

    @Override
    public boolean hasAnyOf(Set<Item> items) {
        for (ItemStack item : this.items) {
            if (items.contains(item.getItem())) return true;
        }
        return false;
    }



    public boolean isFull() {
        if (this.isEmpty()) return false;
        return this.items.get(0).getCount() == getMaxStackSize();
    }

    public ItemStack getStack() {
        return this.items.get(0);
    }

    public Item getItem() {
        return getStack().getItem();
    }

    @Override
    public void clearContent() {
        super.clearContent();
        this.items.clear();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, HeatedTableBlockEntity table) {
        if (level != null) {
            ItemStack stack = table.getStack();
            Item item = table.getItem();
            table.TICKS_ON_TABLE++;

            double x = table.worldPosition.getX() + .5 + (table.rand.nextGaussian() / 4);
            double y = table.worldPosition.getY() + .5;
            double z = table.worldPosition.getZ() + .5 + (table.rand.nextGaussian() / 4);

            if (item == ItemInit.CANNABIS_BUD.get()) {
                level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, .1, 0);
                if (table.TICKS_ON_TABLE >= 200) {
                    if (table.rand.nextDouble() < 0.8) {
                        table.dropItem(ItemInit.DRIED_CANNABIS_BUD.get(), level, table.worldPosition);
                    } else {
                        table.dropItem(ItemInit.BURNT_CANNABIS_BUD.get(), level, table.worldPosition);
                    }
                    stack.shrink(1);
                    table.putItems(stack);
                    table.TICKS_ON_TABLE = 100;
                }
            } else if (item == ItemInit.CANNABIS_LEAF.get()) {
                level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, .1, 0);
                if (table.TICKS_ON_TABLE >= 100) {
                    if (table.rand.nextDouble() < 0.8) {
                        table.dropItem(ItemInit.DRIED_CANNABIS_LEAF.get(), level, table.worldPosition);
                    } else {
                        table.dropItem(ItemInit.BURNT_CANNABIS_LEAF.get(), level, table.worldPosition);
                    }
                    stack.shrink(1);
                    table.putItems(stack);
                    table.TICKS_ON_TABLE = 50;
                }
            } else {
                table.TICKS_ON_TABLE = 0;
            }
        }
    }

    /*
    public ItemStack getStackInSlot(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.items, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.items, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemStack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(stack, itemStack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
        if (!flag) {
            this.markDirty();
        }
    }
     */

    private void dropItem(Item item, Level level, BlockPos pos) {
        level.addFreshEntity(new ItemEntity(level, pos.getX() + .5f, pos.getY() + .6f, pos.getZ() + .5f, item.getDefaultInstance()));
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    /*
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(this.getPos(), 1, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(world.getBlockState(pkt.getPos()), pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.read(state, tag);
    }
     */
}
