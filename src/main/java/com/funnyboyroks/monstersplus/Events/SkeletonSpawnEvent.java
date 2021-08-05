package com.funnyboyroks.monstersplus.Events;

import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
import com.funnyboyroks.monstersplus.Utils.EntityUtils;
import com.funnyboyroks.monstersplus.Utils.ItemUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class SkeletonSpawnEvent {

    public LivingEntity livEnt;
    public final MonsterType monsterType;


    public SkeletonSpawnEvent(LivingEntity livEnt, MonsterType type) {
        this.livEnt = livEnt;
        monsterType = type;
        createMonster();
    }

    public SkeletonSpawnEvent(LivingEntity livEnt) {
        this(
            livEnt,
            MonsterType.generateMonster(EntityType.SKELETON, livEnt.getLocation().getBlock().getBiome())
        );
    }

    public void createMonster() {
        if (monsterType == null) return;

        EntityUtils.setDropChances(livEnt, 0);

        switch (monsterType) {
            case SKELETON_KNIGHT: {
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, Material.WOODEN_SWORD);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.CHEST, Material.IRON_CHESTPLATE);
            }
            break;
            case SKELETON_FIGHTER: {
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, Material.STONE_AXE);
            }
            break;
            case SKELETON_PIRATE: {
                ItemStack hand = ItemUtils.item(Material.FLINT_AND_STEEL, ChatColor.DARK_RED + "Pirate Flint");

                ItemUtils.forceEnchant(hand, Enchantment.FIRE_ASPECT, 2);
                ItemUtils.forceEnchant(hand, Enchantment.DURABILITY, 2);
                // TODO: CustomEnchantCommand.enchantmentList.addCustomEnchantment(hand, "Wither Aspect", 1);

                EntityUtils.ArmorType.CHAINMAIL.apply(livEnt);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
            }
            break;
            case CRANIAL_BASHER: {
                ItemStack hand = ItemUtils.item(Material.SKELETON_SKULL, ChatColor.DARK_RED + "Scourge");
                ItemUtils.forceEnchant(hand, Enchantment.KNOCKBACK, 3);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, Material.IRON_HELMET);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.CHEST, Material.GOLDEN_CHESTPLATE);
            }
            break;
            case HOT_BONES: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 100);
                EntityUtils.burn(livEnt);

                ItemStack hand = ItemUtils.item(Material.BONE, ChatColor.DARK_RED + "Zwei Bone of Ra");
                ItemUtils.forceEnchant(hand, Enchantment.FIRE_ASPECT, 3);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.CHEST, Material.GOLDEN_CHESTPLATE);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);

            }
            break;
            case SKELETON_FISHERMAN: {
                ItemStack hand = ItemUtils.item(Material.FISHING_ROD, ChatColor.DARK_RED + "Rod of Prophecy");
                ItemUtils.forceEnchant(hand, Enchantment.FIRE_ASPECT, 1);
                //TODO: CustomCommands! : CustomEnchantCommand.enchantmentList.addCustomEnchantment(hand, "Fire Hook", 1);

                EntityUtils.ArmorType.CHAINMAIL.apply(livEnt);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
            }
            break;
            case DEAD_GARDENER: {
                ItemStack hand = new ItemStack(Material.IRON_HOE);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 2);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, Material.GRASS);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.LEGS, Material.LEATHER_LEGGINGS);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.FEET, Material.LEATHER_BOOTS);
            }
            break;
            case BONE_SOLDIER: {
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, Material.STONE_AXE);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, Material.LEATHER_HELMET);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.LEGS, Material.LEATHER_LEGGINGS);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.FEET, Material.AIR);
            }
            break;
            case SKELETON_CHAMPION: {
                EntityUtils.setEquipment(
                    livEnt,
                    Material.IRON_SWORD,
                    null,
                    null,
                    Material.GOLDEN_CHESTPLATE,
                    Material.GOLDEN_LEGGINGS,
                    null
                );
            }
            break;
            case DAZZLER: {
                ItemStack bow = ItemUtils.item(Material.BOW, ChatColor.DARK_GRAY + "Kridershot");
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_DAMAGE, 3);
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_INFINITE, 1);
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_FIRE, 1);
                //TODO: CustomCommands! : CustomEnchantCommand.enchantmentList.addCustomEnchantment(bow, "Drunk Shot", 1);

                EntityUtils.setEquipment(livEnt,
                    bow,
                    null,
                    new ItemStack(Material.IRON_HELMET),
                    new ItemStack(Material.AIR),
                    new ItemStack(Material.AIR),
                    new ItemStack(Material.IRON_BOOTS)
                );

                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
            }
            break;
            case FAMISHED_WALKER: {
                ItemStack hand = new ItemStack(Material.ROTTEN_FLESH);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 3);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
            }
            break;
            case CINDERTON: {
                ItemStack hand = new ItemStack(Material.BLAZE_ROD);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 3);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.ArmorType.LEATHER.apply(livEnt, 2, Enchantment.THORNS);
            }
            break;
            case SUMO_SKELETON: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 1);
                ItemStack hand = new ItemStack(Material.AIR);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                ItemStack helm = ItemUtils.item(Material.LEATHER_HELMET, ChatColor.AQUA + "Sky Cap");
                ItemUtils.forceEnchant(helm, Enchantment.DURABILITY, 4);
                ItemUtils.forceEnchant(helm, Enchantment.PROTECTION_FALL, 5);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, helm);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
            }
            break;
            case HOLY_SKELETON: {
                ItemStack bow = ItemUtils.item(Material.BOW, ChatColor.AQUA + "Holy Bow");
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_FIRE, 2);
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_DAMAGE, 1);
                ItemUtils.forceEnchant(bow, Enchantment.DURABILITY, 2);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, bow);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
            }
            break;
            case FLAYED: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.REGENERATION, 1);

                // Fire bow
                ItemStack bow = ItemUtils.item(Material.BOW, ChatColor.DARK_GRAY + "Flay them Living!");
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_FIRE, 3);
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_DAMAGE, 2);
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_INFINITE, 1);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, bow);
                // Wither Skull
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, Material.WITHER_SKELETON_SKULL);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
            }
            break;
            case ICED_SHARPSHOOTER: {
                ItemStack bow = new ItemStack(Material.BOW);
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_DAMAGE, 2);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, bow);
            }
            break;
            case ARSONIC_ARCHER: {
                ItemStack bow = new ItemStack(Material.BOW);
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_DAMAGE, 5);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.LEGS, Material.CHAINMAIL_LEGGINGS);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.FEET, Material.CHAINMAIL_BOOTS);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, bow);
            }
            break;
            case SKELETON_SNIPER: {
                ItemStack bow = new ItemStack(Material.BOW);
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_DAMAGE, 6);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, bow);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, Material.CHAINMAIL_HELMET);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.LEGS, Material.CHAINMAIL_LEGGINGS);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.CHEST, Material.LEATHER_CHESTPLATE);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.FEET, Material.LEATHER_BOOTS);
            }
            break;
            case DEATH_KNIGHT: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 2);

                // Fire bow
                ItemStack hand = ItemUtils.item(Material.BOW, ChatColor.DARK_GRAY + "Raining " + ChatColor.RED + "Death");
                ItemUtils.forceEnchant(hand, Enchantment.ARROW_FIRE, 2);
                ItemUtils.forceEnchant(hand, Enchantment.ARROW_DAMAGE, 4);

                EntityUtils.setEquipment(
                    livEnt,
                    null,
                    Material.LEATHER_LEGGINGS,
                    Material.LEATHER_CHESTPLATE,
                    Material.LEATHER_BOOTS
                );
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
            }
            break;
            case MINER_OF_DEATH: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 1);

                ItemStack hand = ItemUtils.item(Material.DIAMOND_PICKAXE, ChatColor.DARK_AQUA + "War Pick");
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 2);
                ItemUtils.forceEnchant(hand, Enchantment.FIRE_ASPECT, 1);
                ItemUtils.forceEnchant(hand, Enchantment.DURABILITY, 2);
                ItemUtils.forceEnchant(hand, Enchantment.DIG_SPEED, 2);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_UNDEAD, 2);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ARTHROPODS, 2);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
                EntityUtils.ArmorType.LEATHER.apply(livEnt);
            }
            break;
            case SPINECHILLER: {
                ItemStack hand = ItemUtils.item(Material.IRON_AXE, ChatColor.DARK_AQUA + "The Chiller");
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 3);
                ItemUtils.forceEnchant(hand, Enchantment.KNOCKBACK, 1);
                //TODO: CustomCommands! : CustomEnchantCommand.enchantmentList.addCustomEnchantment(hand, "Ice Aspect", 1);

                EntityUtils.clearEquipment(livEnt);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.CHEST, Material.DIAMOND_CHESTPLATE);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
            }
            break;
            case PYRE: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 100);
                EntityUtils.burn(livEnt);

                ItemStack hand = ItemUtils.item(Material.FURNACE, ChatColor.RED + "The Pyre");
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 3);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ARTHROPODS, 3);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
                EntityUtils.ArmorType.GOLD.apply(livEnt);
            }
            break;
            case BAD_BLACKSMITH: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.INCREASE_DAMAGE, 2);

                ItemStack hand = ItemUtils.item(Material.ANVIL, ChatColor.DARK_AQUA + "Worthy Anvil");
                ItemUtils.forceEnchant(hand, Enchantment.KNOCKBACK, 3);
                ItemUtils.forceEnchant(hand, Enchantment.LOOT_BONUS_MOBS, 2);


                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
                EntityUtils.setEquipment(
                    livEnt,
                    null,
                    Material.IRON_CHESTPLATE,
                    Material.IRON_LEGGINGS,
                    Material.GOLDEN_BOOTS
                );
            }
            break;
            case CRAZED_SKELETON: {
                ItemStack hand = ItemUtils.item(Material.CACTUS, ChatColor.DARK_AQUA + "Crazy Cactus");
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ARTHROPODS, 3);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_UNDEAD, 3);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
                EntityUtils.setEquipment(
                    livEnt,
                    null,
                    Material.DIAMOND_CHESTPLATE,
                    Material.DIAMOND_LEGGINGS,
                    null
                );
            }
            break;
            case EXECUTIONER: {
                EntityUtils.setSpeed(livEnt, 0.28);
                ItemStack hand = ItemUtils.item(Material.DIAMOND_AXE, ChatColor.DARK_AQUA + "The Beheader");
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 3);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ARTHROPODS, 2);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_UNDEAD, 2);
                ItemUtils.forceEnchant(hand, Enchantment.DURABILITY, 2);
                ItemUtils.forceEnchant(hand, Enchantment.KNOCKBACK, 1);

                EntityUtils.ArmorType.IRON.apply(livEnt);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, Material.PLAYER_HEAD);
            }
            break;
            case ARCHFIEND: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 1);
                ItemStack hand = ItemUtils.item(Material.BOW, ChatColor.DARK_AQUA + "Fiend Bow");
                ItemUtils.forceEnchant(hand, Enchantment.ARROW_DAMAGE, 3);
                ItemUtils.forceEnchant(hand, Enchantment.ARROW_KNOCKBACK, 1);
                ItemUtils.forceEnchant(hand, Enchantment.ARROW_INFINITE, 1);
                ItemUtils.forceEnchant(hand, Enchantment.KNOCKBACK, 1);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
                EntityUtils.setEquipment(
                    livEnt,
                    null,
                    Material.DIAMOND_LEGGINGS,
                    Material.DIAMOND_CHESTPLATE,
                    Material.GOLDEN_BOOTS
                );
            }
            break;
            case LEGOLAS: {
                ItemStack bow = new ItemStack(Material.BOW);
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_KNOCKBACK, 13);
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_DAMAGE, 80);

                ItemStack helm = new ItemStack(Material.AIR);
                ItemStack leg = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
                ItemStack boot = new ItemStack(Material.CHAINMAIL_BOOTS);

                ItemUtils.forceEnchant(leg, Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                ItemUtils.forceEnchant(chest, Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                ItemUtils.forceEnchant(boot, Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                ItemUtils.forceEnchant(leg, Enchantment.PROTECTION_FIRE, 90);

                EntityUtils.setEquipment(
                    livEnt,
                    bow,
                    null,
                    helm,
                    chest,
                    leg,
                    boot
                );
            }
            break;
            case HAWKEYE: {
                ItemStack bow = new ItemStack(Material.BOW);
                ItemUtils.forceEnchant(bow, Enchantment.ARROW_DAMAGE, 80);

                ItemStack leg = new ItemStack(Material.DIAMOND_LEGGINGS);

                ItemUtils.forceEnchant(leg, Enchantment.PROTECTION_FALL, 90);
                ItemUtils.forceEnchant(leg, Enchantment.PROTECTION_FIRE, 90);
                ItemUtils.forceEnchant(leg, Enchantment.PROTECTION_ENVIRONMENTAL, 6);

                EntityUtils.setEquipment(
                    livEnt,
                    bow,
                    null,
                    new ItemStack(Material.WITHER_SKELETON_SKULL),
                    new ItemStack(Material.DIAMOND_CHESTPLATE),
                    leg,
                    new ItemStack(Material.DIAMOND_BOOTS)
                );
            }
            break;
            case TICKLES: {
                Location loc = livEnt.getLocation();
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 2);

                ItemStack bow = new ItemStack(Material.FEATHER);
                ItemUtils.forceEnchant(bow, Enchantment.DAMAGE_ALL, 18);

                ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
                ItemUtils.forceEnchant(helm, Enchantment.PROTECTION_ENVIRONMENTAL, 10);
                ItemUtils.forceEnchant(helm, Enchantment.PROTECTION_FIRE, 90);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, bow);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, helm);
            }
            break;
            case CAPTAIN_AHAB: {
                ItemStack hand = new ItemStack(Material.FISHING_ROD);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 12);

                ItemStack leg = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                ItemUtils.forceEnchant(leg, Enchantment.PROTECTION_FALL, 90);
                ItemUtils.forceEnchant(leg, Enchantment.PROTECTION_ENVIRONMENTAL, 6);

                EntityUtils.ArmorType.CHAINMAIL.apply(livEnt);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.LEGS, leg);
            }
            break;
        }

        EntityUtils.setCustomMetadata(livEnt, "MonstersPlusName", monsterType.name());

        if (monsterType.health > 0) {
            livEnt.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(monsterType.health);
            EntityUtils.heal(livEnt);
            livEnt.setCustomName(monsterType.getColouredMobName());
        }
    }

}
