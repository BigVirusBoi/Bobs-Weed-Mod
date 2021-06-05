package me.bigvirusboi.weedmod.world;

import com.google.common.collect.ImmutableSet;
import me.bigvirusboi.weedmod.block.CannabisPlantBlock;
import me.bigvirusboi.weedmod.init.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class BiomeFeatures {
    public static final ConfiguredFeature<?, ?> PATCH_CANNABIS = register("patch_cannabis",
            Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(
                    BlockInit.CANNABIS_PLANT.get().getDefaultState().with(CannabisPlantBlock.AGE, 3)),
                    new ColumnBlockPlacer(1, 2))).tries(10).whitelist(ImmutableSet.of(Blocks.COARSE_DIRT, Blocks.GRASS_BLOCK))
                    .preventProjection().build()));

    public static void generateFeatures(final BiomeLoadingEvent e) {
        Biome.Category category = e.getCategory();
        BiomeGenerationSettingsBuilder gen = e.getGeneration();

        if (category == Biome.Category.SAVANNA) {
            addCannabis(gen);
        }
    }

    private static void addCannabis(BiomeGenerationSettingsBuilder builder) {
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, PATCH_CANNABIS.withPlacement(Features.Placements.PATCH_PLACEMENT)
                .withPlacement(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.5D, 6, 8))));
    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }
}
