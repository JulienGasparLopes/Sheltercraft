package com.pinzen.survivalist.survival_age;

import com.pinzen.survivalist.Survivalist;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class Sickle extends Item {

    public static final TagKey<Block> MINEABLE_WITH_SICKLE =
            TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(Survivalist.MOD_ID, "mineable/sickle"));


    public Sickle(ToolMaterial pToolMaterial, Properties pProperties) {
        super(pProperties.tool(
                pToolMaterial,
                MINEABLE_WITH_SICKLE,
                -1f,
                -6f,
                0f
        ));
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pMiningEntity) {
        if (!pLevel.isClientSide && pState.is(MINEABLE_WITH_SICKLE)) {
            pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5f, pPos.getY() + 0.5f, pPos.getZ() + 0.5f,
                    new ItemStack(Items.PLANT_FIBER.get())));
        }
        return super.mineBlock(pStack, pLevel, pState, pPos, pMiningEntity);
    }
}
