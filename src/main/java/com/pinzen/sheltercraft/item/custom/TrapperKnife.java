package com.pinzen.sheltercraft.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

import static com.pinzen.sheltercraft.util.ModTags.MINEABLE_WITH_TRAPPER_KNIFE;

public class TrapperKnife extends Item {
    public TrapperKnife(ToolMaterial pToolMaterial, Properties pProperties) {
        super(pProperties.tool(
                pToolMaterial,
                MINEABLE_WITH_TRAPPER_KNIFE,
                1.5f,
                -1f,
                0f
        ));
    }
}
