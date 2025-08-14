package com.pinzen.sheltercraft.item.custom;

import com.pinzen.sheltercraft.block.ModBlocks;
import com.pinzen.sheltercraft.item.ModItems;
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

            event.accept(ModItems.SHARPENED_FLINT_HATCHET);
            event.accept(ModItems.SHARPENED_FLINT_AXE);
            event.accept(ModItems.SHARPENED_FLINT_PICKAXE);
            event.accept(ModItems.SHARPENED_FLINT_SHOVEL);
            event.accept(ModItems.SHARPENED_FLINT_SICKLE);
        }
        else if( event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.GRANITE_WITH_WHETSTONE);
        }
        else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.WICKER_BASKET);
            event.accept(ModBlocks.CUTTING_STUMP);
            event.accept(ModBlocks.STRAINER);
        }
        else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.FLINT_FLAKE);
            event.accept(ModItems.PLANT_FIBER);
            event.accept(ModItems.WICKER);
            event.accept(ModItems.WHETSTONE);
        }
    }

    public static void addListener(BusGroup busGroup) {
        EventBus<BuildCreativeModeTabContentsEvent> buildEventBus = BuildCreativeModeTabContentsEvent.getBus(busGroup);
        buildEventBus.addListener(ModCreativeModInventory::addToCreativeInv);
    }
}
