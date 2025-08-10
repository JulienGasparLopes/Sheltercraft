package com.pinzen.survivalist.survival_age;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

public class CuttingStumpBE extends BlockEntity {

    private ItemStack heldItem;

    public CuttingStumpBE(BlockPos pos, BlockState state) {
        super(Blocks.CUTTING_STUMP_BE.get(), pos, state);
        this.heldItem = ItemStack.EMPTY;
    }

    public boolean addLog(ItemStack log) {
        if (this.heldItem.isEmpty()) {
            this.heldItem = log.copy();
            this.heldItem.setCount(1);
            setChanged();
            return true;
        }
        return false;
    }

    public ItemStack cutLog() {
        ItemStack returnedPlanks = ItemStack.EMPTY;

        ItemStack removedLog = this.heldItem.copy();
        ResourceLocation resLoc = BuiltInRegistries.ITEM.getKey(removedLog.getItem());
        ResourceLocation resLocPlanks = ResourceLocation.fromNamespaceAndPath(
                resLoc.getNamespace(),
                resLoc.getPath().replace("log", "planks")
        );

        Optional<Holder.Reference<Item>> item = BuiltInRegistries.ITEM.get(resLocPlanks);
        if(item.isPresent()) {
            returnedPlanks = new ItemStack(item.get(), 3);
        }

        this.heldItem = ItemStack.EMPTY;
        setChanged();
        return returnedPlanks;
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
        super.saveAdditional(pOutput);
    }

    @Override
    protected void loadAdditional(ValueInput pInput) {
        Optional<String> heldItemKey = pInput.getString("HeldItem");
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
        return tag;
    }

    @Override
    public void handleUpdateTag(ValueInput tag, HolderLookup.Provider holders) {
        Optional<String> heldItemKey = tag.getString("HeldItem");
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

    @Override
    public AABB getRenderBoundingBox() {
        // Expand the bounding box to include your rendered item
        // Example: 2 blocks tall, centered on the block
        return new AABB(worldPosition).inflate(0.5, 0.5, 0.5);
    }

    public ItemStack getHeldItem() {
        return this.heldItem.copy();
    }
}
