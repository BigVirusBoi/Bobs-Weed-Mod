package me.bigvirusboi.weedmod.init;

import me.bigvirusboi.weedmod.WeedMod;
import me.bigvirusboi.weedmod.item.JointItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WeedMod.MOD_ID);

    public static final RegistryObject<Item> CANNABIS_LEAF = ITEMS.register("cannabis_leaf", () ->
            new Item(new Item.Properties().group(WeedMod.GROUP)));
    public static final RegistryObject<Item> DRIED_CANNABIS_LEAF = ITEMS.register("dried_cannabis_leaf", () ->
            new Item(new Item.Properties().group(WeedMod.GROUP)));
    public static final RegistryObject<Item> BURNT_CANNABIS_LEAF = ITEMS.register("burnt_cannabis_leaf", () ->
            new Item(new Item.Properties().group(WeedMod.GROUP)));
    public static final RegistryObject<Item> CANNABIS_BUD = ITEMS.register("cannabis_bud", () ->
            new Item(new Item.Properties().group(WeedMod.GROUP)));
    public static final RegistryObject<Item> DRIED_CANNABIS_BUD = ITEMS.register("dried_cannabis_bud", () ->
            new Item(new Item.Properties().group(WeedMod.GROUP)));
    public static final RegistryObject<Item> BURNT_CANNABIS_BUD = ITEMS.register("burnt_cannabis_bud", () ->
            new Item(new Item.Properties().group(WeedMod.GROUP)));
    public static final RegistryObject<BlockNamedItem> CANNABIS_SEEDS = ITEMS.register("cannabis_seeds", () ->
            new BlockNamedItem(BlockInit.CANNABIS_PLANT.get(), new Item.Properties().group(WeedMod.GROUP)));

    public static final RegistryObject<Item> ROLLING_PAPER = ITEMS.register("rolling_paper", () ->
            new Item(new Item.Properties().group(WeedMod.GROUP)));
    public static final RegistryObject<JointItem> JOINT = ITEMS.register("joint", () ->
            new JointItem(false, new Item.Properties().group(WeedMod.GROUP)));
    public static final RegistryObject<JointItem> FRANK_JOINT = ITEMS.register("frank_joint", () ->
            new JointItem(true, new Item.Properties().group(WeedMod.GROUP)));

    public static final RegistryObject<BlockItem> HEATED_TABLE = ITEMS.register("heated_table", () ->
            new BlockItem(BlockInit.HEATED_TABLE.get(), new Item.Properties().group(WeedMod.GROUP)));
}
