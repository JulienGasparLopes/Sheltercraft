package com.pinzen.sheltercraft.worldgen;

import com.pinzen.sheltercraft.Sheltercraft;
import com.pinzen.sheltercraft.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_GRANIT_WITH_WHETSTONE_KEY = registerKey("granit_with_whetstone");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest granitReplaceables = new BlockMatchTest(Blocks.GRANITE);

        List<OreConfiguration.TargetBlockState> overworldGranitWithWhetstones = List.of(
                OreConfiguration.target(granitReplaceables, ModBlocks.GRANIT_WITH_WHETSTONE.get().defaultBlockState())
        );

        register(context, OVERWORLD_GRANIT_WITH_WHETSTONE_KEY, Feature.ORE, new OreConfiguration(overworldGranitWithWhetstones, 10));

    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Sheltercraft.MOD_ID, name));
    }
}