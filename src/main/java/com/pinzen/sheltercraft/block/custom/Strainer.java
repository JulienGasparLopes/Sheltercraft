package com.pinzen.sheltercraft.block.custom;

import com.pinzen.sheltercraft.block.ModBlocks;
import com.pinzen.sheltercraft.block.entity.custom.StrainerBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class Strainer extends Block implements EntityBlock {
    public Strainer() {
        super(Block.Properties.of()
                .setId(ModBlocks.REGISTER_BLOCKS.key("strainer"))
                .strength(1.0f)
                .noOcclusion());
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Block.box(0, 0, 0, 16, 13, 16);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new StrainerBE(pPos, pState);
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult
    ) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        ItemStack heldItem = pPlayer.getMainHandItem();
        if (be instanceof StrainerBE strainerBE) {
            if(heldItem.is(Items.GRAVEL)) {
                boolean itemAdded = strainerBE.addItem(heldItem);
                if (itemAdded) {
                    pPlayer.swing(InteractionHand.MAIN_HAND);
                    if (!pLevel.isClientSide) {
                        pLevel.sendBlockUpdated(pPos, pState, pState, 1 | 2 | 8);
                        heldItem.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            else if (heldItem.isEmpty()) {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof StrainerBE strainerBE) {
            if (!pPlayer.getCooldowns().isOnCooldown(pPlayer.getMainHandItem())) {
                pPlayer.getCooldowns().addCooldown(pPlayer.getMainHandItem(), 10);
                pPlayer.swing(InteractionHand.MAIN_HAND);
                if (!pLevel.isClientSide) {
                    pLevel.sendBlockUpdated(pPos, pState, pState, 1 | 2 | 8);

                    List<ItemStack> results = strainerBE.shake(pPlayer);
                    if (!results.isEmpty()) {
                        for (ItemStack res : results) {
                            if (res.isEmpty()) continue;
                            pLevel.addFreshEntity(new ItemEntity(
                                    pLevel,
                                    pPos.getX() + 0.5,
                                    pPos.getY(),
                                    pPos.getZ() + 0.5,
                                    res
                            ));
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }
}
