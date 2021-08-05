package com.funnyboyroks.monstersplus.Jobs.Enchanter;

import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnchantList {
    BLAST_PROTECTION      (Enchantment.PROTECTION_EXPLOSIONS,    "BlastProtection",      new EnchantRecipe(2, 1, 2), new EnchantRecipe(3,  2, 9)),
    SMITE                 (Enchantment.DAMAGE_UNDEAD,            "Smite",                new EnchantRecipe(2, 1, 2), new EnchantRecipe(3, 2, 6)),
    FIRE_PROTECTION       (Enchantment.PROTECTION_FIRE,          "FireProtection",       new EnchantRecipe(2, 1, 3), new EnchantRecipe(3,  2, 9)),
    PROJECTILE_PROTECTION (Enchantment.PROTECTION_PROJECTILE,    "ProjectileProtection", new EnchantRecipe(2, 1, 3), new EnchantRecipe(2, 1, 5)),
    SHARPNESS             (Enchantment.DAMAGE_ALL,               "Sharpness",            new EnchantRecipe(2, 1, 4), new EnchantRecipe(3, 2, 12)),
    EFFICIENCY            (Enchantment.DIG_SPEED,                "Efficiency",           new EnchantRecipe(1, 0, 4), new EnchantRecipe(2, 1, 8)),
    PROTECTION            (Enchantment.PROTECTION_ENVIRONMENTAL, "Protection",           new EnchantRecipe(2, 1, 4), new EnchantRecipe(3, 2, 10)),
    UNBREAKING            (Enchantment.DURABILITY,               "Unbreaking",           new EnchantRecipe(1, 1, 5), new EnchantRecipe(2, 2, 10), new EnchantRecipe(3, 2, 15)),

    BANE_OF_ARTHROPODS    (Enchantment.DAMAGE_ARTHROPODS,        "BaneOfArthropods",     new EnchantRecipe(2, 1, 5)),
    FEATHER_FALLING       (Enchantment.PROTECTION_FALL,          "FeatherFalling",       new EnchantRecipe(1, 0, 6), new EnchantRecipe(2, 1, 10)),
    THORNS                (Enchantment.THORNS,                   "Thorns",               new EnchantRecipe(1, 0, 6), new EnchantRecipe(2, 1, 13)),
    POWER                 (Enchantment.ARROW_DAMAGE,             "Power",                new EnchantRecipe(2, 1, 6), new EnchantRecipe(3, 2, 7), new EnchantRecipe(3, 2, 14)),
    FORTUNE               (Enchantment.LOOT_BONUS_BLOCKS,        "Fortune",              new EnchantRecipe(1, 1, 7)),
    LOOTING               (Enchantment.LOOT_BONUS_MOBS,          "Looting",              new EnchantRecipe(1, 1, 8)),

    FIRE_ASPECT           (Enchantment.FIRE_ASPECT,              "FireAspect",           new EnchantRecipe(1, 1, 11)),
    ;


    private final Enchantment type;
    private final String humanName;
    private final EnchantRecipe[] recipes;

    EnchantList(Enchantment type, String humanName, EnchantRecipe... recipes) {
        this.type = type;
        this.humanName = humanName;
        this.recipes = recipes;
        for (EnchantRecipe recipe : recipes) recipe.setEnchantListItem(this);
    }

    EnchantList(Enchantment type, String humanName, int level, int xpCostIndex, int jobLevel) {
        this(type, humanName, new EnchantRecipe(level, xpCostIndex, jobLevel));
    }

    public List<Integer> getLevels() {
        return Arrays.stream(recipes).map(r -> r.level).collect(Collectors.toList());
    }

    public EnchantRecipe getRecipe(int level) {
        return Arrays.stream(recipes).filter(r -> r.level == level).findFirst().orElse(null);
    }

    public static EnchantRecipe get(String name, int level) {
        return valueOf(name).getRecipe(level);
    }

    public Enchantment getType() {
        return type;
    }

    public static class EnchantRecipe {
        private static final int[] LVL_COSTS = { 10, 17, 23, 30, 35 };

        public final int level;
        public final int xpCost;
        public final int jobLevel;

        private EnchantList enchantListItem;

        public EnchantRecipe(int level, int xpCostIndex, int jobLevel) {
            this.level = level;
            this.xpCost = LVL_COSTS[xpCostIndex];
            this.jobLevel = jobLevel;
        }

        public void setEnchantListItem(EnchantList enchantListItem) {
            this.enchantListItem = enchantListItem;
        }

        @Override
        public String toString() {
            return enchantListItem.humanName;
        }
    }
}
