package me.bigvirusboi.weedmod;

import me.bigvirusboi.weedmod.client.ClientEventBus;
import me.bigvirusboi.weedmod.init.BlockEntityInit;
import me.bigvirusboi.weedmod.init.BlockInit;
import me.bigvirusboi.weedmod.init.ItemInit;
import me.bigvirusboi.weedmod.init.SoundInit;
import me.bigvirusboi.weedmod.world.gen.FeatureGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ComposterBlock;
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
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(ClientEventBus::clientSetup);
        MinecraftForge.EVENT_BUS.addListener(FeatureGenerator::generateFeatures);
    }

    private void commonSetup(final FMLCommonSetupEvent e) {
        e.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ItemInit.CANNABIS_SEEDS.get(), .3f);
            ComposterBlock.COMPOSTABLES.put(ItemInit.CANNABIS_BUD.get(), .5f);
            ComposterBlock.COMPOSTABLES.put(ItemInit.CANNABIS_LEAF.get(), .5f);
        });
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(WeedMod.MOD_ID, path);
    }



    public static final CreativeModeTab TAB = new CreativeModeTab("bobsWeed") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemInit.CANNABIS_LEAF.get());
        }
    };
}
