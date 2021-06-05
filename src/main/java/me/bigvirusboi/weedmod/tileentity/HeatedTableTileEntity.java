package me.bigvirusboi.weedmod.tileentity;

import me.bigvirusboi.weedmod.init.ItemInit;
import me.bigvirusboi.weedmod.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Random;

public class HeatedTableTileEntity extends LockableLootTileEntity implements ITickableTileEntity {
    private final Random rand = new Random();
    protected NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    protected int TICKS_ON_TABLE = 0;

    public HeatedTableTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public HeatedTableTileEntity() {
        this(TileEntityInit.TABLE.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.items);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.items);
        return compound;
    }

    @Override
    protected ITextComponent getDefaultName() {
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

    @Override
    public void markDirty() {
        super.markDirty();
        this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return null;
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return null;
    }

    @Override
    public int getSizeInventory() {
        return this.items.size();
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
        markDirty();
    }

    public ItemStack takeItems() {
        ItemStack stack = items.get(0).copy();
        items.set(0, ItemStack.EMPTY);
        markDirty();
        return stack;
    }

    @Override
    public boolean isEmpty() {
        return this.items.get(0).isEmpty();
    }

    public boolean isFull() {
        if (this.isEmpty()) return false;
        return this.items.get(0).getCount() == 64;
    }

    public ItemStack getStack() {
        return this.items.get(0);
    }

    public Item getItem() {
        return getStack().getItem();
    }

    @Override
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

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        Item item = stack.getItem();
        return item == ItemInit.CANNABIS_LEAF.get() || item == ItemInit.CANNABIS_BUD.get();
    }

    @Override
    public void clear() {
        super.clear();
        this.items.clear();
    }

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

    @Override
    public void tick() {
        if (world != null) {
            ItemStack stack = getStack();
            Item item = getItem();
            TICKS_ON_TABLE++;

            double x = pos.getX() + .5 + (rand.nextGaussian() / 4);
            double y = pos.getY() + .5;
            double z = pos.getZ() + .5 + (rand.nextGaussian() / 4);

            if (item == ItemInit.CANNABIS_BUD.get()) {
                world.addParticle(ParticleTypes.SMOKE, x, y, z, 0, .1, 0);
                if (TICKS_ON_TABLE >= 200) {
                    if (rand.nextDouble() < 0.8) {
                        dropItem(ItemInit.DRIED_CANNABIS_BUD.get(), world, pos);
                    } else {
                        dropItem(ItemInit.BURNT_CANNABIS_BUD.get(), world, pos);
                    }
                    stack.shrink(1);
                    putItems(stack);
                    TICKS_ON_TABLE = 100;
                }
            } else if (item == ItemInit.CANNABIS_LEAF.get()) {
                world.addParticle(ParticleTypes.SMOKE, x, y, z, 0, .1, 0);
                if (TICKS_ON_TABLE >= 100) {
                    if (rand.nextDouble() < 0.8) {
                        dropItem(ItemInit.DRIED_CANNABIS_LEAF.get(), world, pos);
                    } else {
                        dropItem(ItemInit.BURNT_CANNABIS_LEAF.get(), world, pos);
                    }
                    stack.shrink(1);
                    putItems(stack);
                    TICKS_ON_TABLE = 50;
                }
            } else {
                TICKS_ON_TABLE = 0;
            }
        }
    }

    private void dropItem(Item item, World world, BlockPos pos) {
        world.addEntity(new ItemEntity(world, pos.getX() + .5f, pos.getY() + .6f, pos.getZ() + .5f, item.getDefaultInstance()));
    }
}
