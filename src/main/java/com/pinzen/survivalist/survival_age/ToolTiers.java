package com.pinzen.survivalist.survival_age;

import com.pinzen.survivalist.Survivalist;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class ToolTiers {
    public static final TagKey<Item> FLINT_TOOL_MATERIALS =
            TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath(Survivalist.MOD_ID, "flint_tool_materials"));

    // TODO: create a custom BlockTag INCORRECT_FOR_FLINT_TOOL
    public static final ToolMaterial FLINT_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 20, 1.0f, -1.0f, 1, FLINT_TOOL_MATERIALS);
}