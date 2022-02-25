package me.bigvirusboi.weedmod.client;

import me.bigvirusboi.weedmod.WeedMod;
import me.bigvirusboi.weedmod.client.renderer.HeatedTableBlockEntityRenderer;
import me.bigvirusboi.weedmod.init.BlockEntityInit;
import me.bigvirusboi.weedmod.init.BlockInit;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = WeedMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBus {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent e) {
        ItemBlockRenderTypes.setRenderLayer(BlockInit.CANNABIS_PLANT.get(), RenderType.cutout());

        BlockEntityRenderers.register(BlockEntityInit.TABLE.get(), HeatedTableBlockEntityRenderer::new);
    }
}