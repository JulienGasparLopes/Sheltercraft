package com.pinzen.sheltercraft.block.entity;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class BlockEntityHelpers {
    public static void saveItemStack(BiConsumer<String, String> consumer, String key, ItemStack itemStack) {
        ResourceLocation resLoc = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        String itemString = String.format("%s:%s", resLoc.getNamespace(), resLoc.getPath());
        consumer.accept(key, itemString);
    }

    public static void saveFluidStack(BiConsumer<String, String> consumer, String key, FluidStack fluidStack) {
        ResourceLocation resLoc = BuiltInRegistries.FLUID.getKey(fluidStack.getFluid());
        String fluidString = String.format("%s:%s", resLoc.getNamespace(), resLoc.getPath());
        consumer.accept(key, fluidString);
    }

    public static ItemStack loadItemStack(Function<String, Optional<String>> func, String key) {
        ItemStack loadedItemStack = ItemStack.EMPTY;

        Optional<String> itemString = func.apply(key);
        if (itemString.isPresent()) {
            String[] parts = itemString.get().split(":");
            String namespace = parts[0];
            String path = parts[1];
            Optional<Holder.Reference<Item>> item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(namespace, path));
            if (item.isPresent()) {
                loadedItemStack = new ItemStack(item.get());
            }
        }
        return loadedItemStack;
    }

    public static FluidStack loadFluidStack(Function<String, Optional<String>> func, String key) {
        FluidStack loadedFluidStack = FluidStack.EMPTY;

        Optional<String> fluidString = func.apply(key);
        if (fluidString.isPresent()) {
            String[] parts = fluidString.get().split(":");
            String namespace = parts[0];
            String path = parts[1];
            Optional<Holder.Reference<Fluid>> fluid = BuiltInRegistries.FLUID.get(ResourceLocation.fromNamespaceAndPath(namespace, path));
            if (fluid.isPresent()) {
                loadedFluidStack = new FluidStack(fluid.get().get(), 1000);
            }
        }
        return loadedFluidStack;
    }
}
