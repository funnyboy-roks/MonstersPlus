package com.funnyboyroks.monstersplus.Data.structs;

import com.funnyboyroks.monstersplus.Utils.ItemUtils;
import com.funnyboyroks.monstersplus.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum FishingItems {
    SPAWNER      (6, Material.SPAWNER,      0.3, 1, 3,   2  ),
    EMERALD      (6, Material.EMERALD,      0.3, 1, 3.0, 5  ),
    GOLDEN_APPLE (6, Material.GOLDEN_APPLE, 0.9, 1, 4.5, 5  ),
    DIAMOND      (6, Material.DIAMOND,      1.5, 1, 6.0, 7  ),
    GOLD_ORE     (6, Material.GOLD_ORE,     2.5, 1, 6.0, 16 ),

    MAGMA_CREAM       (5, Material.MAGMA_CREAM,       1.0, 1, 4.0, 8  ),
    BLAZE_ROD         (5, Material.BLAZE_ROD,         1.0, 1, 4.0, 8  ),
    ANVIL             (5, Material.ANVIL,             1.0, 1, 2.0, 3  ),
    EXPERIENCE_BOTTLE (5, Material.EXPERIENCE_BOTTLE, 2.0, 1, 5.0, 55 ),
    GOLD_NUGGET       (5, Material.GOLD_NUGGET,       2.5, 5, 6.0, 27 ),
    NETHER_STAR       (5, Material.NETHER_STAR,       0.5, 1, 3.0, 1  ),

    BLAZE_POWDER (4, Material.BLAZE_POWDER, 1.0, 1, 3.0, 8  ),
    ENDER_PEARL  (4, Material.ENDER_PEARL,  1.0, 1, 3.0, 8  ),
    SLIME_BALL   (4, Material.SLIME_BALL,   1.0, 1, 2.0, 8  ),
    IRON_ORE     (4, Material.IRON_ORE,     2.0, 1, 7.0, 15 ),
    OBSIDIAN     (4, Material.OBSIDIAN,     1.0, 1, 5.0, 7  ),

    REDSTONE             (3, Material.REDSTONE,             3.0, 5, 5.0, 38 ),
    LEATHER_CHESTPLATE   (3, Material.LEATHER_CHESTPLATE,   3.0, 1, 0.5, 3  ),
    LEATHER_LEGGINGS     (3, Material.LEATHER_LEGGINGS,     3.0, 1, 0.5, 3  ),
    GOLDEN_AXE           (3, Material.GOLDEN_AXE,           1.0, 1, 2.0, 3  ),
    FERMENTED_SPIDER_EYE (3, Material.FERMENTED_SPIDER_EYE, 1.0, 1, 3.5, 8  ),

    LAPIS_ORE      (2, Material.LAPIS_ORE,      2.5, 1, 5.0, 7  ),
    LEATHER_BOOTS  (2, Material.LEATHER_BOOTS,  3.0, 1, 0.5, 1  ),
    LEATHER_HELMET (2, Material.LEATHER_HELMET, 3.0, 1, 0.5, 1  ),
    PAPER          (2, Material.PAPER,          2.5, 1, 0.5, 12 ),
    SUGAR_CANE     (2, Material.SUGAR_CANE,     3.0, 2, 1.0, 12 ),
    FEATHER        (2, Material.FEATHER,        3.0, 2, 1.0, 12 ),

    LEATHER (1, Material.LEATHER,  4.0, 1, 4.0, 22 ),
    ARROW   (1, Material.ARROW,    3.0, 5, 1.0, 65 ),
    APPLE   (1, Material.APPLE,    2.0, 1, 5.0, 10 ),
    CARROT  (1, Material.CARROT,   4.0, 1, 2.0, 18 ),
    MELON   (1, Material.MELON,    2.0, 1, 1.0, 25 ),
    BEEF    (1, Material.BEEF,     4.0, 1, 2.0, 20 ),
    CHICKEN (1, Material.CHICKEN,  4.0, 1, 2.0, 20 ),
    ;

    private static final int MAX_LEVEL = 30;
    private static final int[] TIERS = {-1, 1, 3, 5, 8, 12, 15 };
    private static final EntityType[] SPAWNER_TYPES = {
        EntityType.CREEPER,
        EntityType.SKELETON,
        EntityType.SPIDER,
        EntityType.ZOMBIE,
        EntityType.ZOMBIFIED_PIGLIN,
        EntityType.ENDERMAN,
        EntityType.CAVE_SPIDER,
        EntityType.SILVERFISH,
        EntityType.BLAZE,
        EntityType.WITCH,
    };

    public final int tier;
    public final Material mat;
    public final short durability;
    public final double startRate;
    public final int startQty;
    public final double finalRate;
    public final int finalQty;

    FishingItems(int tier, Material mat, int durability, double startRate, int startQty, double finalRate, int finalQty) {

        this.tier = tier;
        this.mat = mat;
        this.durability = (short) durability;
        this.startRate = startRate;
        this.startQty = startQty;
        this.finalRate = finalRate;
        this.finalQty = finalQty;
    }

    FishingItems(int tier, Material mat, double startRate, int startQty, double finalRate, int finalQty) {
        this(tier, mat, 0, startRate, startQty, finalRate, finalQty);
    }

    public double getRateRange() {
        return finalRate - startRate;
    }

    public int getQtyRange() {
        return finalQty - startQty;
    }

    public static List<FishingItems> getTier(int level) {
        int tier = 0;
        for (int i = TIERS.length - 1; i >= 0; i--) {
            int v = TIERS[i];
            if (level > v) {
                tier = i;
                break;
            }
        }
        int finalTier = tier;
        return Arrays.stream(values()).filter(i -> i.tier == finalTier).collect(Collectors.toList());
    }

    private static EntityType randEntityType() {
        return Utils.choseRandom(SPAWNER_TYPES);
    }

    public static ItemStack getFishedItem(int lvl) {
        List<FishingItems> list = getTier(lvl);
        Collections.shuffle(list);

        double buffer = .3;

        for (FishingItems item : list) {
            double chance = ((item.getRateRange() / (double) MAX_LEVEL) * (double) lvl) + item.startRate;
            double maxQty = (item.getQtyRange() / MAX_LEVEL) * lvl + item.startQty + buffer;
            int qty = (int) (Math.random() * maxQty + .5);

            if(Utils.randomBool(chance)) {
                if (item.mat == Material.SPAWNER) {
                    ItemStack spawner =  ItemUtils.spawner(randEntityType());
                    spawner.setAmount(qty);
                    return spawner;
                }

                return new ItemStack(item.mat, qty);

            }
        }
        return null;
    }
}
