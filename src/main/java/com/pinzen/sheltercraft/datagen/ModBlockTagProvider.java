package com.pinzen.sheltercraft.datagen;

import com.pinzen.sheltercraft.Sheltercraft;
import com.pinzen.sheltercraft.block.ModBlocks;
import com.pinzen.sheltercraft.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Sheltercraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.MINEABLE_WITH_SICKLE)
                .add(Blocks.SHORT_GRASS)
                .add(Blocks.TALL_GRASS)
                .add(Blocks.FERN)
                .add(Blocks.LARGE_FERN);

        // FLINT is equivalent to WOOD (+ NEED_SHARPENED_FLINT_TOOL)
        tag(ModTags.INCORRECT_FOR_FLINT_TOOL)
                .addTag(ModTags.NEED_SHARPENED_FLINT_TOOL)
                .addTag(BlockTags.NEEDS_STONE_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.NEED_SHARPENED_FLINT_TOOL)
                .add(ModBlocks.GRANITE_WITH_WHETSTONE.get());

        // SHARPENED_FLINT is equivalent to STONE
        tag(ModTags.INCORRECT_FOR_SHARPENED_FLINT_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);
    }
}