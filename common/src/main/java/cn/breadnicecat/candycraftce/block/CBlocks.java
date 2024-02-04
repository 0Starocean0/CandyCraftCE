package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.block.blocks.*;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.CommonUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.isClient;
import static cn.breadnicecat.candycraftce.block.CBlockBuilder.create;
import static cn.breadnicecat.candycraftce.sound.CSoundTypes.JELLY_FOOTSTEP;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;


/**
 * Created in 2023/11/26 9:41
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CBlocks {

	private static final Logger LOGGER = CLogUtils.sign();

	static {
		CandyCraftCE.hookPostBootstrap(() -> BLOCKS = Collections.unmodifiableMap(CBlocks.BLOCKS));
		if (isClient()) {
			CandyCraftCE.hookMinecraftSetup(CBlocks::declareRendererType);
		}
	}

	public static Map<ResourceLocation, BlockEntry<? extends Block>> BLOCKS = new HashMap<>();
	public static final BlockEntry<SugarBlock> SUGAR_BLOCK = create("sugar_block", SugarBlock::new).setProperties(Blocks.SAND, Properties::randomTicks).simpleBlockItem().save();

	public static final BlockEntry<Block> CARAMEL_BLOCK = create("caramel_block").setProperties(Blocks.STONE, null).simpleBlockItem().save();
	public static final BlockEntry<CaramelPortal> CARAMEL_PORTAL = create("caramel_portal", CaramelPortal::new).setProperties(Blocks.NETHER_PORTAL, null).save();

	public static final BlockEntry<Block> CHOCOLATE_STONE = create("chocolate_stone").setProperties(Blocks.STONE, null).simpleBlockItem().save();
	public static final BlockEntry<Block> CHOCOLATE_COBBLESTONE = create("chocolate_cobblestone").setProperties(Blocks.COBBLESTONE, null).simpleBlockItem().save();

	public static final BlockEntry<Block> PUDDING = create("pudding").setProperties(Blocks.DIRT, null).simpleBlockItem().save();
	public static final BlockEntry<CustardPudding> CUSTARD_PUDDING = create("custard_pudding", CustardPudding::new).setProperties(Blocks.GRASS_BLOCK, p -> p.sound(JELLY_FOOTSTEP)).simpleBlockItem().save();
	public static final BlockEntry<PuddingFarm> PUDDING_FARMLAND = create("pudding_farmland", PuddingFarm::new).setProperties(Blocks.FARMLAND, p -> p.sound(JELLY_FOOTSTEP)).simpleBlockItem().save();

	public static final BlockEntry<Block> CANDY_CANE_BLOCK = create("candy_cane_block").setProperties(Blocks.STONE, null).simpleBlockItem().save();
	public static final BlockEntry<WallBlock> CANDY_CANE_WALL = wallBlock("candy_cane_wall").setProperties(CANDY_CANE_BLOCK, null).simpleBlockItem().save();
	public static final BlockEntry<FenceBlock> CANDY_CANE_FENCE = fenceBlock("candy_cane_fence").setProperties(CANDY_CANE_BLOCK, null).simpleBlockItem().save();
	public static final BlockEntry<SlabBlock> CANDY_CANE_SLAB = slabBlock("candy_cane_slab").setProperties(CANDY_CANE_BLOCK, null).simpleBlockItem().save();
	public static final BlockEntry<StairBlock> CANDY_CANE_STAIRS = stairBlock("candy_cane_stairs", CANDY_CANE_BLOCK::defaultBlockState).setProperties(CANDY_CANE_BLOCK, null).simpleBlockItem().save();

	public static final BlockEntry<MarshmallowCraftingTable> MARSHMALLOW_CRAFTING_TABLE = create("marshmallow_crafting_table", MarshmallowCraftingTable::new).setProperties(Blocks.CRAFTING_TABLE, null).simpleBlockItem().save();
	public static final BlockEntry<LicoriceFurnace> LICORICE_FURNACE = create("licorice_furnace", LicoriceFurnace::new).setProperties(Blocks.FURNACE, null).simpleBlockItem().save();
	public static final BlockEntry<ChocolateFurnace> CHOCOLATE_FURNACE = create("chocolate_furnace", ChocolateFurnace::new).setProperties(LICORICE_FURNACE, null).simpleBlockItem().save();
	public static final BlockEntry<SugarFactory> SUGAR_FACTORY = create("sugar_factory", SugarFactory::new).setProperties(CANDY_CANE_BLOCK, null).simpleBlockItem().save();
	public static final BlockEntry<AdvancedSugarFactory> ADVANCED_SUGAR_FACTORY = create("advanced_sugar_factory", AdvancedSugarFactory::new).setProperties(SUGAR_FACTORY, null).simpleBlockItem().save();

//HELPER.single(SUGAR_FACTORY, BlockSugarFactory::new, CCBlockManager::simpleBlockItem,
//        MODEL_SIMPLE, LOOT_DROP_SELF, VTAG_MINEABLE_WITH_PICKAXE);
//HELPER.single(ADVANCED_SUGAR_FACTORY, BlockAdvancedSugarFactory::new, CCBlockManager::simpleBlockItem,
//        MODEL_SIMPLE, LOOT_DROP_SELF, VTAG_MINEABLE_WITH_PICKAXE);

	@Environment(EnvType.CLIENT)
	private static void declareRendererType() {
		accept((b) -> ItemBlockRenderTypes.TYPE_BY_BLOCK.put(b.get(), RenderType.translucent()),
				CARAMEL_PORTAL);
//		accept((b) -> ItemBlockRenderTypes.TYPE_BY_BLOCK.put(b.getBlock(), RenderType.cutout()),
//		);
	}

	public static void init() {
		CommonUtils.logInit(LOGGER);
	}

	public static CBlockBuilder<WallBlock> wallBlock(String name) {
		return create(name, WallBlock::new);
	}

	public static CBlockBuilder<FenceBlock> fenceBlock(String name) {
		return create(name, FenceBlock::new);
	}

	public static CBlockBuilder<SlabBlock> slabBlock(String name) {
		return create(name, SlabBlock::new);
	}

	//Models
	public static CBlockBuilder<StairBlock> stairBlock(String name, Supplier<BlockState> base) {
		return create(name, p -> _stairBlock(base, p));
	}

	//  Platform difference
	@ExpectPlatform
	public static StairBlock _stairBlock(Supplier<BlockState> base, Properties p) {
		return CommonUtils.impossible();
	}
}