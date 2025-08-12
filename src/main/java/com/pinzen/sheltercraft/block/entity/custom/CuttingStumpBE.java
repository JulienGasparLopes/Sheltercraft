package com.pinzen.sheltercraft.block.entity.custom;

import com.pinzen.sheltercraft.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

public class CuttingStumpBE extends BlockEntity {

    private ItemStack heldItem = ItemStack.EMPTY;
    private int axeBlowCount = 0;

    public CuttingStumpBE(BlockPos pos, BlockState state) {
        super(ModBlocks.CUTTING_STUMP_BE.get(), pos, state);
    }

    public ItemStack getHeldItem() {
        return this.heldItem.copy();
    }

    public boolean addItem(ItemStack item) {
        if (this.heldItem.isEmpty()) {
            if(item.is(ItemTags.LOGS) || item.is(ItemTags.PLANKS)) {
                this.heldItem = item.copy();
                this.heldItem.setCount(1);
                setChanged();
                return true;
            }
        }
        return false;
    }

    public ItemStack processCutting(Player player) {
        ItemStack returnedItems = ItemStack.EMPTY;

        ItemStack removedItem = this.heldItem.copy();
        if (removedItem.isEmpty()) return ItemStack.EMPTY;

        if (player.getCooldowns().isOnCooldown(player.getMainHandItem())) {
            return ItemStack.EMPTY;
        }

        axeBlowCount++;
        player.getCooldowns().addCooldown(player.getMainHandItem(), 10);

        if (axeBlowCount < 3) {
            return ItemStack.EMPTY;
        }

        if (removedItem.is(ItemTags.PLANKS)) {
            returnedItems = new ItemStack(Items.STICK);
            returnedItems.setCount(2);
        }
        else {
            ResourceLocation resLoc = BuiltInRegistries.ITEM.getKey(removedItem.getItem());
            ResourceLocation resLocPlanks = ResourceLocation.fromNamespaceAndPath(
                    resLoc.getNamespace(),
                    resLoc.getPath().replace("log", "planks").replace("stripped_", "")
            );

            Optional<Holder.Reference<Item>> item = BuiltInRegistries.ITEM.get(resLocPlanks);
            if(item.isPresent()) {
                returnedItems = new ItemStack(item.get(), 3);
            }
        }

        if (!returnedItems.isEmpty()) {
            this.heldItem = ItemStack.EMPTY;
            this.axeBlowCount = 0;
            setChanged();
        }
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
}
