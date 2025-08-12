package com.pinzen.survivalist.item.custom;

import com.pinzen.survivalist.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static com.pinzen.survivalist.util.ModTags.MINEABLE_WITH_SICKLE;

public class Sickle extends Item {
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
            pLevel.addFreshEntity(
                    new ItemEntity(
                            pLevel,
                            pPos.getX() + 0.5f,
                            pPos.getY() + 0.5f,
                            pPos.getZ() + 0.5f,
                            new ItemStack(ModItems.PLANT_FIBER.get())
                    )
            );
        }
        return super.mineBlock(pStack, pLevel, pState, pPos, pMiningEntity);
    }
}
