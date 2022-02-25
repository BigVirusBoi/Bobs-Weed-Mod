package me.bigvirusboi.weedmod.world;

import me.bigvirusboi.weedmod.block.CannabisPlantBlock;
import me.bigvirusboi.weedmod.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;

public class BiomeFeatures {
    public static final ConfiguredFeature<?, ?> PATCH_CANNABIS = register("patch_cannabis",
            Feature.RANDOM_PATCH.configured(FeatureUtils.simpleRandomPatchConfiguration(10, Feature.BLOCK_COLUMN
                    .configured(BlockColumnConfiguration.simple(BiasedToBottomInt.of(1, 3),
                            BlockStateProvider.simple(BlockInit.CANNABIS_PLANT.get().defaultBlockState().setValue(CannabisPlantBlock.AGE, 3)
                            ))).placed(BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE,
                            BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)))))));

    private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }
}
