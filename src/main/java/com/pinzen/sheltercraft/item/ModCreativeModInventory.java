package com.pinzen.sheltercraft.item;

import com.pinzen.sheltercraft.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.bus.EventBus;

public class ModCreativeModInventory {

    public static void addToCreativeInv(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.FLINT_HATCHET);
            event.accept(ModItems.FLINT_AXE);
            event.accept(ModItems.FLINT_PICKAXE);
            event.accept(ModItems.FLINT_SHOVEL);
            event.accept(ModItems.FLINT_SICKLE);
            event.accept(ModItems.FLINT_TRAPPER_KNIFE);

            event.accept(ModItems.SHARPENED_FLINT_HATCHET);
            event.accept(ModItems.SHARPENED_FLINT_AXE);
            event.accept(ModItems.SHARPENED_FLINT_PICKAXE);
            event.accept(ModItems.SHARPENED_FLINT_SHOVEL);
            event.accept(ModItems.SHARPENED_FLINT_SICKLE);
            event.accept(ModItems.SHARPENED_FLINT_TRAPPER_KNIFE);
        }
        else if( event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.GRANITE_WITH_WHETSTONE);
        }
        else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.WICKER_BASKET);
            event.accept(ModBlocks.CUTTING_STUMP);
            event.accept(ModBlocks.STRAINER);
            event.accept(ModBlocks.WOODEN_TUB);
            event.accept(ModBlocks.DRYING_RACK);
        }
        else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.FLINT_FLAKE);
            event.accept(ModItems.PLANT_FIBER);
            event.accept(ModItems.WICKER);
            event.accept(ModItems.WHETSTONE);

            event.accept(ModItems.ANIMAL_SKIN);
            event.accept(ModItems.RAWHIDE);
            event.accept(ModItems.CURED_HIDE);
            event.accept(ModItems.SOAKED_HIDE);
            event.accept(ModItems.TANNED_HIDE);

            event.accept(ModItems.SALT);
            event.accept(ModItems.WOOD_BARK);
        }
    }

    public static void addListener(BusGroup busGroup) {
        EventBus<BuildCreativeModeTabContentsEvent> buildEventBus = BuildCreativeModeTabContentsEvent.getBus(busGroup);
        buildEventBus.addListener(ModCreativeModInventory::addToCreativeInv);
    }
}
