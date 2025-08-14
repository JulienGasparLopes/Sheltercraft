package com.pinzen.sheltercraft.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pinzen.sheltercraft.block.entity.custom.StrainerBE;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class StrainerRenderer implements BlockEntityRenderer<StrainerBE> {
    private final ItemRenderer itemRenderer;

    public StrainerRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(StrainerBE pStrainerBE, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay, Vec3 pCameraPos) {
        ItemStack stack = pStrainerBE.getHeldItem();
        if (stack.isEmpty()) {
            return;
        }

        pPoseStack.pushPose();

        pPoseStack.translate(0.5, 0.70, 0.5);
        pPoseStack.scale(3f, 0.125f + 0.4f * (1f - (pStrainerBE.getShakeCount() / 4f)), 3f);

        itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, pPackedLight, pPackedOverlay, pPoseStack, pBufferSource, pStrainerBE.getLevel(), 0);

        pPoseStack.popPose();
    }
}
