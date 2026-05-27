package com.ryo92k.fungusinforest;

import com.ryo92k.fungusinforest.worldgen.MushroomClusterFeature;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;

public class FungusInForest implements ModInitializer {
	public static final String MOD_ID = "fungus_in_forest";

	private static final Feature<DefaultFeatureConfig> BROWN_MUSHROOM_CLUSTER_FEATURE = registerFeature(
			"brown_mushroom_cluster",
			new MushroomClusterFeature(DefaultFeatureConfig.CODEC, Blocks.BROWN_MUSHROOM.getDefaultState(), 4, 5, 4)
	);
	private static final Feature<DefaultFeatureConfig> RED_MUSHROOM_CLUSTER_FEATURE = registerFeature(
			"red_mushroom_cluster",
			new MushroomClusterFeature(DefaultFeatureConfig.CODEC, Blocks.RED_MUSHROOM.getDefaultState(), 4, 5, 4)
	);

	private static final RegistryKey<PlacedFeature> FOREST_BROWN_MUSHROOM_CLUSTER = placedFeature("forest_brown_mushroom_cluster");
	private static final RegistryKey<PlacedFeature> FOREST_RED_MUSHROOM_CLUSTER = placedFeature("forest_red_mushroom_cluster");

	@Override
	public void onInitialize() {
		addForestVegetation(FOREST_BROWN_MUSHROOM_CLUSTER);
		addForestVegetation(FOREST_RED_MUSHROOM_CLUSTER);
	}

	private static void addForestVegetation(RegistryKey<PlacedFeature> feature) {
		BiomeModifications.addFeature(
				BiomeSelectors.foundInOverworld().and(
						BiomeSelectors.tag(BiomeTags.IS_FOREST)
								.or(BiomeSelectors.tag(BiomeTags.IS_TAIGA))
								.or(BiomeSelectors.tag(BiomeTags.IS_JUNGLE))
				),
				GenerationStep.Feature.VEGETAL_DECORATION,
				feature
		);
	}

	private static RegistryKey<PlacedFeature> placedFeature(String name) {
		return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, name));
	}

	private static Feature<DefaultFeatureConfig> registerFeature(String name, Feature<DefaultFeatureConfig> feature) {
		return Registry.register(Registries.FEATURE, Identifier.of(MOD_ID, name), feature);
	}
}
