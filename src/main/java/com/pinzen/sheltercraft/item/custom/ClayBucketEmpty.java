package com.pinzen.sheltercraft.item.custom;

import com.pinzen.sheltercraft.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ClayBucketEmpty extends Item {
    public ClayBucketEmpty(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();

        if (player == null) return InteractionResult.PASS;

        BlockState state = level.getBlockState(pos);
        if (state.getBlock() == Blocks.WATER && state.getFluidState().isSource()) {
            if (!level.isClientSide) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                player.setItemInHand(context.getHand(), new ItemStack(ModItems.CLAY_BUCKET_WATER.get()));
            }
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

}
