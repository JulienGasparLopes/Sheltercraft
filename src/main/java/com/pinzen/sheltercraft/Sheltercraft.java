package com.pinzen.sheltercraft;

import com.mojang.logging.LogUtils;
import com.pinzen.sheltercraft.block.ModBlocks;
import com.pinzen.sheltercraft.block.entity.renderer.CuttingStumpRenderer;
import com.pinzen.sheltercraft.event.ModEvents;
import com.pinzen.sheltercraft.screen.ModMenuTypes;
import com.pinzen.sheltercraft.screen.custom.WickerBasketScreen;
import com.pinzen.sheltercraft.item.ModItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(Sheltercraft.MOD_ID)
public final class Sheltercraft {
    public static final String MOD_ID = "sheltercraft";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Block> REGISTER_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> REGISTER_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> REGISTER_BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);
    public static final DeferredRegister<MenuType<?>> REGISTER_MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);

    public Sheltercraft(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);

        new ModItems();
        new ModBlocks();
        new ModEvents();
        new ModMenuTypes();

        REGISTER_BLOCKS.register(modBusGroup);
        REGISTER_ITEMS.register(modBusGroup);
        REGISTER_BLOCK_ENTITIES.register(modBusGroup);
        REGISTER_MENUS.register(modBusGroup);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }


    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            BlockEntityRenderers.register(ModBlocks.CUTTING_STUMP_BE.get(), CuttingStumpRenderer::new);
            MenuScreens.register(ModMenuTypes.WICKER_BASKET_MENU.get(), WickerBasketScreen::new);
        }
    }
}
