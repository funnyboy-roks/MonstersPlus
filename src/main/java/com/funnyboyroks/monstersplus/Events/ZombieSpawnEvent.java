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
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class ZombieSpawnEvent {

    public LivingEntity livEnt;
    public final MonsterType monsterType;


    public ZombieSpawnEvent(LivingEntity livEnt, MonsterType type) {
        this.livEnt = livEnt;
        monsterType = type;
        createMonster();
    }

    public ZombieSpawnEvent(LivingEntity livEnt) {
        this(
            livEnt,
            MonsterType.generateMonster(EntityType.ZOMBIE, livEnt.getLocation().getBlock().getBiome())
        );
    }

    public void createMonster() {
        if (monsterType == null) return;

        EntityUtils.setDropChances(livEnt, 0);

        switch (monsterType) {
            case ZOMBIE_CHARGER: {
                EntityUtils.setSpeed(livEnt, .4);

            }
            break;
            case LUMBER_ZOMBIE: {
                EntityUtils.setEquipment(
                    livEnt,
                    Material.GOLDEN_AXE,
                    null,
                    null,
                    Material.LEATHER_LEGGINGS,
                    Material.CHAINMAIL_CHESTPLATE,
                    Material.LEATHER_BOOTS
                );

            }
            break;
            case PETTY_THIEF: {
                EntityUtils.setEquipment(
                    livEnt,
                    EquipmentSlot.HAND,
                    Material.IRON_INGOT
                );
                EntityUtils.ArmorType.IRON.apply(livEnt);

            }
            break;
            case BURNING_WALKER: {
                ItemStack boots = ItemUtils.item(Material.DIAMOND_BOOTS, ChatColor.RED + "Fire Treads");
                ItemUtils.forceEnchant(boots, Enchantment.PROTECTION_FIRE, 6);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, Material.FIRE_CHARGE);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, Material.WITHER_SKELETON_SKULL);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.FEET, boots);

                EntityUtils.setDropChance(livEnt, EquipmentSlot.FEET, .07f);

                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 100);
                livEnt.setFireTicks(EntityUtils.POTION_DURATION);

            }
            break;
            case ZOMBIE_REAPER: {
                ItemStack stack = new ItemStack(Material.IRON_HOE);
                ItemUtils.forceEnchant(stack, Enchantment.KNOCKBACK, 2);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, stack);

            }
            break;
            case ZOMBIE_FISHERMAN: {
                ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
                ItemUtils.forceEnchant(fishingRod, Enchantment.FIRE_ASPECT, 1);
                ItemUtils.forceEnchant(fishingRod, Enchantment.DAMAGE_ALL, 2);
                // TODO: Add custom Enchant "Sharp Hook" | OG Line: CustomEnchantCommand.enchantmentList.addCustomEnchantment(inhand, "Sharp Hook", 2);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, fishingRod);
                EntityUtils.ArmorType.CHAINMAIL.apply(livEnt);

            }
            break;
            case ROTTEN_FIGHTER: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.INCREASE_DAMAGE, 1);

                ItemStack pants = ItemUtils.item(Material.DIAMOND_LEGGINGS, ChatColor.DARK_GREEN + "Tough Brawler Pants");
                ItemUtils.forceEnchant(pants, Enchantment.DURABILITY, 3);
                ItemUtils.forceEnchant(pants, Enchantment.OXYGEN, 2);
                ItemUtils.forceEnchant(pants, Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                EntityUtils.clearEquipment(livEnt);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, Material.DIAMOND_HELMET);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.LEGS, pants);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.LEGS, .07f);


            }
            break;
            case LAME_BRAIN: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.DAMAGE_RESISTANCE, 1);

                ItemStack helm = new ItemStack(Material.GOLDEN_HELMET);
                ItemStack leg = new ItemStack(Material.GOLDEN_LEGGINGS);
                ItemStack main = ItemUtils.item(Material.SLIME_BALL, ChatColor.GREEN + "Brainzzzz");

                ItemUtils.forceEnchant(helm, Enchantment.THORNS, 2);
                ItemUtils.forceEnchant(leg, Enchantment.THORNS, 2);
                ItemUtils.forceEnchant(main, Enchantment.DAMAGE_ARTHROPODS, 5);
                ItemUtils.forceEnchant(main, Enchantment.DAMAGE_ALL, 1);

                EntityUtils.setEquipment(
                    livEnt,
                    main,
                    null,
                    helm,
                    null,
                    leg,
                    null
                );

            }
            break;
            case ZOMBIE_CLIMBER: {
                ItemStack hand = ItemUtils.item(Material.LEAD, ChatColor.GOLD + "Mountaineer's Rope");

                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 3);
                ItemUtils.forceEnchant(hand, Enchantment.LOOT_BONUS_MOBS, 3);


                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.LEGS, Material.LEATHER_LEGGINGS);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.FEET, Material.LEATHER_BOOTS);

                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, .07f);


            }
            break;
            case UNDEAD_MONK: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.INCREASE_DAMAGE, 1);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, Material.PLAYER_HEAD);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HEAD, 0.25f);


            }
            break;
            case LUNATIC: {
                Location loc = livEnt.getLocation();
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE_VILLAGER);

                ItemStack hand = ItemUtils.item(Material.CACTUS, ChatColor.GREEN + "Cactuar Head");
                ItemUtils.forceEnchant(hand, Enchantment.KNOCKBACK, 2);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_UNDEAD, 4);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 2);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
            }
            break;
            case THE_RESTLESS: {
                ItemStack hand = ItemUtils.item(Material.LEAD, ChatColor.DARK_BLUE + "The Dream");
                // TODO: Custom Enchant "Wither Aspect": OG Line: CustomEnchantCommand.enchantmentList.addCustomEnchantment(bed, "Wither Aspect", 1);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);

            }
            break;
            case ROTTING_JACK: {
                EntityUtils.setSpeed(livEnt, .4);
                ItemStack helm = ItemUtils.item(Material.JACK_O_LANTERN, ChatColor.GOLD + "Jack Mask");
                ItemUtils.forceEnchant(helm, Enchantment.FIRE_ASPECT, 2);
                ItemUtils.forceEnchant(helm, Enchantment.KNOCKBACK, 1);
                ItemUtils.forceEnchant(helm, Enchantment.LOOT_BONUS_MOBS, 1);

                ItemStack hand = ItemUtils.item(Material.GOLDEN_AXE, ChatColor.GOLD + "Jack Hack");
                ItemUtils.forceEnchant(hand, Enchantment.DIG_SPEED, 3);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ARTHROPODS, 2);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, helm);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HEAD, .07f);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, .07f);

            }
            break;
            case STINK_SWARM: {
                // TODO: Disabled?
            }
            break;
            case CACTUAR: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.INVISIBILITY, 1);

                ItemStack helm = new ItemStack(Material.CACTUS);
                ItemUtils.forceEnchant(helm, Enchantment.THORNS, 2);

                ItemStack boots = ItemUtils.item(Material.DIAMOND_BOOTS, ChatColor.GREEN + "Cactuar Greaves");
                ItemUtils.forceEnchant(boots, Enchantment.THORNS, 4);
                ItemUtils.forceEnchant(boots, Enchantment.DURABILITY, 3);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, helm);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.FEET, boots);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.FEET, .05f);
            }
            break;
            case CACTII: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.INVISIBILITY, 1);

                ItemStack helm = new ItemStack(Material.CACTUS);
                ItemUtils.forceEnchant(helm, Enchantment.THORNS, 3);

                ItemStack pants = ItemUtils.item(Material.DIAMOND_LEGGINGS, ChatColor.GREEN + "Cactii Jammers");
                ItemUtils.forceEnchant(pants, Enchantment.THORNS, 4);
                ItemUtils.forceEnchant(pants, Enchantment.DURABILITY, 3);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, helm);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.LEGS, pants);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.LEGS, .05f);
            }
            break;
            case HARDENED_THIEF: {
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, Material.GOLD_INGOT);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 1);
                EntityUtils.ArmorType.GOLD.apply(livEnt);
            }
            break;
            case FAMISHED_LURKER: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SLOW, 1);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.INCREASE_DAMAGE, 2);

                ItemStack chest = ItemUtils.item(Material.CHAINMAIL_CHESTPLATE, ChatColor.DARK_GREEN + "Famine Mail");
                ItemUtils.forceEnchant(chest, Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                ItemUtils.forceEnchant(chest, Enchantment.DURABILITY, 3);
                ItemUtils.forceEnchant(chest, Enchantment.PROTECTION_PROJECTILE, 2);
                // TODO: CUSTOM ENCHANT. OG: CustomEnchantCommand.enchantmentList.addCustomEnchantment(item, "Poverty Barrier", 1);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, Material.STICK);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.CHEST, chest);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.LEGS, Material.CHAINMAIL_LEGGINGS);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.CHEST, 0.07f);


            }
            break;
            case COLD_CORPSE: {
                ItemStack head = ItemUtils.item(Material.ICE, ChatColor.DARK_BLUE + "Vigilance Cube");
                ItemUtils.forceEnchant(head, Enchantment.LOOT_BONUS_MOBS, 3);
                ItemUtils.forceEnchant(head, Enchantment.DAMAGE_UNDEAD, 3);
                // TODO Custom Enchant - OG: CustomEnchantCommand.enchantmentList.addCustomEnchantment(item, "Ice Aspect", 1);

                EntityUtils.ArmorType.LEATHER.apply(livEnt);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, Material.IRON_SWORD);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, head);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HEAD, 0.07f);
            }
            break;
            case FROSTED_BITER: {
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, Material.DIAMOND_SWORD);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
            }
            break;
            case ARCTIC_ZED: {
                EntityUtils.setSpeed(livEnt, 0.29);
                ItemStack hand = ItemUtils.item(Material.DIAMOND_HOE, ChatColor.DARK_AQUA + "Zed Hoe");
                ItemUtils.forceEnchant(hand, Enchantment.DURABILITY, 4);
                // TODO Custom Enchant - OG: CustomEnchantCommand.enchantmentList.addCustomEnchantment(inhand, "Hydration", 1);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
                EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0.07f);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.CHEST, Material.DIAMOND_CHESTPLATE);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.FEET, Material.DIAMOND_BOOTS);
            }
            break;
            case UNDYING: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.INCREASE_DAMAGE, 1);

                ItemStack hand = ItemUtils.item(Material.DIAMOND_HOE, ChatColor.RED + "Killsickle");
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 3);
                ItemUtils.forceEnchant(hand, Enchantment.FIRE_ASPECT, 1);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_UNDEAD, 2);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ARTHROPODS, 1);

                EntityUtils.ArmorType.LEATHER.apply(livEnt);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HEAD, Material.AIR);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
            }
            break;
            case ROYAL_THIEF: {
                EntityUtils.ArmorType.DIAMOND.apply(livEnt);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, Material.DIAMOND);
            }
            break;
            case BURNT_GHOUL: {
                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, Material.IRON_SWORD);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.LEGS, Material.IRON_LEGGINGS);
                EntityUtils.setEquipment(livEnt, EquipmentSlot.CHEST, Material.IRON_CHESTPLATE);
            }
            break;
            case QACTUAR: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.INVISIBILITY, 1);

                ItemStack chest = ItemUtils.item(Material.DIAMOND_CHESTPLATE, ChatColor.GREEN + "Qactuar Jacket");
                ItemUtils.forceEnchant(chest, Enchantment.THORNS, 4);
                ItemUtils.forceEnchant(chest, Enchantment.DURABILITY, 3);

                ItemStack helm = new ItemStack(Material.CACTUS);
                ItemUtils.forceEnchant(helm, Enchantment.THORNS, 4);

                EntityUtils.setEquipment(
                    livEnt,
                    null,
                    null,
                    helm,
                    chest,
                    null,
                    null
                );
                EntityUtils.setDropChance(livEnt, EquipmentSlot.CHEST, .05f);
            }
            break;
            case ATILLA: {

                EntityUtils.setAttack(livEnt, 20);
                ItemStack helm = new ItemStack(Material.GOLDEN_HELMET);
                ItemStack leg = new ItemStack(Material.GOLDEN_LEGGINGS);
                ItemStack chest = new ItemStack(Material.GOLDEN_CHESTPLATE);
                ItemStack boot = new ItemStack(Material.GOLDEN_BOOTS);
                ItemStack hand = new ItemStack(Material.TNT);

                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 6);
                ItemUtils.forceEnchant(helm, Enchantment.PROTECTION_EXPLOSIONS, 30);
                ItemUtils.forceEnchant(leg, Enchantment.PROTECTION_EXPLOSIONS, 30);
                ItemUtils.forceEnchant(chest, Enchantment.PROTECTION_EXPLOSIONS, 30);
                ItemUtils.forceEnchant(chest, Enchantment.PROTECTION_ENVIRONMENTAL, 8);
                ItemUtils.forceEnchant(boot, Enchantment.PROTECTION_FIRE, 90);

                EntityUtils.setEquipment(
                    livEnt,
                    hand,
                    null,
                    helm,
                    chest,
                    leg,
                    boot
                );
            }
            break;
            case SLEEPY_HOLLOW: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.INVISIBILITY, 1);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 2);

                ItemStack leg = new ItemStack(Material.IRON_LEGGINGS);
                ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
                ItemStack boot = new ItemStack(Material.IRON_BOOTS);
                ItemStack hand = new ItemStack(Material.DIAMOND_SWORD);

                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 26);
                ItemUtils.forceEnchant(hand, Enchantment.KNOCKBACK, 10);
                ItemUtils.forceEnchant(leg, Enchantment.PROTECTION_FIRE, 90);
                ItemUtils.forceEnchant(chest, Enchantment.PROTECTION_FALL, 90);
                ItemUtils.forceEnchant(boot, Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                ItemUtils.forceEnchant(chest, Enchantment.THORNS, 6);

                EntityUtils.setEquipment(
                    livEnt,
                    hand,
                    null,
                    null,
                    chest,
                    leg,
                    boot
                );
            }
            break;
            case HYDE: {

                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 1);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 50);

                ItemStack helm = new ItemStack(Material.DRAGON_HEAD);
                ItemStack leg = new ItemStack(Material.DIAMOND_LEGGINGS);
                ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
                ItemStack boot = new ItemStack(Material.IRON_BOOTS);
                ItemStack hand = new ItemStack(Material.DIAMOND_SWORD);

                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 30);
                ItemUtils.forceEnchant(leg, Enchantment.PROTECTION_FIRE, 90);
                ItemUtils.forceEnchant(chest, Enchantment.PROTECTION_FALL, 90);
                ItemUtils.forceEnchant(chest, Enchantment.PROTECTION_EXPLOSIONS, 90);
                ItemUtils.forceEnchant(boot, Enchantment.PROTECTION_ENVIRONMENTAL, 4);

                EntityUtils.setEquipment(
                    livEnt,
                    hand,
                    null,
                    helm,
                    chest,
                    leg,
                    boot
                );
            }
            break;
            case JUGGLER: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SLOW, 1);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 50);

                ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                ItemStack leg = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                ItemStack boot = new ItemStack(Material.CHAINMAIL_BOOTS);

                ItemUtils.forceEnchant(chest, Enchantment.PROTECTION_FALL, 90);
                ItemUtils.forceEnchant(chest, Enchantment.PROTECTION_EXPLOSIONS, 90);
                ItemUtils.forceEnchant(leg, Enchantment.PROTECTION_FIRE, 90);
                ItemUtils.forceEnchant(boot, Enchantment.PROTECTION_ENVIRONMENTAL, 4);

                EntityUtils.setEquipment(
                    livEnt,
                    new ItemStack(Material.CLAY_BALL),
                    null,
                    new ItemStack(Material.LEATHER_HELMET),
                    chest,
                    leg,
                    boot
                );
            }
            break;
            case HOCUS: {

                Location loc = livEnt.getLocation();
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WITCH);

                livEnt.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(MonsterType.HOCUS.health);
                EntityUtils.heal(livEnt);

                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 50);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 3);

                ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                ItemStack leg = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                ItemStack boot = new ItemStack(Material.CHAINMAIL_BOOTS);

                ItemUtils.forceEnchant(chest, Enchantment.PROTECTION_FALL, 90);
                ItemUtils.forceEnchant(chest, Enchantment.PROTECTION_EXPLOSIONS, 90);
                ItemUtils.forceEnchant(leg, Enchantment.PROTECTION_FIRE, 90);
                ItemUtils.forceEnchant(boot, Enchantment.PROTECTION_ENVIRONMENTAL, 4);

                EntityUtils.setEquipment(
                    livEnt,
                    null,
                    null,
                    new ItemStack(Material.LEATHER_HELMET),
                    chest,
                    leg,
                    boot
                );
                livEnt.setCustomName(ChatColor.DARK_PURPLE + "Hocus");

                LivingEntity pocus = MonsterType.POCUS.spawn(loc, CreatureSpawnEvent.SpawnReason.CUSTOM);
                EntityUtils.addLongPotion(pocus, PotionEffectType.FIRE_RESISTANCE, 50);
                EntityUtils.addLongPotion(pocus, PotionEffectType.SPEED, 1);

                pocus.setCustomName(ChatColor.DARK_PURPLE + "Pocus");

                LivingEntity bat = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.BAT);

                bat.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
                EntityUtils.heal(bat);
                EntityUtils.addLongPotion(bat, PotionEffectType.FIRE_RESISTANCE, 50);
                EntityUtils.addLongPotion(bat, PotionEffectType.SLOW, 2);

                livEnt.addPassenger(pocus);
                bat.addPassenger(livEnt);
            }
            break;
            case ACHILLES: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FAST_DIGGING, 5);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 2);

                ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
                ItemStack boot = new ItemStack(Material.DIAMOND_BOOTS);
                ItemStack hand = new ItemStack(Material.DIAMOND_SWORD);

                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 26);
                ItemUtils.forceEnchant(chest, Enchantment.PROTECTION_FALL, 90);
                ItemUtils.forceEnchant(boot, Enchantment.PROTECTION_ENVIRONMENTAL, 7);

                EntityUtils.setEquipment(
                    livEnt,
                    hand,
                    null,
                    null,
                    chest,
                    new ItemStack(Material.DIAMOND_LEGGINGS),
                    boot
                );
            }
            break;
            case PIGLET: {
                Location loc = livEnt.getLocation();
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIFIED_PIGLIN);

                EntityUtils.addLongPotion(livEnt, PotionEffectType.FAST_DIGGING, 5);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 2);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.DAMAGE_RESISTANCE, 1);

                ItemStack hand = new ItemStack(Material.DIAMOND_SWORD);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 42);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);
            }
            break;
            case WILBUR: {
                Location loc = livEnt.getLocation();
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIFIED_PIGLIN);

                EntityUtils.addLongPotion(livEnt, PotionEffectType.FAST_DIGGING, 8);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 2);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.DAMAGE_RESISTANCE, 1);

                ItemStack hand = new ItemStack(Material.DIAMOND_SWORD);
                ItemUtils.forceEnchant(hand, Enchantment.DAMAGE_ALL, 42);

                EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, hand);

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
