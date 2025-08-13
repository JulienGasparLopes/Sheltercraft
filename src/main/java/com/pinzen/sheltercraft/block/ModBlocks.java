package com.pinzen.sheltercraft.block;

import com.pinzen.sheltercraft.Sheltercraft;
import com.pinzen.sheltercraft.block.custom.CuttingStump;
import com.pinzen.sheltercraft.block.custom.Strainer;
import com.pinzen.sheltercraft.block.entity.custom.CuttingStumpBE;
import com.pinzen.sheltercraft.block.custom.WickerBasket;
import com.pinzen.sheltercraft.block.entity.custom.StrainerBE;
import com.pinzen.sheltercraft.block.entity.custom.WickerBasketBE;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlocks {

    public static final RegistryObject<Block> CUTTING_STUMP = Sheltercraft.REGISTER_BLOCKS.register("cutting_stump", CuttingStump::new);
    public static final RegistryObject<Item> CUTTING_STUMP_ITEM = Sheltercraft.REGISTER_ITEMS.register("cutting_stump",
            () -> new BlockItem(ModBlocks.CUTTING_STUMP.get(), new Item.Properties().setId(Sheltercraft.REGISTER_ITEMS.key("cutting_stump"))));

    public static final RegistryObject<BlockEntityType<CuttingStumpBE>> CUTTING_STUMP_BE = Sheltercraft.REGISTER_BLOCK_ENTITIES.register(
            "cutting_stump_be",
            () -> new BlockEntityType<>(
                    CuttingStumpBE::new,
                    Set.of(CUTTING_STUMP.get())
            ));

    public static final RegistryObject<Block> WICKER_BASKET = Sheltercraft.REGISTER_BLOCKS.register("wicker_basket", WickerBasket::new);
    public static final RegistryObject<Item> WICKER_BASKET_ITEM = Sheltercraft.REGISTER_ITEMS.register("wicker_basket",
            () -> new BlockItem(ModBlocks.WICKER_BASKET.get(), new Item.Properties().setId(Sheltercraft.REGISTER_ITEMS.key("wicker_basket"))));

    public static final RegistryObject<BlockEntityType<WickerBasketBE>> WICKER_BASKET_BE = Sheltercraft.REGISTER_BLOCK_ENTITIES.register(
            "wicket_basket_be",
            () -> new BlockEntityType<>(
                    WickerBasketBE::new,
                    Set.of(WICKER_BASKET.get())
            ));

    public static final RegistryObject<Block> STRAINER = Sheltercraft.REGISTER_BLOCKS.register("strainer", Strainer::new);
    public static final RegistryObject<Item> STRAINER_ITEM = Sheltercraft.REGISTER_ITEMS.register("strainer",
            () -> new BlockItem(ModBlocks.STRAINER.get(), new Item.Properties().setId(Sheltercraft.REGISTER_ITEMS.key("strainer"))));

    public static final RegistryObject<BlockEntityType<StrainerBE>> STRAINER_BE = Sheltercraft.REGISTER_BLOCK_ENTITIES.register(
            "strainer_be",
            () -> new BlockEntityType<>(
                    StrainerBE::new,
                    Set.of(STRAINER.get())
            ));

    public static final RegistryObject<Block> GRANITE_WITH_WHETSTONE = Sheltercraft.REGISTER_BLOCKS.register("granite_with_whetstone", () -> new Block(
            Block.Properties.of()
                    .setId(Sheltercraft.REGISTER_BLOCKS.key("granite_with_whetstone"))
                    .strength(3.0f)
                    .requiresCorrectToolForDrops()
    ));
    public static final RegistryObject<Item> GRANITE_WITH_WHETSTONE_ITEM = Sheltercraft.REGISTER_ITEMS.register("granite_with_whetstone",
            () -> new BlockItem(ModBlocks.GRANITE_WITH_WHETSTONE.get(), new Item.Properties().setId(Sheltercraft.REGISTER_ITEMS.key("granite_with_whetstone"))));

}
