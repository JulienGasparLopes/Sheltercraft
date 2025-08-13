package com.pinzen.sheltercraft.item.custom;

import com.pinzen.sheltercraft.item.ModItems;
import com.pinzen.sheltercraft.util.Helpers;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Whetstone extends Item {

    public Whetstone() {
        super(new Item.Properties().setId(Helpers.resKeyItem("whetstone")));
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
        return 20 * 3;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player.getOffhandItem().is(ModItems.FLINT_FLAKE.get())) {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }

        return InteractionResult.FAIL;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (player.getOffhandItem().is(Items.FLINT)) {
                if (remainingUseDuration % 5 == 0) {
                    level.playSound(null, player.blockPosition(), SoundEvents.GRINDSTONE_USE, SoundSource.PLAYERS, 0.5f, 1.0f);
                }
            } else {
                player.releaseUsingItem();
            }
        }
    }

    @Override
    public boolean releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntity, int pTimeLeft) {
        if (!pLevel.isClientSide && pEntity instanceof Player player) {
            ItemStack offhand = player.getOffhandItem();

            if (offhand.is(ModItems.FLINT_FLAKE.get())) {
                ItemStack sharpened = new ItemStack(ModItems.SHARPENED_FLINT.get());
                offhand.shrink(1);
                player.addItem(sharpened);
                return true;
            }
        }
        return super.releaseUsing(pStack, pLevel, pEntity, pTimeLeft);
    }
}