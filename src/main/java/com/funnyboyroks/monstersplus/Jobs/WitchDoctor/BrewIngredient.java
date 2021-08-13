package com.funnyboyroks.monstersplus.Jobs.WitchDoctor;

import com.funnyboyroks.monstersplus.Data.structs.JobType;
import com.funnyboyroks.monstersplus.Utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.stream.Collectors;

public enum BrewIngredient {
    MAGMA_CREAM            (Material.MAGMA_CREAM, PotionEffectType.FIRE_RESISTANCE, new int[]{ 120, 240, 360 }, new int[]{ 0, 1, 2 }, 2, "Magma Cream - Fire Resistance"),
    CACTUS                 (Material.CACTUS,                 PotionEffectType.HARM,              new int[]{ 0, 0, 0 },       new int[]{ 0, 1, 1 }, 2, "Cactus - Instant Damage"),
    GLISTERING_MELON_SLICE (Material.GLISTERING_MELON_SLICE, PotionEffectType.HEAL,              new int[]{ 0, 0, 0 },       new int[]{ 0, 1, 2 }, 2, "Glistering Melon - Instant Health"),
    ROTTEN_FLESH           (Material.ROTTEN_FLESH,           PotionEffectType.HUNGER,            new int[]{ 20, 40, 60 },    new int[]{ 0, 1, 2 }, 2, "Rotten Flesh - Hunger"),
    BLAZE_POWDER           (Material.BLAZE_POWDER,           PotionEffectType.INCREASE_DAMAGE,   new int[]{ 45, 90, 135 },   new int[]{ 0, 1, 1 }, 2, "Blaze Powder - Increased Damage"),
    GOLDEN_CARROT          (Material.GOLDEN_CARROT,          PotionEffectType.NIGHT_VISION,      new int[]{ 120, 240, 360 }, new int[]{ 0, 2, 4 }, 2, "Golden Carrot - Night Vision"),
    SPIDER_EYE             (Material.SPIDER_EYE,             PotionEffectType.POISON,            new int[]{ 10, 20, 30 },    new int[]{ 0, 1, 1 }, 2, "Spider Eye - Poison"),
    GHAST_TEAR             (Material.GHAST_TEAR,             PotionEffectType.REGENERATION,      new int[]{ 15, 30, 45 },    new int[]{ 0, 1, 1 }, 2, "Ghast Tear - Regeneration"),
    SLIME_BALL             (Material.SLIME_BALL,             PotionEffectType.SLOW,              new int[]{ 20, 40, 60 },    new int[]{ 0, 1, 2 }, 2, "Slimeball - Slow"),
    INK_SAC                (Material.INK_SAC,                PotionEffectType.SLOW_DIGGING,      new int[]{ 60, 120, 180 },  new int[]{ 0, 1, 2 }, 2, "Ink Sack - Slow Digging"),
    SUGAR                  (Material.SUGAR,                  PotionEffectType.SPEED,             new int[]{ 90, 180, 270 },  new int[]{ 0, 2, 3 }, 2, "Sugar - Speed"),
    FERMENTED_SPIDER_EYE   (Material.FERMENTED_SPIDER_EYE,   PotionEffectType.WEAKNESS,          new int[]{ 15, 30, 45 },    new int[]{ 0, 1, 2 }, 2, "Fermented Spider Eye - Weakness"),
    POISONOUS_POTATO       (Material.POISONOUS_POTATO,       PotionEffectType.CONFUSION,         new int[]{ 30, 60, 90 },    new int[]{ 0, 2, 4 }, 3, "Poisonous Potato - Confusion"),
    COAL_BLOCK             (Material.COAL_BLOCK,             PotionEffectType.FAST_DIGGING,      new int[]{ 60, 120, 180 },  new int[]{ 0, 1, 1 }, 4, "Coal Block - Fast Digging"),
    FEATHER                (Material.FEATHER,                PotionEffectType.JUMP,              new int[]{ 30, 60, 90 },    new int[]{ 0, 1, 3 }, 4, "Feather - Jump Boost"),
    DIAMOND                (Material.DIAMOND,                PotionEffectType.INVISIBILITY,      new int[]{ 45, 90, 135 },   new int[]{ 0, 0, 0 }, 5, "Diamond - Invisibility"),
    ENDER_PEARL            (Material.ENDER_PEARL,            PotionEffectType.BLINDNESS,         new int[]{ 20, 40, 60 },    new int[]{ 0, 2, 4 }, 6, "Ender Pearl - Blindness"),
    IRON_INGOT             (Material.IRON_INGOT,             PotionEffectType.DAMAGE_RESISTANCE, new int[]{ 20, 40, 60 },    new int[]{ 0, 1, 2 }, 7, "Iron Ingot - Damage Resistance"),
    SALMON                 (Material.SALMON,                 PotionEffectType.WATER_BREATHING,   new int[]{ 120, 240, 360 }, new int[]{ 0, 0, 0 }, 8, "Raw Salmon - Water Breathing"),

    GUNPOWDER              (Material.GUNPOWDER,              "Splash",    3,  "Gunpowder - Splash Modifier"),
    REDSTONE_BLOCK         (Material.REDSTONE_BLOCK,         "Extend 1",  6,  "Redstone Block - Extend 1"),
    LAPIS_BLOCK            (Material.LAPIS_BLOCK,            "Extend 2",  13, "Lapis Block - Extend 2"),
    QUARTZ_BLOCK           (Material.QUARTZ_BLOCK,           "Amplify 1", 8,  "Quartz Block - Amplify 1"),
    IRON_BLOCK             (Material.IRON_BLOCK,             "Amplify 2", 20, "Iron Block - Amplify 2"),
    ;

    public final Material mat;
    public final PotionEffectType type;
    public final String special;
    public final int[] durations;
    public final int[] amps;
    public final int lvl;
    public final String desc;

    BrewIngredient(Material mat, PotionEffectType type, String special, int[] durations, int[] amps, int lvl, String desc) {
        this.mat = mat;
        this.type = type;
        this.special = special;
        this.durations = durations;
        this.amps = amps;
        this.lvl = lvl;
        this.desc = desc;
    }

    BrewIngredient(Material mat, PotionEffectType type, int[] durations, int[] amps, int lvl, String desc) {
        this (mat, type, null, durations, amps, lvl, desc);
    }

    BrewIngredient(Material mat, String special, int lvl, String desc) {
        this (mat, null, special, null, null, lvl, desc);
    }

    public boolean canUse(Player player) {
        return PlayerUtils.getJobLvl(player, JobType.WITCH_DOCTOR) >= lvl;
    }

    public static BrewIngredient getIngredient(Material mat) {
        try {
            return valueOf(mat.name());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static BrewIngredient getIngredient(PotionEffectType type) {
        for(BrewIngredient ing : values()) {
            if(ing.type == type) {
                return ing;
            }
        }
        return null;
    }

    public static List<PotionEffectType> getEffectTypes(List<BrewIngredient> ings) {
        return getPotionTypes(ings)
            .stream()
            .map(ing -> ing.type)
            .collect(Collectors.toList());
    }

    public static List<BrewIngredient> getPotionTypes(List<BrewIngredient> ings) {
        return ings
            .stream()
            .filter(ing -> ing.type != null)
            .collect(Collectors.toList());
    }

    public PotionEffect getPotionEffect(int extend, int amplify) {
        return new PotionEffect(type, durations[extend] * 20, amps[amplify]);
    }
}
