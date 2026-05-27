package com.ryo92k.fungusinforest.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class MushroomClusterFeature extends Feature<DefaultFeatureConfig> {
	private final BlockState mushroom;
	private final int minSize;
	private final int maxSize;
	private final int radius;

	public MushroomClusterFeature(Codec<DefaultFeatureConfig> codec, BlockState mushroom, int minSize, int maxSize, int radius) {
		super(codec);
		this.mushroom = mushroom;
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.radius = radius;
	}

	@Override
	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		StructureWorldAccess world = context.getWorld();
		Random random = context.getRandom();
		BlockPos origin = context.getOrigin();
		int targetSize = minSize + random.nextInt(maxSize - minSize + 1);
		int placed = 0;

		for (int attempt = 0; attempt < targetSize * 5 && placed < targetSize; attempt++) {
			int x = origin.getX() + random.nextInt(radius * 2 + 1) - radius;
			int z = origin.getZ() + random.nextInt(radius * 2 + 1) - radius;
			BlockPos surface = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(x, origin.getY(), z));
			BlockPos ground = surface.down();

			if (canPlaceOn(world.getBlockState(ground)) && world.isAir(surface)) {
				world.setBlockState(surface, mushroom, 2);
				placed++;
			}
		}

		return placed > 0;
	}

	private static boolean canPlaceOn(BlockState state) {
		return state.isOf(Blocks.GRASS_BLOCK)
				|| state.isOf(Blocks.DIRT)
				|| state.isOf(Blocks.COARSE_DIRT)
				|| state.isOf(Blocks.PODZOL)
				|| state.isOf(Blocks.ROOTED_DIRT)
				|| state.isOf(Blocks.MOSS_BLOCK);
	}
}
