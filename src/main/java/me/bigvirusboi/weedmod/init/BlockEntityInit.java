package me.bigvirusboi.weedmod.init;

import me.bigvirusboi.weedmod.WeedMod;
import me.bigvirusboi.weedmod.block.entity.HeatedTableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, WeedMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<HeatedTableBlockEntity>> TABLE =
            BLOCK_ENTITIES.register("table", () -> BlockEntityType.Builder.of(
                    HeatedTableBlockEntity::new, BlockInit.HEATED_TABLE.get()).build(null));
}
