package me.bigvirusboi.weedmod;

import me.bigvirusboi.weedmod.client.ClientEventBus;
import me.bigvirusboi.weedmod.init.BlockInit;
import me.bigvirusboi.weedmod.init.ItemInit;
import me.bigvirusboi.weedmod.init.SoundInit;
import me.bigvirusboi.weedmod.init.TileEntityInit;
import me.bigvirusboi.weedmod.world.BiomeFeatures;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = WeedMod.MOD_ID)
@Mod.EventBusSubscriber(modid = WeedMod.MOD_ID)
public class WeedMod {
    public static final String MOD_ID = "bobs_weed_mod";
    public static final Logger LOGGER = LogManager.getLogger();

    public WeedMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);

        SoundInit.SOUNDS.register(bus);
        TileEntityInit.TILE_ENTITIES.register(bus);
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(ClientEventBus::clientSetup);
        MinecraftForge.EVENT_BUS.addListener(BiomeFeatures::generateFeatures);
    }

    private void commonSetup(final FMLCommonSetupEvent e) {
        e.enqueueWork(() -> {
            ComposterBlock.CHANCES.put(ItemInit.CANNABIS_SEEDS.get(), 0.3F);
            ComposterBlock.CHANCES.put(ItemInit.CANNABIS_BUD.get(), 0.5F);
            ComposterBlock.CHANCES.put(ItemInit.CANNABIS_LEAF.get(), 0.5F);
        });
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(WeedMod.MOD_ID, path);
    }



    public static final WeedItemGroup GROUP = WeedItemGroup.instance;

    private static class WeedItemGroup extends ItemGroup {
        public static final WeedItemGroup instance = new WeedItemGroup("weed");

        private WeedItemGroup(String label) {
            super(label);
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.CANNABIS_LEAF.get());
        }
    }
}
