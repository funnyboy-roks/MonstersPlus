package com.funnyboyroks.monstersplus.Data.structs;

import com.funnyboyroks.monstersplus.Utils.ItemUtils;
import com.funnyboyroks.monstersplus.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum TrophyType {
    ATILLA        (MonsterType.ATILLA,        "&cAtilla Killa",             Material.TNT,                  Enchantment.PROTECTION_EXPLOSIONS, 3),
    SLEEPY_HOLLOW (MonsterType.SLEEPY_HOLLOW, "&9Sleepy's Missing Head",    Material.ZOMBIE_HEAD,          Enchantment.KNOCKBACK, 2),
    HYDE          (MonsterType.HYDE,          "&bHyde's Hide",              Material.FIRE_CHARGE,          Enchantment.PROTECTION_EXPLOSIONS, 2),
    JUGGLER       (MonsterType.JUGGLER,       "&6Juggler's Sticky Ball",    Material.SLIME_BALL,           Enchantment.THORNS, 1),
    HOCUS         (MonsterType.HOCUS,         "&aHocus' Book of Spells",    Material.BOOK,                 Enchantment.THORNS, 2),
    POCUS         (MonsterType.POCUS,         "&4Energy Rod of Pocus",      Material.BLAZE_ROD,            Enchantment.DIG_SPEED, 2),
    ACHILLES      (MonsterType.ACHILLES,      "&fAchilles' Divine Arrow",   Material.ARROW,                Enchantment.DAMAGE_UNDEAD, 3),
    PIGLET        (MonsterType.PIGLET,        "&cPiglet Bacon",             Material.COOKED_PORKCHOP,      Enchantment.DURABILITY, 2),
    WILBUR        (MonsterType.WILBUR,        "&aWilbur's Son",             Material.PIG_SPAWN_EGG,        Enchantment.ARROW_DAMAGE, 1),
    LEGOLAS       (MonsterType.LEGOLAS,       "&2Legolas' Leg",             Material.BONE,                 Enchantment.DURABILITY, 2),
    HAWKEYE       (MonsterType.HAWKEYE,       "&3Hawkeye's Eye",            Material.ENDER_EYE,            Enchantment.ARROW_DAMAGE, 1),
    TICKLES       (MonsterType.TICKLES,       "&8Tickle's Tickler",         Material.FEATHER,              Enchantment.LOOT_BONUS_BLOCKS, 1),
    CAPTAIN_AHAB  (MonsterType.CAPTAIN_AHAB,  "&9Ahab's Ancient Rod",       Material.FISHING_ROD,          Enchantment.DAMAGE_ALL, 3),
    INFEARNO      (MonsterType.INFEARNO,      "&4Infearno Jelly",           Material.MAGMA_CREAM,          Enchantment.ARROW_FIRE, 1),
    HADAMARD      (MonsterType.HADAMARD,      "&6Hadamard Jelly",           Material.SLIME_BALL,           Enchantment.ARROW_INFINITE, 1),
    LESATH        (MonsterType.LESATH,        "&cLesath's Web",             Material.COBWEB,               Enchantment.PROTECTION_ENVIRONMENTAL, 3),
    SHELOB        (MonsterType.SHELOB,        "&eShelob's Child",           Material.EGG,                  Enchantment.DAMAGE_ARTHROPODS, 2),
    ARACHNE       (MonsterType.ARACHNE,       "&6Arachne's Rotten Eyeball", Material.FERMENTED_SPIDER_EYE, Enchantment.FIRE_ASPECT, 1),
    CHARLOTTE     (MonsterType.CHARLOTTE,     "&3Charlotte's Web",          Material.COBWEB,               Enchantment.ARROW_FIRE, 3),
    RAIKOU        (MonsterType.RAIKOU,        "&bRaikou's Collar",          Material.LEAD,                 Enchantment.PROTECTION_ENVIRONMENTAL, 2),
    ENTEI         (MonsterType.ENTEI,         "&4Entei's Collar",           Material.LEAD,                 Enchantment.PROTECTION_ENVIRONMENTAL, 2),
    SUICUNE       (MonsterType.SUICUNE,       "&9Suicune's Collar",         Material.LEAD,                 Enchantment.PROTECTION_ENVIRONMENTAL, 2),
    BARKIRA       (MonsterType.BARKIRA,       "&7Barkira's Bone",           Material.BONE,                 Enchantment.DAMAGE_UNDEAD, 1),
    ;

    public final MonsterType monster;
    public final Enchantment enchant;
    public final int enchantLvl;

    private final String displayName;
    private final Material mat;
    private final ItemStack item;

    TrophyType(MonsterType monster, String displayName, Material mat, Enchantment enchant, int enchantLvl) {
        this.monster = monster;
        this.displayName = displayName;
        this.mat = mat;
        this.enchant = enchant;
        this.enchantLvl = enchantLvl;

        item = new ItemStack(mat);
        ItemUtils.renameItemStack(item, this.displayName);
    }

    public ItemStack toItemStack() {
        return item.clone();
    }
}
