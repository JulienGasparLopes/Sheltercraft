package com.pinzen.sheltercraft.util;

import com.pinzen.sheltercraft.Sheltercraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    // Items
    public static final TagKey<Item> FLINT_TOOL_MATERIALS =
            ItemTags.create(ResourceLocation.fromNamespaceAndPath(Sheltercraft.MOD_ID, "flint_tool_materials"));
    public static final TagKey<Item> SHARPENED_FLINT_TOOL_MATERIALS =
            ItemTags.create(ResourceLocation.fromNamespaceAndPath(Sheltercraft.MOD_ID, "sharpened_flint_tool_materials"));
    public static final TagKey<Item> TRAPPER_KNIFES =
            ItemTags.create(ResourceLocation.fromNamespaceAndPath(Sheltercraft.MOD_ID, "trapper_knifes"));

    // Blocks
    public static final TagKey<Block> MINEABLE_WITH_SICKLE =
            BlockTags.create(ResourceLocation.fromNamespaceAndPath(Sheltercraft.MOD_ID, "mineable_with_sickle"));
    public static final TagKey<Block> MINEABLE_WITH_TRAPPER_KNIFE =
            BlockTags.create(ResourceLocation.fromNamespaceAndPath(Sheltercraft.MOD_ID, "mineable_with_trapper_knife"));
    public static final TagKey<Block> NEED_SHARPENED_FLINT_TOOL =
            BlockTags.create(ResourceLocation.fromNamespaceAndPath(Sheltercraft.MOD_ID, "need_sharpened_flint_tool"));
    public static final TagKey<Block> INCORRECT_FOR_FLINT_TOOL =
            BlockTags.create(ResourceLocation.fromNamespaceAndPath(Sheltercraft.MOD_ID, "incorrect_for_flint_tool"));
    public static final TagKey<Block> INCORRECT_FOR_SHARPENED_FLINT_TOOL =
            BlockTags.create(ResourceLocation.fromNamespaceAndPath(Sheltercraft.MOD_ID, "incorrect_for_sharpened_flint_tool"));
}
