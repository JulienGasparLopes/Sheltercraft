package com.pinzen.survivalist.survival_age;

import com.pinzen.survivalist.Survivalist;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Survivalist.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        ItemStack heldItem = event.getItemStack();
        InteractionHand hand = event.getHand();

        if (level.isClientSide()) return;

        if (heldItem.is(net.minecraft.world.item.Items.FLINT)) {
            if (player.getCooldowns().isOnCooldown(heldItem)) {
                event.setCancellationResult(InteractionResult.FAIL);
                return;
            }

            if (state.is(BlockTags.BASE_STONE_OVERWORLD) && event.getFace() == Direction.UP) {
                player.swing(hand, true);
                player.getCooldowns().addCooldown(heldItem, 10);

                // One chance out of three to spawn a custom item
                if (level.random.nextInt(3) != 0) {
                    return;
                }

                ItemStack customDrop = new ItemStack(Items.SHARPENED_FLINT.get());
                ItemEntity drop = new ItemEntity(
                        level,
                        pos.getX() + 0.5,
                        pos.getY() + 1,
                        pos.getZ() + 0.5,
                        customDrop
                );
                level.addFreshEntity(drop);

                // Remove 1 flint
                heldItem.shrink(1);

                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        LevelAccessor level = event.getLevel();
        BlockPos pos = event.getPos();

        if(!(level instanceof Level) || level.isClientSide()) return;

        if (event.getState().is(BlockTags.LOGS)) {
            if (!(event.getPlayer().getMainHandItem().getItem() instanceof AxeItem)){
                level.destroyBlock(pos, false);
            }
        }

        if (event.getState().is(BlockTags.LEAVES)) {
            if (level instanceof Level LevelCasted) {
                if (LevelCasted.random.nextInt(8) == 0) {
                    ItemStack stick = new ItemStack(net.minecraft.world.item.Items.STICK);
                    ItemEntity drop = new ItemEntity(
                            LevelCasted,
                            pos.getX() + 0.5,
                            pos.getY() + 0.5,
                            pos.getZ() + 0.5,
                            stick
                    );
                    LevelCasted.addFreshEntity(drop);
                }
            }
        }
    }
}
