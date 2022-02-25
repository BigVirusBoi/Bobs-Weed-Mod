package me.bigvirusboi.weedmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import me.bigvirusboi.weedmod.block.entity.HeatedTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public class HeatedTableBlockEntityRenderer implements BlockEntityRenderer<HeatedTableBlockEntity> {
    public HeatedTableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(HeatedTableBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLightIn, int combinedOverlayIn) {
        NonNullList<ItemStack> items = blockEntity.getItems();
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5D, 0.6D, 0.5D);
                poseStack.scale(0.5f, 0.5f, 0.5f);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90));
                renderItem(stack, poseStack, bufferSource, combinedLightIn);
                poseStack.popPose();
            }
        }
    }

    private void renderItem(ItemStack stack, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLightIn) {
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, 0);
    }
}
