package com.pinzen.sheltercraft.util;

import com.pinzen.sheltercraft.Sheltercraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class Helpers {

    public static ResourceKey<Item> resKeyItem(String id) {
        return ResourceKey.create(
                Registries.ITEM,
                ResourceLocation.parse(String.format("%s:%s", Sheltercraft.MOD_ID, id))
        );
    }
}
