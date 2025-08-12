package com.pinzen.survivalist.survival_age;

import com.pinzen.survivalist.Survivalist;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Survivalist.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(Tags.MINEABLE_WITH_SICKLE)
                .add(Blocks.SHORT_GRASS)
                .add(Blocks.TALL_GRASS)
                .add(Blocks.FERN)
                .add(Blocks.LARGE_FERN);
        Survivalist.LOGGER.warn("jkj done add tags !!!");
    }
}