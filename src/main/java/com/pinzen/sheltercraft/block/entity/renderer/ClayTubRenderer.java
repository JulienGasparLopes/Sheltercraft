package com.pinzen.sheltercraft.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pinzen.sheltercraft.block.entity.custom.ClayTubBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public class ClayTubRenderer implements BlockEntityRenderer<ClayTubBE> {

    private final ItemRenderer itemRenderer;

    public ClayTubRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ClayTubBE clayTubBE, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
        FluidStack fluidStack = clayTubBE.getStoredFluid();

        ItemStack itemStack = clayTubBE.getProcessingItem();

        if (!fluidStack.isEmpty()) {
            poseStack.pushPose();
            Fluid fluid = fluidStack.getFluid();
            IClientFluidTypeExtensions fx = IClientFluidTypeExtensions.of(fluid);

            // texture + color (ARGB)
            ResourceLocation stillTex = fx.getStillTexture(fluidStack);
            int colorARGB = fx.getTintColor(fluidStack);

            float minY = 0.1f, maxY = 0.3f;

            renderCuboid(
                    poseStack,
                    bufferSource.getBuffer(RenderType.translucentMovingBlock()),
                    stillTex,
                    colorARGB,
                    0.05f, minY, 0.05f,
                    0.95f, maxY, 0.95f,
                    packedLight,
                    clayTubBE.isFluidTanned() // isTanned flag to determine if the tub is tanned
            );
            poseStack.popPose();
        }

        if (!itemStack.isEmpty()) {
            poseStack.pushPose();
            Quaternionfc rotation = new Quaternionf().rotateX((float) Math.toRadians(90));
            poseStack.translate(0.5, 0.25, 0.3);
            poseStack.rotateAround(rotation, 0f, 0f, 0f);
            poseStack.scale(1.3f, 1.3f, 1.3f);

            VertexConsumer consumer = bufferSource.getBuffer(RenderType.translucentMovingBlock());
            itemRenderer.renderStatic(itemStack, ItemDisplayContext.GROUND, packedLight, packedOverlay, poseStack, (layer) -> consumer, clayTubBE.getLevel(), 0);
            poseStack.popPose();
        }
    }

    private void renderCuboid(
            PoseStack poseStack,
            VertexConsumer consumer,
            ResourceLocation texture,
            int colorARGB,
            float minX, float minY, float minZ,
            float maxX, float maxY, float maxZ,
            int packedLight,
            boolean isTanned

    ) {
        // Extract RGBA from ARGB int
        float a = (colorARGB >> 24 & 0xFF) / 255f;
        float r = (colorARGB >> 16 & 0xFF) / 255f;
        float g = (colorARGB >> 8 & 0xFF) / 255f;
        float b = (colorARGB & 0xFF) / 255f;

        if (isTanned) {
            r *= 0.8f;
            g *= 0.5f;
            b *= 0.3f;
        }

        int blockLight = packedLight & 0xFFFF;
        int skyLight   = (packedLight >> 16) & 0xFFFF;

        // Get the animated texture sprite
        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                .apply(texture);

        float u0 = sprite.getU0();
        float u1 = sprite.getU1();
        float v0 = sprite.getV0();
        float v1 = sprite.getV1();

        Matrix4f matrix = poseStack.last().pose();

        // Top face (y = maxY)
        consumer.addVertex(matrix, minX, maxY, minZ).setColor(r, g, b, a).setUv(u0, v0).setUv2(blockLight, skyLight).setNormal(poseStack.last(), 0, 1, 0);
        consumer.addVertex(matrix, minX, maxY, maxZ).setColor(r, g, b, a).setUv(u0, v1).setUv2(blockLight, skyLight).setNormal(poseStack.last(), 0, 1, 0);
        consumer.addVertex(matrix, maxX, maxY, maxZ).setColor(r, g, b, a).setUv(u1, v1).setUv2(blockLight, skyLight).setNormal(poseStack.last(), 0, 1, 0);
        consumer.addVertex(matrix, maxX, maxY, minZ).setColor(r, g, b, a).setUv(u1, v0).setUv2(blockLight, skyLight).setNormal(poseStack.last(), 0, 1, 0);

        // Sides can be added here if you want the fluid visible from all angles:
        // - north face (z = minZ)
        // - south face (z = maxZ)
        // - east  face (x = maxX)
        // - west  face (x = minX)
    }
}
