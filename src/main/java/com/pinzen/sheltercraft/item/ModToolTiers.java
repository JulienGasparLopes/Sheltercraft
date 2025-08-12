package com.pinzen.sheltercraft.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;

import static com.pinzen.sheltercraft.util.ModTags.FLINT_TOOL_MATERIALS;

public class ModToolTiers {
    // TODO: create a custom BlockTag INCORRECT_FOR_FLINT_TOOL
    public static final ToolMaterial FLINT_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 20, 1.0f, -1.0f, 1, FLINT_TOOL_MATERIALS);
}