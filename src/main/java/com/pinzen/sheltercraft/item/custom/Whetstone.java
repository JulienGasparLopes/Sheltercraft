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
            ItemStack offhand = player.getOffhandItem();
            ItemStack mainHand = player.getMainHandItem();
            if (offhand.is(ModItems.FLINT_FLAKE.get())) {
                if (remainingUseDuration <= 1) {
                    ItemStack sharpened = new ItemStack(ModItems.SHARPENED_FLINT.get());
                    offhand.shrink(1);
                    mainHand.shrink(1);
                    player.addItem(sharpened);
                    return;
                }
                if (remainingUseDuration % 5 == 0) {
                    level.playSound(null, player.blockPosition(), SoundEvents.STONE_HIT, SoundSource.PLAYERS, 0.5f, 1.0f);
                    // player.swing(InteractionHand.MAIN_HAND); // Not Working
                    player.spawnItemParticles(mainHand, 7);
                }
            } else {
                player.releaseUsingItem();
            }
        }
    }
}