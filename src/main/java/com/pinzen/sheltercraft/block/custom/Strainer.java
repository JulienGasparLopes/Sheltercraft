package com.pinzen.sheltercraft.block.custom;

import com.pinzen.sheltercraft.Sheltercraft;
import net.minecraft.world.level.block.Block;

public class Strainer extends Block {
    public Strainer() {
        super(Block.Properties.of()
                .setId(Sheltercraft.REGISTER_BLOCKS.key("strainer"))
                .strength(1.0f)
                .noOcclusion());
    }
}
