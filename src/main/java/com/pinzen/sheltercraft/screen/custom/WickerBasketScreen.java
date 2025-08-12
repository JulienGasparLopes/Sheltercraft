package com.pinzen.sheltercraft.screen.custom;

import com.pinzen.sheltercraft.Sheltercraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WickerBasketScreen extends AbstractContainerScreen<WickerBasketMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Sheltercraft.MOD_ID, "textures/gui/wicker_basket.png");

    public WickerBasketScreen(WickerBasketMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {

        graphics.blit(
                TEXTURE,
                this.leftPos,
                this.topPos,
                this.leftPos + imageWidth,
                this.topPos + imageHeight,
                0,
                imageWidth / 256f,
                0,
                imageHeight / 256f
        );
    }
}