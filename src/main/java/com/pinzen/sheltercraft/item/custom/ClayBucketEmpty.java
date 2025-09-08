package com.pinzen.sheltercraft.item.custom;

import com.pinzen.sheltercraft.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class ClayBucketEmpty extends Item {
    public ClayBucketEmpty(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand pHand) {
        BlockHitResult ray = (BlockHitResult) player.pick(5.0D, 0.0F, true);

        if (!level.isClientSide()) {
            if (ray.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = ray.getBlockPos();
                if (level.getBlockState(pos).is(Blocks.WATER)) {
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    player.getMainHandItem().shrink(1);
                    player.addItem(new ItemStack(ModItems.CLAY_BUCKET_WATER.get()));
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }

}
