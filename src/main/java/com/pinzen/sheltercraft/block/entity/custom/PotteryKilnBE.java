package com.pinzen.sheltercraft.block.entity.custom;

import com.pinzen.sheltercraft.block.ModBlocks;
import com.pinzen.sheltercraft.block.entity.BlockEntityHelpers;
import com.pinzen.sheltercraft.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;


public class PotteryKilnBE extends BlockEntity {

    private static final int PROCESSING_TICKS = 20 * 3; // 20 * 60 * 6;

    private ItemStack storedItem = ItemStack.EMPTY;
    private int processedTicks = 0;

    public PotteryKilnBE(BlockPos pos, BlockState state) {
        super(ModBlocks.POTTERY_KILN_BE.get(), pos, state);
    }

    public boolean isFired () {
        if (this.level == null) {
            return false;
        }
        BlockState blockUnder = level.getBlockState(this.worldPosition.atY(this.worldPosition.getY() - 1));
        return blockUnder.is(Blocks.CAMPFIRE);
    }

    public ItemStack getStoredItem() {
        return this.storedItem.copy();
    }

    public boolean addItem(ItemStack item) {
        if (this.storedItem.isEmpty()) {
            if(item.is(ModItems.UNFIRED_CLAY_TUB.get())) {
                this.storedItem = item.copy();
                this.storedItem.setCount(1);
                this.processedTicks = 0;
                setChanged();
                return true;
            }
        }
        return false;
    }

    public ItemStack retrieveItem() {
        if (!this.storedItem.isEmpty()) {
            ItemStack itemToReturn = this.storedItem.copy();
            this.storedItem = ItemStack.EMPTY;
            this.processedTicks = 0;
            setChanged();
            return itemToReturn;
        }
        return ItemStack.EMPTY;
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (this.isFired() && this.storedItem.is(ModItems.UNFIRED_CLAY_TUB.get())) {
            this.processedTicks++;
            if (this.processedTicks >= PROCESSING_TICKS) {
                this.storedItem = new ItemStack(ModBlocks.CLAY_TUB_ITEM.get());
                this.processedTicks = 0;
                setChanged();
                level.sendBlockUpdated(blockPos, blockState, blockState, 1 | 2 | 8);
            }
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(ValueOutput pOutput) {
        BlockEntityHelpers.saveItemStack(pOutput::putString, "storedItem", storedItem);
        pOutput.putInt("processedTicks", processedTicks);
        super.saveAdditional(pOutput);
    }

    @Override
    protected void loadAdditional(ValueInput pInput) {
        this.storedItem = BlockEntityHelpers.loadItemStack(pInput::getString, "storedItem");
        this.processedTicks = pInput.getInt("processedTicks").orElse(0);
        super.loadAdditional(pInput);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        CompoundTag tag = new CompoundTag();
        BlockEntityHelpers.saveItemStack(tag::putString, "storedItem", storedItem);
        tag.putInt("processedTicks", processedTicks);
        return tag;
    }

    @Override
    public void handleUpdateTag(ValueInput tag, HolderLookup.Provider holders) {
        this.storedItem = BlockEntityHelpers.loadItemStack(tag::getString, "storedItem");
        this.processedTicks = tag.getInt("processedTicks").orElse(0);
        setChanged();
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition).inflate(0.5, 0.5, 0.5);
    }
}
