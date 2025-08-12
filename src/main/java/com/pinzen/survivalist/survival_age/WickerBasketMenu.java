package com.pinzen.survivalist.survival_age;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class WickerBasketMenu extends AbstractContainerMenu {
    private final WickerBasketBE wickerBasketBE;

    public WickerBasketMenu(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(id, playerInventory, playerInventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }


    public WickerBasketMenu(int id, Inventory playerInventory, BlockEntity be) {
        super(Menus.WICKER_BASKET_MENU.get(), id);
        this.wickerBasketBE = (WickerBasketBE) be;

        // Chest slots (3x2)
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 3; col++) {
                this.addSlot(new Slot(this.wickerBasketBE, col + row * 3, 62 + col * 18, 17 + row * 18));
            }
        }

        // Player inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.wickerBasketBE.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex) {
        // Code from : https://docs.minecraftforge.net/en/latest/gui/menus/#quickmovestack
        // TODO: fix to avoid moving remaining items when only storing part of the stack

        ItemStack quickMovedStack = ItemStack.EMPTY;
        Slot quickMovedSlot = this.slots.get(quickMovedSlotIndex);

        // If the slot is in the valid range and the slot is not empty
        if (quickMovedSlot.hasItem()) {
            ItemStack rawStack = quickMovedSlot.getItem();
            quickMovedStack = rawStack.copy();

            // If the quick move was performed on the data inventory
            if (quickMovedSlotIndex < WickerBasketBE.SLOT_COUNT ) {
                // Try to move the result slot into the player inventory/hotbar
                if (!this.moveItemStackTo(rawStack, 5, 41, true)) {
                    // If it cannot be moved, stop quick move
                    return ItemStack.EMPTY;
                }
                quickMovedSlot.onQuickCraft(rawStack, quickMovedStack);
            }
            // Else if the quick move was performed on the player inventory or hotbar slot
            else if (quickMovedSlotIndex < WickerBasketBE.SLOT_COUNT + 27 + 9) {
                // Try to move the inventory/hotbar slot into the data inventory input slots
                if (!this.moveItemStackTo(rawStack, 1, 5, false)) {
                    // If it cannot be moved in player inventory slot, try to move to hotbar
                    if (quickMovedSlotIndex < WickerBasketBE.SLOT_COUNT + 27) {
                        if (!this.moveItemStackTo(rawStack, 32, 41, false)) {
                            // If it cannot be moved, stop quick move
                            return ItemStack.EMPTY;
                        }
                    }
                    // Else try to move hotbar into player inventory slot
                    else if (!this.moveItemStackTo(rawStack, 5, 32, false)) {
                        // If it cannot be moved, stop quick move
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (rawStack.isEmpty()) {
                // If the raw stack has completely moved out of the slot, set the slot to the empty stack
                quickMovedSlot.set(ItemStack.EMPTY);
            } else {
                // Otherwise, notify the slot that the stack count has changed
                quickMovedSlot.setChanged();
            }

            /*
            The following if statement and Slot#onTake call can be removed if the
            menu does not represent a container that can transform stacks (e.g.
            chests).
            */
            if (rawStack.getCount() == quickMovedStack.getCount()) {
                // If the raw stack was not able to move to another slot, no longer quick move
                return ItemStack.EMPTY;
            }
            // Execute logic on what to do post move with the remaining stack
            quickMovedSlot.onTake(player, rawStack);
        }

        return quickMovedStack;
    }
}