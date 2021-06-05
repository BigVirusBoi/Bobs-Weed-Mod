package me.bigvirusboi.weedmod.init;

import me.bigvirusboi.weedmod.WeedMod;
import me.bigvirusboi.weedmod.block.CannabisPlantBlock;
import me.bigvirusboi.weedmod.block.HeatedTableBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WeedMod.MOD_ID);

    public static final RegistryObject<CannabisPlantBlock> CANNABIS_PLANT = BLOCKS.register("cannabis_plant", () ->
            new CannabisPlantBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.GREEN).doesNotBlockMovement()
                    .notSolid().sound(SoundType.PLANT).tickRandomly().zeroHardnessAndResistance()));

    public static final RegistryObject<HeatedTableBlock> HEATED_TABLE = BLOCKS.register("heated_table", () ->
            new HeatedTableBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).harvestLevel(2)
                    .hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE).setLightLevel((state) -> 3)));
}
