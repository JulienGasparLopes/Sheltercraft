package com.pinzen.sheltercraft.block.custom;

import com.pinzen.sheltercraft.block.ModBlocks;
import com.pinzen.sheltercraft.block.entity.custom.CuttingStumpBE;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class CuttingStump extends Block implements EntityBlock {

    public CuttingStump() {
        super(Block.Properties.of()
                .setId(ModBlocks.REGISTER_BLOCKS.key("cutting_stump"))
                .strength(2.0f)
                .requiresCorrectToolForDrops()
                .noOcclusion());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CuttingStumpBE(pPos, pState);
    }

    @Override
    protected void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        if (!pLevel.isClientSide) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof CuttingStumpBE cuttingStumpBE) {
                ItemStack heldItem = pPlayer.getMainHandItem();
                if (heldItem.is(ItemTags.AXES)) {
                    ItemStack planks = cuttingStumpBE.processCutting(pPlayer);
                    if (!planks.isEmpty()) {
                        pLevel.sendBlockUpdated(pPos, pState, pState, 1 | 2 | 8);
                        pLevel.addFreshEntity(new ItemEntity(
                                pLevel,
                                pPos.getX() + 0.5,
                                pPos.getY() + 1,
                                pPos.getZ() + 0.5,
                                planks
                        ));
                        return;
                    }
                }
            }
        }
        super.attack(pState, pLevel, pPos, pPlayer);
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult
    ) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        ItemStack heldItem = pPlayer.getMainHandItem();
        if (be instanceof CuttingStumpBE cuttingStumpBE) {
            boolean itemAdded = cuttingStumpBE.addItem(heldItem);
            if (itemAdded) {
                if (!pLevel.isClientSide) {
                    pLevel.sendBlockUpdated(pPos, pState, pState, 1 | 2 | 8);
                    heldItem.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Block.box(0, 0, 0, 16, 4, 16);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder);

        BlockEntity be = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (be instanceof CuttingStumpBE myBe) {
            ItemStack stored = myBe.getHeldItem();
            if (!stored.isEmpty()) {
                drops.add(stored.copy());
            }
        }

        return drops;
    }
}
