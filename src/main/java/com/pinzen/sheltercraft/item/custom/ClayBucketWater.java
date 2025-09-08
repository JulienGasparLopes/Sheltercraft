package com.pinzen.sheltercraft.item.custom;

import com.pinzen.sheltercraft.block.entity.custom.ClayTubBE;
import com.pinzen.sheltercraft.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class ClayBucketWater extends Item {

    public ClayBucketWater(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();

        if (player == null) return InteractionResult.PASS;

        BlockPos placePos = pos.relative(context.getClickedFace());

        // BlockState blockState = level.getBlockState(placePos);
        // if (blockState.isAir()) {
        //     if (!level.isClientSide) {
        //         level.setBlock(placePos, Blocks.WATER.defaultBlockState(), 3);
        //         player.setItemInHand(context.getHand(), new ItemStack(ModItems.CLAY_BUCKET_EMPTY.get()));
        //     }
        //     return InteractionResult.SUCCESS;
        // } else

        BlockEntity blockEntity = level.getBlockEntity(placePos);
        if (blockEntity instanceof ClayTubBE clayTubBE) {
            if (!level.isClientSide) {
                clayTubBE.addFluid(new FluidStack(Fluids.WATER, 1000));
                player.setItemInHand(context.getHand(), new ItemStack(ModItems.CLAY_BUCKET_EMPTY.get()));
            }
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

}
