package io.github.alloffabric.artis.util;

import blue.endless.jankson.JsonObject;
import io.github.alloffabric.artis.Artis;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

public class BlockSettingsParser {
    public static final Map<String, Material> MATERIALS = new HashMap<>();
    public static final Map<String, MapColor> MAP_COLORS = new HashMap<>();
    public static final Map<String, BlockSoundGroup> SOUND_GROUPS = new HashMap<>();

    static {
        MATERIALS.put("air", Material.AIR);
        MATERIALS.put("structure_void", Material.STRUCTURE_VOID);
        MATERIALS.put("portal", Material.PORTAL);
        MATERIALS.put("carpet", Material.CARPET);
        MATERIALS.put("plant", Material.PLANT);
        MATERIALS.put("underwater_plant", Material.UNDERWATER_PLANT);
        MATERIALS.put("replaceable_plant", Material.REPLACEABLE_PLANT);
        MATERIALS.put("nether_shoots", Material.NETHER_SHOOTS);
        MATERIALS.put("replaceable_underwater_plant", Material.REPLACEABLE_UNDERWATER_PLANT);
        MATERIALS.put("water", Material.WATER);
        MATERIALS.put("bubble_column", Material.BUBBLE_COLUMN);
        MATERIALS.put("lava", Material.LAVA);
        MATERIALS.put("snow_layer", Material.SNOW_LAYER);
        MATERIALS.put("fire", Material.FIRE);
        MATERIALS.put("decoration", Material.DECORATION);
        MATERIALS.put("cobweb", Material.COBWEB);
        MATERIALS.put("sculk", Material.SCULK);
        MATERIALS.put("redstone_lamp", Material.REDSTONE_LAMP);
        MATERIALS.put("organic_product", Material.ORGANIC_PRODUCT);
        MATERIALS.put("soil", Material.SOIL);
        MATERIALS.put("solid_organic", Material.SOLID_ORGANIC);
        MATERIALS.put("dense_ice", Material.DENSE_ICE);
        MATERIALS.put("aggregate", Material.AGGREGATE);
        MATERIALS.put("sponge", Material.SPONGE);
        MATERIALS.put("shulker_box", Material.SHULKER_BOX);
        MATERIALS.put("wood", Material.WOOD);
        MATERIALS.put("nether_wood", Material.NETHER_WOOD);
        MATERIALS.put("bamboo_sapling", Material.BAMBOO_SAPLING);
        MATERIALS.put("bamboo", Material.BAMBOO);
        MATERIALS.put("wool", Material.WOOL);
        MATERIALS.put("tnt", Material.TNT);
        MATERIALS.put("leaves", Material.LEAVES);
        MATERIALS.put("glass", Material.GLASS);
        MATERIALS.put("ice", Material.ICE);
        MATERIALS.put("cactus", Material.CACTUS);
        MATERIALS.put("stone", Material.STONE);
        MATERIALS.put("metal", Material.METAL);
        MATERIALS.put("snow_block", Material.SNOW_BLOCK);
        MATERIALS.put("repair_station", Material.REPAIR_STATION);
        MATERIALS.put("barrier", Material.BARRIER);
        MATERIALS.put("piston", Material.PISTON);
        MATERIALS.put("moss_block", Material.MOSS_BLOCK);
        MATERIALS.put("gourd", Material.GOURD);
        MATERIALS.put("egg", Material.EGG);
        MATERIALS.put("cake", Material.CAKE);
        MATERIALS.put("amethyst", Material.AMETHYST);
        MATERIALS.put("powder_snow", Material.POWDER_SNOW);

        MAP_COLORS.put("clear", MapColor.CLEAR);
        MAP_COLORS.put("pale_green", MapColor.PALE_GREEN);
        MAP_COLORS.put("pale_yellow", MapColor.PALE_YELLOW);
        MAP_COLORS.put("white_gray", MapColor.WHITE_GRAY);
        MAP_COLORS.put("bright_red", MapColor.BRIGHT_RED);
        MAP_COLORS.put("pale_purple", MapColor.PALE_PURPLE);
        MAP_COLORS.put("iron_gray", MapColor.IRON_GRAY);
        MAP_COLORS.put("dark_green", MapColor.DARK_GREEN);
        MAP_COLORS.put("white", MapColor.WHITE);
        MAP_COLORS.put("light_blue_gray", MapColor.LIGHT_BLUE_GRAY);
        MAP_COLORS.put("dirt_brown", MapColor.DIRT_BROWN);
        MAP_COLORS.put("stone_gray", MapColor.STONE_GRAY);
        MAP_COLORS.put("water_blue", MapColor.WATER_BLUE);
        MAP_COLORS.put("oak_tan", MapColor.OAK_TAN);
        MAP_COLORS.put("off_white", MapColor.OFF_WHITE);
        MAP_COLORS.put("orange", MapColor.ORANGE);
        MAP_COLORS.put("magenta", MapColor.MAGENTA);
        MAP_COLORS.put("light_blue", MapColor.LIGHT_BLUE);
        MAP_COLORS.put("yellow", MapColor.YELLOW);
        MAP_COLORS.put("lime", MapColor.LIME);
        MAP_COLORS.put("pink", MapColor.PINK);
        MAP_COLORS.put("gray", MapColor.GRAY);
        MAP_COLORS.put("light_gray", MapColor.LIGHT_GRAY);
        MAP_COLORS.put("cyan", MapColor.CYAN);
        MAP_COLORS.put("purple", MapColor.PURPLE);
        MAP_COLORS.put("blue", MapColor.BLUE);
        MAP_COLORS.put("brown", MapColor.BROWN);
        MAP_COLORS.put("green", MapColor.GREEN);
        MAP_COLORS.put("red", MapColor.RED);
        MAP_COLORS.put("black", MapColor.BLACK);
        MAP_COLORS.put("gold", MapColor.GOLD);
        MAP_COLORS.put("diamond_blue", MapColor.DIAMOND_BLUE);
        MAP_COLORS.put("lapis_blue", MapColor.LAPIS_BLUE);
        MAP_COLORS.put("emerald_green", MapColor.EMERALD_GREEN);
        MAP_COLORS.put("spruce_brown", MapColor.SPRUCE_BROWN);
        MAP_COLORS.put("dark_red", MapColor.DARK_RED);
        MAP_COLORS.put("terracotta_white", MapColor.TERRACOTTA_WHITE);
        MAP_COLORS.put("terracotta_orange", MapColor.TERRACOTTA_ORANGE);
        MAP_COLORS.put("terracotta_magenta", MapColor.TERRACOTTA_MAGENTA);
        MAP_COLORS.put("terracotta_light_blue", MapColor.TERRACOTTA_LIGHT_BLUE);
        MAP_COLORS.put("terracotta_yellow", MapColor.TERRACOTTA_YELLOW);
        MAP_COLORS.put("terracotta_lime", MapColor.TERRACOTTA_LIME);
        MAP_COLORS.put("terracotta_pink", MapColor.TERRACOTTA_PINK);
        MAP_COLORS.put("terracotta_gray", MapColor.TERRACOTTA_GRAY);
        MAP_COLORS.put("terracotta_light_gray", MapColor.TERRACOTTA_LIGHT_GRAY);
        MAP_COLORS.put("terracotta_cyan", MapColor.TERRACOTTA_CYAN);
        MAP_COLORS.put("terracotta_purple", MapColor.TERRACOTTA_PURPLE);
        MAP_COLORS.put("terracotta_blue", MapColor.TERRACOTTA_BLUE);
        MAP_COLORS.put("terracotta_brown", MapColor.TERRACOTTA_BROWN);
        MAP_COLORS.put("terracotta_green", MapColor.TERRACOTTA_GREEN);
        MAP_COLORS.put("terracotta_red", MapColor.TERRACOTTA_RED);
        MAP_COLORS.put("terracotta_black", MapColor.TERRACOTTA_BLACK);
        MAP_COLORS.put("dull_red", MapColor.DULL_RED);
        MAP_COLORS.put("dull_pink", MapColor.DULL_PINK);
        MAP_COLORS.put("dark_crimson", MapColor.DARK_CRIMSON);
        MAP_COLORS.put("teal", MapColor.TEAL);
        MAP_COLORS.put("dark_aqua", MapColor.DARK_AQUA);
        MAP_COLORS.put("dark_dull_pink", MapColor.DARK_DULL_PINK);
        MAP_COLORS.put("bright_teal", MapColor.BRIGHT_TEAL);
        MAP_COLORS.put("deepslate_gray", MapColor.DEEPSLATE_GRAY);
        MAP_COLORS.put("raw_iron_pink", MapColor.RAW_IRON_PINK);
        MAP_COLORS.put("lichen_green", MapColor.LICHEN_GREEN);

        SOUND_GROUPS.put("wood", BlockSoundGroup.WOOD);
        SOUND_GROUPS.put("gravel", BlockSoundGroup.GRAVEL);
        SOUND_GROUPS.put("grass", BlockSoundGroup.GRASS);
        SOUND_GROUPS.put("lily_pad", BlockSoundGroup.LILY_PAD);
        SOUND_GROUPS.put("stone", BlockSoundGroup.STONE);
        SOUND_GROUPS.put("metal", BlockSoundGroup.METAL);
        SOUND_GROUPS.put("glass", BlockSoundGroup.GLASS);
        SOUND_GROUPS.put("wool", BlockSoundGroup.WOOL);
        SOUND_GROUPS.put("sand", BlockSoundGroup.SAND);
        SOUND_GROUPS.put("snow", BlockSoundGroup.SNOW);
        SOUND_GROUPS.put("powder_snow", BlockSoundGroup.POWDER_SNOW);
        SOUND_GROUPS.put("ladder", BlockSoundGroup.LADDER);
        SOUND_GROUPS.put("anvil", BlockSoundGroup.ANVIL);
        SOUND_GROUPS.put("slime", BlockSoundGroup.SLIME);
        SOUND_GROUPS.put("honey", BlockSoundGroup.HONEY);
        SOUND_GROUPS.put("wet_grass", BlockSoundGroup.WET_GRASS);
        SOUND_GROUPS.put("coral", BlockSoundGroup.CORAL);
        SOUND_GROUPS.put("bamboo", BlockSoundGroup.BAMBOO);
        SOUND_GROUPS.put("bamboo_sapling", BlockSoundGroup.BAMBOO_SAPLING);
        SOUND_GROUPS.put("scaffolding", BlockSoundGroup.SCAFFOLDING);
        SOUND_GROUPS.put("sweet_berry_bush", BlockSoundGroup.SWEET_BERRY_BUSH);
        SOUND_GROUPS.put("crop", BlockSoundGroup.CROP);
        SOUND_GROUPS.put("stem", BlockSoundGroup.STEM);
        SOUND_GROUPS.put("vine", BlockSoundGroup.VINE);
        SOUND_GROUPS.put("nether_wart", BlockSoundGroup.NETHER_WART);
        SOUND_GROUPS.put("lantern", BlockSoundGroup.LANTERN);
        SOUND_GROUPS.put("nether_stem", BlockSoundGroup.NETHER_STEM);
        SOUND_GROUPS.put("nylium", BlockSoundGroup.NYLIUM);
        SOUND_GROUPS.put("fungus", BlockSoundGroup.FUNGUS);
        SOUND_GROUPS.put("roots", BlockSoundGroup.ROOTS);
        SOUND_GROUPS.put("shroomlight", BlockSoundGroup.SHROOMLIGHT);
        SOUND_GROUPS.put("weeping_vines", BlockSoundGroup.WEEPING_VINES);
        SOUND_GROUPS.put("weeping_vines_low_pitch", BlockSoundGroup.WEEPING_VINES_LOW_PITCH);
        SOUND_GROUPS.put("soul_sand", BlockSoundGroup.SOUL_SAND);
        SOUND_GROUPS.put("soul_soil", BlockSoundGroup.SOUL_SOIL);
        SOUND_GROUPS.put("basalt", BlockSoundGroup.BASALT);
        SOUND_GROUPS.put("wart_block", BlockSoundGroup.WART_BLOCK);
        SOUND_GROUPS.put("netherrack", BlockSoundGroup.NETHERRACK);
        SOUND_GROUPS.put("nether_bricks", BlockSoundGroup.NETHER_BRICKS);
        SOUND_GROUPS.put("nether_sprouts", BlockSoundGroup.NETHER_SPROUTS);
        SOUND_GROUPS.put("nether_ore", BlockSoundGroup.NETHER_ORE);
        SOUND_GROUPS.put("bone", BlockSoundGroup.BONE);
        SOUND_GROUPS.put("netherite", BlockSoundGroup.NETHERITE);
        SOUND_GROUPS.put("ancient_debris", BlockSoundGroup.ANCIENT_DEBRIS);
        SOUND_GROUPS.put("lodestone", BlockSoundGroup.LODESTONE);
        SOUND_GROUPS.put("chain", BlockSoundGroup.CHAIN);
        SOUND_GROUPS.put("nether_gold_ore", BlockSoundGroup.NETHER_GOLD_ORE);
        SOUND_GROUPS.put("gilded_blackstone", BlockSoundGroup.GILDED_BLACKSTONE);
        SOUND_GROUPS.put("candle", BlockSoundGroup.CANDLE);
        SOUND_GROUPS.put("amethyst_block", BlockSoundGroup.AMETHYST_BLOCK);
        SOUND_GROUPS.put("amethyst_cluster", BlockSoundGroup.AMETHYST_CLUSTER);
        SOUND_GROUPS.put("small_amethyst_bud", BlockSoundGroup.SMALL_AMETHYST_BUD);
        SOUND_GROUPS.put("medium_amethyst_bud", BlockSoundGroup.MEDIUM_AMETHYST_BUD);
        SOUND_GROUPS.put("large_amethyst_bud", BlockSoundGroup.LARGE_AMETHYST_BUD);
        SOUND_GROUPS.put("tuff", BlockSoundGroup.TUFF);
        SOUND_GROUPS.put("calcite", BlockSoundGroup.CALCITE);
        SOUND_GROUPS.put("dripstone_block", BlockSoundGroup.DRIPSTONE_BLOCK);
        SOUND_GROUPS.put("pointed_dripstone", BlockSoundGroup.POINTED_DRIPSTONE);
        SOUND_GROUPS.put("copper", BlockSoundGroup.COPPER);
        SOUND_GROUPS.put("cave_vines", BlockSoundGroup.CAVE_VINES);
        SOUND_GROUPS.put("spore_blossom", BlockSoundGroup.SPORE_BLOSSOM);
        SOUND_GROUPS.put("azalea", BlockSoundGroup.AZALEA);
        SOUND_GROUPS.put("flowering_azalea", BlockSoundGroup.FLOWERING_AZALEA);
        SOUND_GROUPS.put("moss_carpet", BlockSoundGroup.MOSS_CARPET);
        SOUND_GROUPS.put("moss_block", BlockSoundGroup.MOSS_BLOCK);
        SOUND_GROUPS.put("big_dripleaf", BlockSoundGroup.BIG_DRIPLEAF);
        SOUND_GROUPS.put("small_dripleaf", BlockSoundGroup.SMALL_DRIPLEAF);
        SOUND_GROUPS.put("rooted_dirt", BlockSoundGroup.ROOTED_DIRT);
        SOUND_GROUPS.put("hanging_roots", BlockSoundGroup.HANGING_ROOTS);
        SOUND_GROUPS.put("azalea_leaves", BlockSoundGroup.AZALEA_LEAVES);
        SOUND_GROUPS.put("sculk_sensor", BlockSoundGroup.SCULK_SENSOR);
        SOUND_GROUPS.put("glow_lichen", BlockSoundGroup.GLOW_LICHEN);
        SOUND_GROUPS.put("deepslate", BlockSoundGroup.DEEPSLATE);
        SOUND_GROUPS.put("deepslate_bricks", BlockSoundGroup.DEEPSLATE_BRICKS);
        SOUND_GROUPS.put("deepslate_tiles", BlockSoundGroup.DEEPSLATE_TILES);
        SOUND_GROUPS.put("polished_deepslate", BlockSoundGroup.POLISHED_DEEPSLATE);
    }

