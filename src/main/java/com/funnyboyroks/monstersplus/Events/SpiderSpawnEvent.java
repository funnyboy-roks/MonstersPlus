package com.funnyboyroks.monstersplus.Events;

import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
import com.funnyboyroks.monstersplus.Utils.EntityUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffectType;

public class SpiderSpawnEvent {

    public LivingEntity livEnt;
    public final MonsterType monsterType;


    public SpiderSpawnEvent(LivingEntity livEnt, MonsterType type) {
        this.livEnt = livEnt;
        monsterType = type;
        createMonster();
    }

    public SpiderSpawnEvent(LivingEntity livEnt) {
        this(
            livEnt,
            MonsterType.generateMonster(EntityType.SPIDER, livEnt.getLocation().getBlock().getBiome())
        );
    }

    public void createMonster() {
        if (monsterType == null) return;

        EntityUtils.setDropChances(livEnt, 0);



        switch (monsterType) {
            case BLACK_WIDOW: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 1);
            }
            break;
            case MOUNTAIN_PINCER: {
                EntityUtils.setSpeed(livEnt, 0.40);
            }
            break;
            case WITCH_APPRENTICE:
            case WITCH_OF_THE_EAST:
            case WARLOCK:
            case WITCH_OF_THE_WEST:
            case DARK_MAGE:
            case SWAMP_WITCH: {
                Location loc = livEnt.getLocation();
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WITCH);
            }
            break;
            case SCORPION:
            case ROCK_SCORPION:
            case FILTH_PURCH: {
                Location loc = livEnt.getLocation();
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
            }
            break;
            case STINGER: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.INCREASE_DAMAGE, 1);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 1);
            }
            break;
            case DESERT_SCORPION: {
                Location loc = livEnt.getLocation();
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 100);
                EntityUtils.burn(livEnt);
            }
            break;
            case TARANTULA: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.JUMP, 5);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.REGENERATION, 2);
            }
            break;
            case ICE_SPIDER:
            case BROWN_RECLUSE: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.INCREASE_DAMAGE, 1);
            }
            break;
            case WATER_STINGER: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.INCREASE_DAMAGE, 1);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.DAMAGE_RESISTANCE, 1);
            }
            break;
            case INFEARNO: {
                Location loc = livEnt.getLocation();
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.MAGMA_CUBE);
                ((MagmaCube) livEnt).setSize(7);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 50);
            }
            break;
            case HADAMARD: {
                Location loc = livEnt.getLocation();
                livEnt.remove();
                LivingEntity livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.SLIME);
                ((Slime) livEnt).setSize(5);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 50);
            }
            break;
            case LESATH: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 50);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 2);
            }
            break;
            case SHELOB: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 2);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 1);
            }
            break;
            case ARACHNE: {
                Location loc = livEnt.getLocation();
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 2);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 1);
            }
            break;
            case CHARLOTTE: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.INCREASE_DAMAGE, 2);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 3);

                EntityUtils.setMaxHealth(livEnt, monsterType.health);
                EntityUtils.heal(livEnt);

                livEnt.setCustomName(ChatColor.DARK_PURPLE + "Charlotte");
            }
            break;
            case RAIKOU: {
                Location loc = livEnt.getLocation();
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WOLF);
                ((Wolf) livEnt).setAngry(true);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 50);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 1);
            }
            break;
            case ENTEI: {
                Location loc = livEnt.getLocation();
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WOLF);
                ((Wolf) livEnt).setAngry(true);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 100);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 1);
                EntityUtils.burn(livEnt);
            }
            break;
            case SUICUNE:
            case BARKIRA: {
                Location loc = livEnt.getLocation();
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WOLF);
                ((Wolf) livEnt).setAngry(true);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.FIRE_RESISTANCE, 100);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 1);
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
