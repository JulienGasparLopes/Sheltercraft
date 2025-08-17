package com.pinzen.sheltercraft.block.custom;

import com.mojang.serialization.MapCodec;
import com.pinzen.sheltercraft.block.ModBlocks;
import com.pinzen.sheltercraft.block.entity.custom.WoodenTubBE;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WoodenTub extends BaseEntityBlock {
    public static final MapCodec<WoodenTub> CODEC = simpleCodec(WoodenTub::new);

    public WoodenTub() {
        this(Properties.of());
    }

    public WoodenTub(Properties properties) {
        super(properties
                .setId(ModBlocks.REGISTER_BLOCKS.key("wooden_tub"))
                .strength(2.0f)
                .noOcclusion());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new WoodenTubBE(pPos, pState);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Block.box(0, 0, 0, 16, 7, 16);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder);

        BlockEntity be = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (be instanceof WoodenTubBE woodenTubBE) {
            ItemStack stored = woodenTubBE.getProcessingItem();
            if (!stored.isEmpty()) {
                drops.add(stored.copy());
            }
        }

        return drops;
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult
    ) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof WoodenTubBE woodenTubBE) {
            ItemStack heldItem = pPlayer.getItemInHand(pHand);
            if (heldItem.is(Items.WATER_BUCKET)) {
                if (woodenTubBE.addFluid(new FluidStack(Fluids.WATER, 1000))) {
                    if (!pPlayer.isCreative()) {
                        pPlayer.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET));
                    }
                    pLevel.sendBlockUpdated(pPos, pState, pState, 1 | 2 | 8);
                    return InteractionResult.SUCCESS;
                }
            } else if (woodenTubBE.addItem(heldItem)) {
                heldItem.shrink(1);
                pLevel.sendBlockUpdated(pPos, pState, pState, 1 | 2 | 8);
                return InteractionResult.SUCCESS;
            }
            else if (heldItem.isEmpty()) {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof WoodenTubBE woodenTubBE) {
            ItemStack retrievedItem = woodenTubBE.retrieveItem();
            if (!retrievedItem.isEmpty()) {
                if (!pLevel.isClientSide) {
                    pLevel.sendBlockUpdated(pPos, pState, pState, 1 | 2 | 8);
                    pPlayer.getInventory().placeItemBackInInventory(retrievedItem);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        if (!level.isClientSide && entity instanceof ItemEntity itemEntity) {
            ItemStack stack = itemEntity.getItem();
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof WoodenTubBE woodenTubBE) {
                if (woodenTubBE.tanFluid()) {
                    entity.discard();
                    level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0f, 1.0f);
                    stack.shrink(1); // consume one diamond
                    level.sendBlockUpdated(pos, state, state, 1 | 2 | 8);
                }
            }
        }
        super.entityInside(state, level, pos, entity, effectApplier);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlocks.WOODEN_TUB_BE.get(),
                (level, blockPos, blockState, growthChamberBlockEntity) -> growthChamberBlockEntity.tick(level, blockPos, blockState));
    }
}