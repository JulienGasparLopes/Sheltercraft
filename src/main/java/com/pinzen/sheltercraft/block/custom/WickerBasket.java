package com.pinzen.sheltercraft.block.custom;

import com.pinzen.sheltercraft.block.ModBlocks;
import com.pinzen.sheltercraft.block.entity.custom.WickerBasketBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WickerBasket extends Block implements EntityBlock {
    public WickerBasket() {
        super(Block.Properties.of()
                .setId(ModBlocks.REGISTER_BLOCKS.key("wicker_basket"))
                .strength(0.75f)
                .noOcclusion());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new WickerBasketBE(pPos, pState);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Block.box(2, 0, 2, 12, 14, 12);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof WickerBasketBE chest) {
                ((ServerPlayer)pPlayer).openMenu(new SimpleMenuProvider(chest, Component.literal("Wicker Basket")), pPos);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
