package me.bigvirusboi.weedmod.tileentity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.bigvirusboi.weedmod.tileentity.HeatedTableTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeatedTableTileEntityRenderer extends TileEntityRenderer<HeatedTableTileEntity> {
    public HeatedTableTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(HeatedTableTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        NonNullList<ItemStack> items = tileEntityIn.getItems();
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                matrixStackIn.push();
                matrixStackIn.translate(0.5D, 0.6D, 0.5D);
                matrixStackIn.scale(0.5f, 0.5f, 0.5f);
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90));
                renderItem(stack, matrixStackIn, bufferIn, combinedLightIn);
                matrixStackIn.pop();
            }
        }
    }

    private void renderItem(ItemStack stack, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn) {
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
    }
}
