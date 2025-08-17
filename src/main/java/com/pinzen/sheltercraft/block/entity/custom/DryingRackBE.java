package com.pinzen.sheltercraft.block.entity.custom;

import com.pinzen.sheltercraft.block.ModBlocks;
import com.pinzen.sheltercraft.block.entity.BlockEntityHelpers;
import com.pinzen.sheltercraft.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import java.util.List;

public class DryingRackBE extends BlockEntity {

    private ItemStack dryingItem1 = ItemStack.EMPTY;
    private ItemStack dryingItem2 = ItemStack.EMPTY;
    private int dryingTickCount1 = 0;
    private int dryingTickCount2 = 0;

    private static final int DRYING_TICKS = 20 * 60 * 3;
    private static final List<Item> DRYABLE_ITEMS = List.of(
            ModItems.TANNED_HIDE.get()
    );

    public DryingRackBE(BlockPos pos, BlockState state) {
        super(ModBlocks.DRYING_RACK_BE.get(), pos, state);
    }

    public List<ItemStack> getDryingItems() {
        return List.of(dryingItem1, dryingItem2);
    }

    public boolean hasPlace() {
        return this.dryingItem1.isEmpty() || this.dryingItem2.isEmpty();
    }

    public boolean addItem(ItemStack item) {
        if (DRYABLE_ITEMS.stream().noneMatch(item::is) || !this.hasPlace()) {
            return false;
        }
        ItemStack toAdd = item.copy();
        toAdd.setCount(1);

        if (this.dryingItem1.isEmpty()) {
                this.dryingItem1 = toAdd;
                this.dryingTickCount1 = 0;
        }
        else {
            this.dryingItem2 = toAdd;
            this.dryingTickCount2 = 0;

        }
        setChanged();
        return true;
    }

    public ItemStack retrieveItem() {
        ItemStack toReturn = ItemStack.EMPTY;

        if (this.dryingTickCount1 > DRYING_TICKS) {
            toReturn = this.dryingItem1.copy();
            this.dryingItem1 = ItemStack.EMPTY;
            this.dryingTickCount1 = 0;
        }
        else if (this.dryingTickCount2 > DRYING_TICKS) {
            toReturn = this.dryingItem2.copy();
            this.dryingItem2 = ItemStack.EMPTY;
            this.dryingTickCount2 = 0;
        }
        else if (!this.dryingItem1.isEmpty()) {
            toReturn = this.dryingItem1.copy();
            this.dryingItem1 = ItemStack.EMPTY;
            this.dryingTickCount1 = 0;
        }
        else if (!this.dryingItem2.isEmpty()) {
            toReturn = this.dryingItem2.copy();
            this.dryingItem2 = ItemStack.EMPTY;
            this.dryingTickCount2 = 0;
        }
        setChanged();
        return toReturn;
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        boolean shouldUpdate = false;
        if (!this.dryingItem1.isEmpty() && this.dryingTickCount1 < DRYING_TICKS) {
            this.dryingTickCount1++;
            if (this.dryingTickCount1 >= DRYING_TICKS) {
                this.dryingItem1 = new ItemStack(Items.LEATHER);
                shouldUpdate = true;
            }
        }
        if (!this.dryingItem2.isEmpty() && this.dryingTickCount2 < DRYING_TICKS) {
            this.dryingTickCount2++;
            if (this.dryingTickCount2 >= DRYING_TICKS) {
                this.dryingItem2 = new ItemStack(Items.LEATHER);
                shouldUpdate = true;
            }
        }

        if (shouldUpdate) {
            level.sendBlockUpdated(this.worldPosition, blockState, blockState, 1 | 2 | 8);
            setChanged();
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(ValueOutput pOutput) {
        BlockEntityHelpers.saveItemStack(pOutput::putString, "dryingItem1", dryingItem1);
        BlockEntityHelpers.saveItemStack(pOutput::putString, "dryingItem2", dryingItem2);
        pOutput.putInt("dryingTickCount1", dryingTickCount1);
        pOutput.putInt("dryingTickCount2", dryingTickCount2);
        super.saveAdditional(pOutput);
    }

    @Override
    protected void loadAdditional(ValueInput pInput) {
        this.dryingItem1 = BlockEntityHelpers.loadItemStack(pInput::getString, "dryingItem1");
        this.dryingItem2 = BlockEntityHelpers.loadItemStack(pInput::getString, "dryingItem2");
        this.dryingTickCount1 = pInput.getInt("dryingTickCount1").orElse(0);
        this.dryingTickCount2 = pInput.getInt("dryingTickCount2").orElse(0);
        super.loadAdditional(pInput);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        CompoundTag tag = super.getUpdateTag(pRegistries);
        BlockEntityHelpers.saveItemStack(tag::putString, "dryingItem1", dryingItem1);
        BlockEntityHelpers.saveItemStack(tag::putString, "dryingItem2", dryingItem2);
        tag.putInt("dryingTickCount1", dryingTickCount1);
        tag.putInt("dryingTickCount2", dryingTickCount2);
        return tag;
    }

    @Override
    public void handleUpdateTag(ValueInput tag, HolderLookup.Provider holders) {
        this.dryingItem1 = BlockEntityHelpers.loadItemStack(tag::getString, "dryingItem1");
        this.dryingItem2 = BlockEntityHelpers.loadItemStack(tag::getString, "dryingItem2");
        this.dryingTickCount1 = tag.getInt("dryingTickCount1").orElse(0);
        this.dryingTickCount2 = tag.getInt("dryingTickCount2").orElse(0);
    }
}
