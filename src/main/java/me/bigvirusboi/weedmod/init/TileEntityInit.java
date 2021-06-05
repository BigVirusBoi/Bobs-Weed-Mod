package me.bigvirusboi.weedmod.init;

import me.bigvirusboi.weedmod.WeedMod;
import me.bigvirusboi.weedmod.tileentity.HeatedTableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, WeedMod.MOD_ID);

    public static final RegistryObject<TileEntityType<HeatedTableTileEntity>> TABLE =
            TILE_ENTITIES.register("table", () -> TileEntityType.Builder.create(
                    HeatedTableTileEntity::new, BlockInit.HEATED_TABLE.get()).build(null));
}
