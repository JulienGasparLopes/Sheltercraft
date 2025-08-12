package com.pinzen.sheltercraft.util;

import com.pinzen.sheltercraft.Sheltercraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static final TagKey<Item> FLINT_TOOL_MATERIALS =
            ItemTags.create(ResourceLocation.fromNamespaceAndPath(Sheltercraft.MOD_ID, "flint_tool_materials"));

    public static final TagKey<Block> MINEABLE_WITH_SICKLE =
            BlockTags.create(ResourceLocation.fromNamespaceAndPath(Sheltercraft.MOD_ID, "mineable_with_sickle"));
}
