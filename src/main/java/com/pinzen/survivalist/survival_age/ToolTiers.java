package com.pinzen.survivalist.survival_age;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;

import static com.pinzen.survivalist.survival_age.Tags.FLINT_TOOL_MATERIALS;

public class ToolTiers {
    // TODO: create a custom BlockTag INCORRECT_FOR_FLINT_TOOL
    public static final ToolMaterial FLINT_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 20, 1.0f, -1.0f, 1, FLINT_TOOL_MATERIALS);
}