    public static Block.Settings parseSettings(JsonObject json) {
        if (json == null) {
            Artis.log(Level.ERROR, "Cannot parse block settings that aren't a json object!");
            return FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE);
        }
        FabricBlockSettings settings;
        if (json.containsKey("copy")) {
            String copyBlockArg = json.get(String.class, "copy");
            settings = FabricBlockSettings.copyOf(Registry.BLOCK.get(new Identifier(copyBlockArg)));
        } else if (json.containsKey("material")) {
            String materialArg = json.get(String.class, "material");
            settings = FabricBlockSettings.of(MATERIALS.get(materialArg));
        } else {
            settings = FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE);
        }

        if (json.containsKey("requires_tool") && json.getBoolean("requires_tool", false)) {
            settings.requiresTool();
        } else {
            settings.breakByHand(true);
        }

        if (json.containsKey("material_color")) {
            settings.mapColor(MAP_COLORS.get(json.get(String.class, "material_color")));
        }

        if (json.containsKey("collidable")) {
            settings.collidable(json.getBoolean("collidable", true));
        }

        if (json.containsKey("non_opaque")) {
            settings.nonOpaque();
        }

        if (json.containsKey("sounds")) {
            String sounds = json.get(String.class, "sounds");
            settings.sounds(SOUND_GROUPS.get(sounds));
        }

        if (json.containsKey("light_level")) {
            settings.luminance(json.getInt("light_level", 0));
        }

        if (json.containsKey("hardness")) {
            settings.hardness(json.getFloat("hardness", 0));
        }

        if (json.containsKey("resistance")) {
            settings.resistance(json.getFloat("resistance", 0));
        }

        if (json.containsKey("slipperiness")) {
            settings.slipperiness(json.getFloat("slipperiness", 0));
        }

        if (json.containsKey("break_instantly")) {
            settings.breakInstantly();
        }

        if (json.containsKey("drops_nothing")) {
            settings.dropsNothing();
        } else if (json.containsKey("drops_like")) {
            settings.dropsLike(Registry.BLOCK.get(new Identifier(json.get(String.class, "drops_like"))));
        } else if (json.containsKey("drops")) {
            settings.drops(new Identifier(json.get(String.class, "drops")));
        }

        if (json.containsKey("dynamic_bounds")) {
            settings.dynamicBounds();
        }

        return settings;
    }
}
