package com.pinzen.sheltercraft.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pinzen.sheltercraft.block.entity.custom.PotteryKilnBE;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public class PotteryKilnRenderer implements BlockEntityRenderer<PotteryKilnBE> {

    private final ItemRenderer itemRenderer;

    public PotteryKilnRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(PotteryKilnBE potteryKilnBE, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay, Vec3 pCameraPos) {
        ItemStack stack = potteryKilnBE.getStoredItem();
        if (stack.isEmpty()) {
            return;
        }

        pPoseStack.pushPose();
        pPoseStack.translate(0.5, 0.3, 0.5);
        Quaternionfc rotation = new Quaternionf().rotateY((float) Math.toRadians(27f));
        pPoseStack.rotateAround(rotation, 0, 0, 0);
        pPoseStack.scale(0.8f, 0.8f, 0.8f);

        itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, pPackedLight, pPackedOverlay, pPoseStack, pBufferSource, potteryKilnBE.getLevel(), 0);
        pPoseStack.popPose();
    }
}
