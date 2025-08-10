package com.pinzen.survivalist;

import com.mojang.logging.LogUtils;
import com.pinzen.survivalist.survival_age.Blocks;
import com.pinzen.survivalist.survival_age.CuttingStumpRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

/**
 * TODO
 * - Add internal counter to cutting stump to cut log after X left cliks
 * - Add cooldown to cutting stump
 *
 * - Create Boulder item and make it drop from stone when mined with a flint_pickaxe
 * - (Optional) other stone must NOT drop anything when mined with a flint_pickaxe
 *
 * - Create PlantFiber item and make it drop from grass
 * - Allow to wovening PlantFiber to create Wicker
 * - Add WickerBasket item to allow to store items
 *
 * - Remove SimpleTable
 */

@Mod(Survivalist.MOD_ID)
public final class Survivalist {
    public static final String MOD_ID = "survivalist";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Block> REGISTER_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> REGISTER_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> REGISTER_BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<Block> SIMPLE_TABLE = REGISTER_BLOCKS.register("simple_table",
            () -> new Block(BlockBehaviour.Properties.of()
                    .setId(REGISTER_BLOCKS.key("simple_table"))
                    .noOcclusion()
                    .mapColor(MapColor.WOOD))
            {
                @Override
                protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
                    return Block.box(0, 0, 0, 16, 8, 16);
                }
            }

    );
    public static final RegistryObject<Item> SIMPLE_TABLE_ITEM = REGISTER_ITEMS.register("simple_table",
            () -> new BlockItem(SIMPLE_TABLE.get(), new Item.Properties().setId(REGISTER_ITEMS.key("simple_table"))));


    public Survivalist(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);

        new com.pinzen.survivalist.survival_age.Items(); // Not sure if this is needed
        new com.pinzen.survivalist.survival_age.Blocks(); // Not sure if this is needed
        new com.pinzen.survivalist.survival_age.ModEvents(); // Not sure if this is needed

        REGISTER_BLOCKS.register(modBusGroup);
        REGISTER_ITEMS.register(modBusGroup);
        REGISTER_BLOCK_ENTITIES.register(modBusGroup);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }


    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            BlockEntityRenderers.register(Blocks.CUTTING_STUMP_BE.get(), CuttingStumpRenderer::new);
        }
    }
}
