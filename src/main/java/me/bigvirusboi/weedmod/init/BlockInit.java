package me.bigvirusboi.weedmod.init;

import me.bigvirusboi.weedmod.WeedMod;
import me.bigvirusboi.weedmod.block.CannabisPlantBlock;
import me.bigvirusboi.weedmod.block.HeatedTableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WeedMod.MOD_ID);

    public static final RegistryObject<CannabisPlantBlock> CANNABIS_PLANT = BLOCKS.register("cannabis_plant", () ->
            new CannabisPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks()
                    .instabreak().sound(SoundType.HARD_CROP)));

    public static final RegistryObject<HeatedTableBlock> HEATED_TABLE = BLOCKS.register("heated_table", () ->
            new HeatedTableBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE)
                    .strength(2f, 6f).requiresCorrectToolForDrops().lightLevel((state) -> 3)));
}
