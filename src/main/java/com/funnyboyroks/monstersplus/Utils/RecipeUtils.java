package com.funnyboyroks.monstersplus.Utils;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;

import java.util.Map;

public class RecipeUtils {

    public static final Map<Character, Material> RECIPE_PARTS_MAP =
        (new ImmutableMap.Builder<Character, Material>())
            .put('a', Material.IRON_AXE)
            .put('b', Material.TORCH)
            .put('c', Material.OBSIDIAN)
            .put('d', Material.SLIME_BALL)
            .put('e', Material.ENDER_PEARL)
            .put('f', Material.INK_SAC)
            .put('g', Material.LEAD)
            .put('h', Material.SPIDER_EYE)
            .put('i', Material.GLASS)
            .put('j', Material.IRON_INGOT)
            .put('k', Material.GUNPOWDER)
            .put('l', Material.STONE_BRICKS)
            .put('m', Material.BLAZE_ROD)
            .put('n', Material.ROTTEN_FLESH)
            .put('o', Material.BONE)
            .put('p', Material.ARROW)
            .put('q', Material.FLINT)
            .put('r', Material.STRING)
            .put('s', Material.VINE)
            .put('t', Material.ICE)
            .put('u', Material.WHEAT_SEEDS)
            .put('v', Material.COBBLESTONE)
            .put('w', Material.EMERALD)
            .put('x', Material.END_STONE)
            .put('y', Material.LEATHER)
            .put('z', Material.GOLD_INGOT)
            .put('A', Material.DIAMOND)
            .put('B', Material.EGG)
            .put('C', Material.WHEAT)
            .put('D', Material.CARROT)
            .put('E', Material.WHITE_WOOL)
            .put('F', Material.FEATHER)
            .put('G', Material.MELON_SEEDS)
            .put('H', Material.MILK_BUCKET)
            .put('I', Material.MUSHROOM_STEW)
            .put('J', Material.LAPIS_BLOCK)
            .put('K', Material.COAL)
            .put('L', Material.SPAWNER)
            .put('M', Material.GOLD_ORE)
            .put('N', Material.IRON_SWORD)
            .put('S', Material.STICK)
            .put('T', Material.COD)
            .put('U', Material.GLASS_BOTTLE)
            .put('V', Material.IRON_PICKAXE)
            .put('W', Material.FISHING_ROD)
            .put('X', Material.FURNACE)
            .build();

}
