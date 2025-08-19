package com.pinzen.sheltercraft.block.custom;

import com.mojang.serialization.MapCodec;
import com.pinzen.sheltercraft.block.ModBlocks;
import com.pinzen.sheltercraft.block.entity.custom.DryingRackBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DryingRack extends BaseEntityBlock {
    public static final MapCodec<WoodenTub> CODEC = simpleCodec(WoodenTub::new);

    public DryingRack() {
        super(Properties.of()
                .setId(ModBlocks.REGISTER_BLOCKS.key("drying_rack"))
                .strength(1.0f)
                .noOcclusion());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DryingRackBE(pPos, pState);
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult
    ) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        ItemStack heldItem = pPlayer.getMainHandItem();
        if (be instanceof DryingRackBE dryingRackBE) {
            boolean itemAdded = dryingRackBE.addItem(heldItem);
            if (itemAdded) {
                if (!pLevel.isClientSide) {
                    pLevel.sendBlockUpdated(pPos, pState, pState, 1 | 2 | 8);
                    heldItem.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        if (pStack.isEmpty()) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof DryingRackBE dryingRackBE) {
            ItemStack retrievedItem = dryingRackBE.retrieveItem();
            if (!retrievedItem.isEmpty()) {
                pPlayer.addItem(retrievedItem.copy());
                if (!pLevel.isClientSide) {
                    pLevel.sendBlockUpdated(pPos, pState, pState, 1 | 2 | 8);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder);

        BlockEntity be = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (be instanceof DryingRackBE dryingRackBE) {
            List<ItemStack> storedItems = dryingRackBE.getDryingItems();
            for (ItemStack stored : storedItems) {
                if (!stored.isEmpty()) {
                    drops.add(stored.copy());
                }
            }
        }

        return drops;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlocks.DRYING_RACK_BE.get(),
                (level, blockPos, blockState, growthChamberBlockEntity) -> growthChamberBlockEntity.tick(level, blockPos, blockState));
    }
}
