package com.pinzen.sheltercraft.block.entity.custom;

import com.pinzen.sheltercraft.block.ModBlocks;
import com.pinzen.sheltercraft.block.entity.BlockEntityHelpers;
import com.pinzen.sheltercraft.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraftforge.fluids.FluidStack;

public class ClayTubBE extends BlockEntity {

    private static final int PROCESSING_TICKS = 20 * 3; // 20 * 60 * 6;

    private FluidStack storedFluid = FluidStack.EMPTY;
    private boolean isTanned = false;
    private ItemStack processingItem = ItemStack.EMPTY;
    private int processedTicks = 0;

    public ClayTubBE(BlockPos pPos, BlockState pBlockState) {
        super(ModBlocks.CLAY_TUB_BE.get(), pPos, pBlockState);
    }

    public boolean addItem(ItemStack item) {
        if (this.isItemEmpty() && (item.is(ModItems.CURED_HIDE.get()) || item.is(ModItems.SOAKED_HIDE.get()))) {
            this.processingItem = item.copy();
            this.processingItem.setCount(1);
            this.processedTicks = 0;
            setChanged();
            if (level instanceof ServerLevel) {
                level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 1 | 2 | 8);
            }
            return true;
        }
        return false;
    }

    public ItemStack retrieveItem() {
        if (!this.processingItem.isEmpty()) {
            ItemStack item = this.processingItem.copy();
            this.processingItem = ItemStack.EMPTY;
            this.processedTicks = 0;
            setChanged();
            if (level instanceof ServerLevel) {
                level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 1 | 2 | 8);
            }
            return item;
        }
        return ItemStack.EMPTY;
    }

    public boolean addFluid(FluidStack fluid) {
        if (this.isFluidEmpty() && fluid.isFluidEqual(new FluidStack(Fluids.WATER, 1000))) {
            this.storedFluid = fluid.copy();
            this.processedTicks = 0;
            setChanged();
            if (level instanceof ServerLevel) {
                level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 1 | 2 | 8);
            }
            return true;
        }
        return false;
    }

    public boolean tanFluid() {
        if (this.isFluidEmpty() || this.isTanned) {
            return false;
        }

        this.isTanned = true;
        this.processedTicks = 0;
        setChanged();
        return true;
    }

    public FluidStack getStoredFluid() {
        return storedFluid.copy();
    }

    public boolean isFluidTanned () {
        return isTanned;
    }

    public ItemStack getProcessingItem() {
        return processingItem.copy();
    }

    public boolean isItemEmpty() {
        return processingItem.isEmpty();
    }

    public boolean isFluidEmpty() {
        return storedFluid.isEmpty();
    }

    public boolean isEmpty() {
        return isItemEmpty() && isFluidEmpty();
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (this.processingItem.is(ModItems.CURED_HIDE.get()) && this.storedFluid.getFluid().isSame(Fluids.WATER) && !this.isTanned) {
            if (processedTicks < PROCESSING_TICKS) {
                processedTicks++;
            } else {
                this.processingItem = new ItemStack(ModItems.SOAKED_HIDE.get());
                this.storedFluid = FluidStack.EMPTY;
                level.sendBlockUpdated(this.worldPosition, blockState, blockState, 1 | 2 | 8);
                setChanged();
            }
        }
        else if (this.processingItem.is(ModItems.SOAKED_HIDE.get()) && this.storedFluid.getFluid().isSame(Fluids.WATER) && this.isTanned) {
            if (processedTicks < PROCESSING_TICKS) {
                processedTicks++;
            } else {
                this.processingItem = new ItemStack(ModItems.TANNED_HIDE.get());
                this.storedFluid = FluidStack.EMPTY;
                this.isTanned = false;
                level.sendBlockUpdated(this.worldPosition, blockState, blockState, 1 | 2 | 8);
                setChanged();
            }
        }
        else if (this.processingItem.isEmpty() && this.storedFluid.getFluid().isSame(Fluids.WATER) && !this.isTanned) {
            if (processedTicks < PROCESSING_TICKS) {
                if (level.getBlockState(blockPos.atY(blockPos.getY() - 1)).is(Blocks.CAMPFIRE)) {
                    processedTicks += 4;
                }
                else {
                    processedTicks++;
                }
            }
            else {
                this.processingItem = new ItemStack(ModItems.SALT.get());
                this.storedFluid = FluidStack.EMPTY;
                this.isTanned = false;
                level.sendBlockUpdated(this.worldPosition, blockState, blockState, 1 | 2 | 8);
                setChanged();
            }
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(ValueOutput pOutput) {
        BlockEntityHelpers.saveFluidStack(pOutput::putString, "storedFluid", storedFluid);
        BlockEntityHelpers.saveItemStack(pOutput::putString, "processingItem", processingItem);
        pOutput.putInt("processedTicks", processedTicks);
        pOutput.putBoolean("isTanned", isTanned);
        super.saveAdditional(pOutput);
    }

    @Override
    protected void loadAdditional(ValueInput pInput) {
        this.storedFluid = BlockEntityHelpers.loadFluidStack(pInput::getString, "storedFluid");
        this.processingItem = BlockEntityHelpers.loadItemStack(pInput::getString, "processingItem");
        this.processedTicks = pInput.getInt("processedTicks").orElse(0);
        this.isTanned = pInput.getBooleanOr("isTanned", false);
        super.loadAdditional(pInput);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        CompoundTag tag = super.getUpdateTag(pRegistries);

        BlockEntityHelpers.saveFluidStack(tag::putString, "storedFluid", storedFluid);
        BlockEntityHelpers.saveItemStack(tag::putString, "processingItem", processingItem);
        tag.putInt("processedTicks", processedTicks);
        tag.putBoolean("isTanned", isTanned);
        return tag;
    }

    @Override
    public void handleUpdateTag(ValueInput valueInput, HolderLookup.Provider holders) {
        this.storedFluid = BlockEntityHelpers.loadFluidStack(valueInput::getString, "storedFluid");
        this.processingItem = BlockEntityHelpers.loadItemStack(valueInput::getString, "processingItem");
        this.processedTicks = valueInput.getInt("processedTicks").orElse(0);
        this.isTanned = valueInput.getBooleanOr("isTanned", false);
    }
}
