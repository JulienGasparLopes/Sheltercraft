package com.pinzen.sheltercraft.block.entity.custom;

import com.pinzen.sheltercraft.block.ModBlocks;
import com.pinzen.sheltercraft.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StrainerBE extends BlockEntity {

    private ItemStack heldItem = ItemStack.EMPTY;
    private int shakeCount = 0;

    public StrainerBE(BlockPos pos, BlockState state) {
        super(ModBlocks.STRAINER_BE.get(), pos, state);
    }

    public ItemStack getHeldItem() {
        return this.heldItem.copy();
    }

    public int getShakeCount() {
        return this.shakeCount;
    }

    public boolean addItem(ItemStack item) {
        if (this.heldItem.isEmpty()) {
            if(item.is(Items.GRAVEL)) {
                this.heldItem = item.copy();
                this.heldItem.setCount(1);
                setChanged();
                return true;
            }
        }
        return false;
    }

    public List<ItemStack> shake(Player player) {
        List<ItemStack> returnedItems = new ArrayList<>();

        if (this.heldItem.isEmpty()) return returnedItems;

        shakeCount++;

        if (shakeCount >= 4) {
            returnedItems.add(new ItemStack(Items.FLINT, 1));
            if (player.getRandom().nextInt(20) == 0) {
                returnedItems.add(new ItemStack(ModItems.WHETSTONE.get(), 1));
            }
            this.heldItem = ItemStack.EMPTY;
            this.shakeCount = 0;
        }

        setChanged();
        return returnedItems;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(ValueOutput pOutput) {
        ResourceLocation resLoc = BuiltInRegistries.ITEM.getKey(heldItem.getItem());
        String heldItemKey = String.format("%s:%s", resLoc.getNamespace(), resLoc.getPath());
        pOutput.putString("HeldItem", heldItemKey);
        pOutput.putInt("ShakeCount", shakeCount);
        super.saveAdditional(pOutput);
    }

    @Override
    protected void loadAdditional(ValueInput pInput) {
        Optional<String> heldItemKey = pInput.getString("HeldItem");
        this.shakeCount = pInput.getInt("ShakeCount").orElse(0);

        if (heldItemKey.isEmpty() || heldItemKey.get().equals("Air")) {
            this.heldItem = ItemStack.EMPTY;
        }
        else {
            String[] parts = heldItemKey.get().split(":");
            String namespace = parts[0];
            String path = parts[1];
            Optional<Holder.Reference<Item>> item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(namespace, path));
            item.ifPresent(itemReference -> this.heldItem = new ItemStack(itemReference));
        }
        super.loadAdditional(pInput);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        ResourceLocation resLoc = BuiltInRegistries.ITEM.getKey(heldItem.getItem());
        String heldItemKey = String.format("%s:%s", resLoc.getNamespace(), resLoc.getPath());
        CompoundTag tag = super.getUpdateTag(pRegistries);
        tag.putString("HeldItem", heldItemKey);
        tag.putInt("ShakeCount", shakeCount);
        return tag;
    }

    @Override
    public void handleUpdateTag(ValueInput tag, HolderLookup.Provider holders) {
        Optional<String> heldItemKey = tag.getString("HeldItem");
        this.shakeCount = tag.getInt("ShakeCount").orElse(0);
        if (heldItemKey.isEmpty() || heldItemKey.get().equals("Air")) {
            this.heldItem = ItemStack.EMPTY;
        }
        else {
            String[] parts = heldItemKey.get().split(":");
            String namespace = parts[0];
            String path = parts[1];
            Optional<Holder.Reference<Item>> item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(namespace, path));
            item.ifPresent(itemReference -> this.heldItem = new ItemStack(itemReference));
        }
    }
}
