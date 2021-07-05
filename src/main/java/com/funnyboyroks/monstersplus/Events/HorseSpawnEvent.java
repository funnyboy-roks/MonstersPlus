package com.funnyboyroks.monstersplus.Events;

import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
import com.funnyboyroks.monstersplus.Utils.EntityUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffectType;

public class HorseSpawnEvent {

    public LivingEntity livEnt;
    public final MonsterType monsterType;


    public HorseSpawnEvent(LivingEntity livEnt, MonsterType type) {
        this.livEnt = livEnt;
        monsterType = type;
        createMonster();
    }

    public HorseSpawnEvent(LivingEntity livEnt) {
        this(
            livEnt,
            MonsterType.generateMonster(EntityType.SPIDER, livEnt.getLocation().getBlock().getBiome())
        );
    }

    public void createMonster() {
        if (monsterType == null) return;

        EntityUtils.setDropChances(livEnt, 0);

        AbstractHorse horse = (Horse) livEnt;
        Location loc = livEnt.getLocation();
        World world = loc.getWorld();

        livEnt.remove();


        // New Horses
        switch (monsterType) {
            case UNDEAD_HORSE:
            case PLAGUE_STALLION:
                livEnt = (LivingEntity) world.spawnEntity(loc, EntityType.ZOMBIE_HORSE);
                horse = (ZombieHorse) livEnt;
                break;
            case BRONCO_OF_DEATH:
            case SKELETON_HORSE:
                livEnt = (LivingEntity) world.spawnEntity(loc, EntityType.SKELETON_HORSE);
                horse = (SkeletonHorse) livEnt;
        }

        EntityUtils.setMaxHealth(horse, monsterType.health);
        EntityUtils.heal(horse);
        horse.setTamed(true);
        switch (monsterType) {
            case UNDEAD_HORSE: {
                horse.setCustomName("Undead Horse");
            }
            break;
            case SKELETON_HORSE: {
                horse.setCustomName("Skeleton Horse");
            }
            break;
            case BRONCO_OF_DEATH: {
                horse.setJumpStrength(2);
                EntityUtils.setSpeed(horse, 0.40);
                EntityUtils.addLongPotion(horse, PotionEffectType.FIRE_RESISTANCE, 3);
                EntityUtils.addLongPotion(horse, PotionEffectType.REGENERATION, 1);

                horse.setCustomName(ChatColor.WHITE + "Bronco" + ChatColor.GRAY + " of" + ChatColor.RED + " Death");
            }
            break;
            case PLAGUE_STALLION: {
                horse.setJumpStrength(2);
                EntityUtils.setSpeed(horse, 0.40);

                EntityUtils.addLongPotion(horse, PotionEffectType.FIRE_RESISTANCE, 3);
                EntityUtils.addLongPotion(horse, PotionEffectType.REGENERATION, 1);

                horse.setCustomName(ChatColor.DARK_GREEN + "Plague" + ChatColor.DARK_PURPLE + " Stallion");
            }
        }

        EntityUtils.setCustomMetadata(livEnt, "MonstersPlusName", monsterType.name());
    }

}
