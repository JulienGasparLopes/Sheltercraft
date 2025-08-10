package com.pinzen.survivalist.survival_age;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CuttingStumpRenderer implements BlockEntityRenderer<CuttingStumpBE> {

    private final ItemRenderer itemRenderer;

    public CuttingStumpRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(CuttingStumpBE pCuttingStumpBE, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay, Vec3 pCameraPos) {
        ItemStack stack = pCuttingStumpBE.getHeldItem();
        if (stack.isEmpty()) {
            return;
        }

        pPoseStack.pushPose();

        // import net.minecraft.world.item.Items;
        // import org.joml.Quaternionf;
        // import org.joml.Quaternionfc;
        // float time = (System.currentTimeMillis() % 36000) / 10f;
        // Quaternionfc rotation = new Quaternionf().rotateY((float) Math.toRadians(time));
        // pPoseStack.rotateAround(rotation, 0.5f, 0.5f, 0.5f);

        pPoseStack.translate(0.5, 0, 0.5);

        pPoseStack.scale(3f, 3f, 3f);


        itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, pPackedLight, pPackedOverlay, pPoseStack, pBufferSource, pCuttingStumpBE.getLevel(), 0);

        pPoseStack.popPose();
    }
}
