package me.bigvirusboi.weedmod.client;

import me.bigvirusboi.weedmod.WeedMod;
import me.bigvirusboi.weedmod.init.BlockInit;
import me.bigvirusboi.weedmod.init.TileEntityInit;
import me.bigvirusboi.weedmod.tileentity.renderer.HeatedTableTileEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = WeedMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBus {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent e) {
        RenderTypeLookup.setRenderLayer(BlockInit.CANNABIS_PLANT.get(), RenderType.getCutout());

        ClientRegistry.bindTileEntityRenderer(TileEntityInit.TABLE.get(), HeatedTableTileEntityRenderer::new);
    }
}