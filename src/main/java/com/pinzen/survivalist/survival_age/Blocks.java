package com.pinzen.survivalist.survival_age;

import com.pinzen.survivalist.Survivalist;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class Blocks {

    public static final RegistryObject<Block> CUTTING_STUMP = Survivalist.REGISTER_BLOCKS.register("cutting_stump", CuttingStump::new);
    public static final RegistryObject<Item> CUTTING_STUMP_ITEM = Survivalist.REGISTER_ITEMS.register("cutting_stump",
            () -> new BlockItem(Blocks.CUTTING_STUMP.get(), new Item.Properties().setId(Survivalist.REGISTER_ITEMS.key("cutting_stump"))));

    public static final RegistryObject<BlockEntityType<CuttingStumpBE>> CUTTING_STUMP_BE = Survivalist.REGISTER_BLOCK_ENTITIES.register(
            "cutting_stump_be",
            () -> new BlockEntityType<>(
                    CuttingStumpBE::new,
                    Set.of(CUTTING_STUMP.get())
            ));

}
