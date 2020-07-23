package net.draycia.gunpowder;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class GunpowderMod implements ModInitializer {

	// Blocks
	public static final Block SULFUR_ORE = new Block(FabricBlockSettings.copyOf(Blocks.COAL_ORE));
	public static final Block NITER_ORE = new Block(FabricBlockSettings.copyOf(Blocks.COAL_ORE));

	public static final Block SULFUR_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.COAL_BLOCK));
	public static final Block NITER_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.COAL_BLOCK));

	// BlockItems
	public static final Item SULFUR_ORE_ITEM = new BlockItem(SULFUR_ORE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
	public static final Item NITER_ORE_ITEM = new BlockItem(NITER_ORE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

	public static final Item SULFUR_BLOCK_ITEM = new BlockItem(SULFUR_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
	public static final Item NITER_BLOCK_ITEM = new BlockItem(NITER_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

	// Items
	public static final Item SULFUR = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
	// TODO: make NITER work as a bonemeal
	public static final Item NITER = new Item(new Item.Settings().group(ItemGroup.MATERIALS));

	@Override
	public void onInitialize() {
		// Register blocks
		Registry.register(Registry.BLOCK, new Identifier("gunpowder", "sulfur_ore"), SULFUR_ORE);
		Registry.register(Registry.BLOCK, new Identifier("gunpowder", "niter_ore"), NITER_ORE);

		Registry.register(Registry.BLOCK, new Identifier("gunpowder", "sulfur_block"), SULFUR_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier("gunpowder", "niter_block"), NITER_BLOCK);

		// Register items
		Registry.register(Registry.ITEM, new Identifier("gunpowder", "sulfur_ore"), SULFUR_ORE_ITEM);
		Registry.register(Registry.ITEM, new Identifier("gunpowder", "niter_ore"), NITER_ORE_ITEM);

		Registry.register(Registry.ITEM, new Identifier("gunpowder", "sulfur_block"), SULFUR_BLOCK_ITEM);
		Registry.register(Registry.ITEM, new Identifier("gunpowder", "niter_block"), NITER_BLOCK_ITEM);

		Registry.register(Registry.ITEM, new Identifier("gunpowder", "sulfur"), SULFUR);
		Registry.register(Registry.ITEM, new Identifier("gunpowder", "niter"), NITER);

		// Register ores
		Registry.BIOME.forEach(this::handleBiome);
		RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> handleBiome(biome));
	}

	private void handleBiome(Biome biome) {
		if (biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
			addOre(biome, SULFUR_ORE, 5, 10);
			addOre(biome, NITER_ORE, 5, 10);
		}
	}

	private void addOre(Biome biome, Block ore, int size, int count) {
		biome.addFeature(
				GenerationStep.Feature.UNDERGROUND_ORES,
				Feature.ORE.configure(
						new OreFeatureConfig(
								OreFeatureConfig.Target.NATURAL_STONE,
								ore.getDefaultState(),
								size //Ore vein size
						)).createDecoratedFeature(
						Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(
								count, //Number of veins per chunk
								0, //Bottom Offset
								0, //Min y level
								64 //Max y level
						))));
	}

}
