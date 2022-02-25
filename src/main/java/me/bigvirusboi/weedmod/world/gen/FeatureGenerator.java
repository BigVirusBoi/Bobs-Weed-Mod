package me.bigvirusboi.weedmod.world.gen;

import me.bigvirusboi.weedmod.world.BiomePlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class FeatureGenerator {
    public static void generateFeatures(final BiomeLoadingEvent e) {
        Biome.BiomeCategory category = e.getCategory();
        BiomeGenerationSettingsBuilder gen = e.getGeneration();

        if (category != Biome.BiomeCategory.THEEND && category != Biome.BiomeCategory.NETHER) {
            addCannabis(gen);
        }
    }

    private static void addCannabis(BiomeGenerationSettingsBuilder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BiomePlacements.PATCH_CANNABIS);
    }
}
