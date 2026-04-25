package com.pinzen.sheltercraft.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pinzen.sheltercraft.block.entity.custom.DryingRackBE;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

import java.util.List;

public class DryingRackRenderer implements BlockEntityRenderer<DryingRackBE> {

    private final ItemRenderer itemRenderer;

    public DryingRackRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(DryingRackBE dryingRackBE, float pPartialTick, PoseStack pPoseStack,
            MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay, Vec3 pCameraPos) {
        BlockState state = dryingRackBE.getBlockState();

        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        float yRot = switch (facing) {
            case NORTH -> 180f;
            case SOUTH -> 0f;
            case WEST -> 90f;
            case EAST -> -90f;
            default -> 0f;
        };

        List<ItemStack> dryingItems = dryingRackBE.getDryingItems();

        int i = 0;
        for (ItemStack itemStack : dryingItems) {
            if (!itemStack.isEmpty()) {
                pPoseStack.pushPose();

                pPoseStack.translate(0.5, 0, 0.5);
                Quaternionfc rotationY = new Quaternionf().rotateY((float) Math.toRadians(yRot));
                pPoseStack.rotateAround(rotationY, 0f, 0f, 0f);
                pPoseStack.translate(-0.5, 0, -0.5);

                pPoseStack.translate(0.5, 0.25, 0.20 + 0.6 * i);
                Quaternionfc rotationX = new Quaternionf().rotateX((float) Math.toRadians(27.5f - 55f * i));
                pPoseStack.rotateAround(rotationX, 0f, 0f, 0f);
                pPoseStack.scale(1.8f, 1.8f, 1.8f);

                itemRenderer.renderStatic(itemStack, ItemDisplayContext.GROUND, pPackedLight, pPackedOverlay,
                        pPoseStack, pBufferSource, dryingRackBE.getLevel(), 0);
                pPoseStack.popPose();
            }
            i++;
        }
    }
}
