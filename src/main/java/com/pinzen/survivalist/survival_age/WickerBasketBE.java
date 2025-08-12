package com.pinzen.survivalist.survival_age;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class WickerBasketBE extends BlockEntity implements MenuProvider, Container {
    public static final int SLOT_COUNT = 6;
    public static final int MAX_STACK = 16;

    private final NonNullList<ItemStack> items;

    public WickerBasketBE(BlockPos pPos, BlockState pBlockState) {
        super(Blocks.WICKER_BASKET_BE.get(), pPos, pBlockState);
        this.items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Fiber Chest");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new WickerBasketMenu(pContainerId, pPlayerInventory, this);
    }

    @Override
    public int getContainerSize() {
        return SLOT_COUNT;
    }

    @Override
    public int getMaxStackSize() {
        return MAX_STACK; // This changes the stack limit for this container
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return items.get(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return ContainerHelper.removeItem(items, pSlot, pAmount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return ContainerHelper.takeItem(items, pSlot);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        if (pStack.getCount() > getMaxStackSize()) {
            pStack.setCount(getMaxStackSize());
        }
        items.set(pSlot, pStack);
        setChanged();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return pPlayer.distanceToSqr(
                worldPosition.getX() + 0.5D,
                worldPosition.getY() + 0.5D,
                worldPosition.getZ() + 0.5D
        ) <= 64.0D;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    // Saving
    @Override
    protected void saveAdditional(ValueOutput pOutput) {
        super.saveAdditional(pOutput);
        ContainerHelper.saveAllItems(pOutput, items);
    }

    @Override
    protected void loadAdditional(ValueInput pInput) {
        super.loadAdditional(pInput);
        ContainerHelper.loadAllItems(pInput, items);
    }
}
