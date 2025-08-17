package com.pinzen.sheltercraft.block;

import com.pinzen.sheltercraft.Sheltercraft;
import com.pinzen.sheltercraft.block.custom.*;
import com.pinzen.sheltercraft.block.entity.custom.*;
import com.pinzen.sheltercraft.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlocks {
    public static final DeferredRegister<Block> REGISTER_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Sheltercraft.MOD_ID);

    // Simple Blocks
    public static final RegistryObject<Block> GRANITE_WITH_WHETSTONE = REGISTER_BLOCKS.register("granite_with_whetstone", () -> new Block(
            Block.Properties.of()
                    .setId(REGISTER_BLOCKS.key("granite_with_whetstone"))
                    .strength(3.0f)
                    .requiresCorrectToolForDrops()
    ));
    public static final RegistryObject<Item> GRANITE_WITH_WHETSTONE_ITEM = ModItems.REGISTER_ITEMS.register("granite_with_whetstone",
            () -> new BlockItem(ModBlocks.GRANITE_WITH_WHETSTONE.get(), new Item.Properties().setId(ModItems.REGISTER_ITEMS.key("granite_with_whetstone"))));

    // Blocks with Block Entities
    public static final RegistryObject<Block> CUTTING_STUMP = REGISTER_BLOCKS.register("cutting_stump", CuttingStump::new);
    public static final RegistryObject<Item> CUTTING_STUMP_ITEM = ModItems.REGISTER_ITEMS.register("cutting_stump",
            () -> new BlockItem(ModBlocks.CUTTING_STUMP.get(), new Item.Properties().setId(ModItems.REGISTER_ITEMS.key("cutting_stump"))));

    public static final RegistryObject<BlockEntityType<CuttingStumpBE>> CUTTING_STUMP_BE = Sheltercraft.REGISTER_BLOCK_ENTITIES.register(
            "cutting_stump_be",
            () -> new BlockEntityType<>(
                    CuttingStumpBE::new,
                    Set.of(CUTTING_STUMP.get())
            ));

    public static final RegistryObject<Block> WICKER_BASKET = REGISTER_BLOCKS.register("wicker_basket", WickerBasket::new);
    public static final RegistryObject<Item> WICKER_BASKET_ITEM = ModItems.REGISTER_ITEMS.register("wicker_basket",
            () -> new BlockItem(ModBlocks.WICKER_BASKET.get(), new Item.Properties().setId(ModItems.REGISTER_ITEMS.key("wicker_basket"))));

    public static final RegistryObject<BlockEntityType<WickerBasketBE>> WICKER_BASKET_BE = Sheltercraft.REGISTER_BLOCK_ENTITIES.register(
            "wicket_basket_be",
            () -> new BlockEntityType<>(
                    WickerBasketBE::new,
                    Set.of(WICKER_BASKET.get())
            ));

    public static final RegistryObject<Block> STRAINER = REGISTER_BLOCKS.register("strainer", Strainer::new);
    public static final RegistryObject<Item> STRAINER_ITEM = ModItems.REGISTER_ITEMS.register("strainer",
            () -> new BlockItem(ModBlocks.STRAINER.get(), new Item.Properties().setId(ModItems.REGISTER_ITEMS.key("strainer"))));

    public static final RegistryObject<BlockEntityType<StrainerBE>> STRAINER_BE = Sheltercraft.REGISTER_BLOCK_ENTITIES.register(
            "strainer_be",
            () -> new BlockEntityType<>(
                    StrainerBE::new,
                    Set.of(STRAINER.get())
            ));

    public static final RegistryObject<Block> WOODEN_TUB = REGISTER_BLOCKS.register("wooden_tub", WoodenTub::new);
    public static final RegistryObject<Item> WOODEN_TUB_ITEM = ModItems.REGISTER_ITEMS.register("wooden_tub",
            () -> new BlockItem(ModBlocks.WOODEN_TUB.get(), new Item.Properties().setId(ModItems.REGISTER_ITEMS.key("wooden_tub"))));

    public static final RegistryObject<BlockEntityType<WoodenTubBE>> WOODEN_TUB_BE = Sheltercraft.REGISTER_BLOCK_ENTITIES.register(
            "wooden_tub_be",
            () -> new BlockEntityType<>(
                    WoodenTubBE::new,
                    Set.of(WOODEN_TUB.get())
            ));

    public static final RegistryObject<Block> DRYING_RACK = REGISTER_BLOCKS.register("drying_rack", DryingRack::new);
    public static final RegistryObject<Item> DRYING_RACK_ITEM = ModItems.REGISTER_ITEMS.register("drying_rack",
            () -> new BlockItem(ModBlocks.DRYING_RACK.get(), new Item.Properties().setId(ModItems.REGISTER_ITEMS.key("drying_rack"))));

    public static final RegistryObject<BlockEntityType<DryingRackBE>> DRYING_RACK_BE = Sheltercraft.REGISTER_BLOCK_ENTITIES.register(
            "drying_rack_be",
            () -> new BlockEntityType<>(
                    DryingRackBE::new,
                    Set.of(DRYING_RACK.get())
            ));

    public static void register(BusGroup busGroup) {
        REGISTER_BLOCKS.register(busGroup);
    }
}
