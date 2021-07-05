package com.funnyboyroks.monstersplus.Events;

import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
import com.funnyboyroks.monstersplus.Utils.EntityUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

public class CreeperSpawnEvent {

    public LivingEntity livEnt;
    public final MonsterType monsterType;


    public CreeperSpawnEvent(LivingEntity livEnt, MonsterType type) {
        this.livEnt = livEnt;
        monsterType = type;
        createMonster();
    }

    public CreeperSpawnEvent(LivingEntity livEnt) {
        this(
            livEnt,
            MonsterType.generateMonster(EntityType.SPIDER, livEnt.getLocation().getBlock().getBiome())
        );
    }

    public void createMonster() {
        if (monsterType == null) return;

        EntityUtils.setDropChances(livEnt, 0);

        switch (monsterType) {
            case SQUIRT: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 1);
            }
            break;
            case HEAVY_CREEPER: {
                EntityUtils.addLongPotion(livEnt, PotionEffectType.DAMAGE_RESISTANCE, 1);
                EntityUtils.addLongPotion(livEnt, PotionEffectType.SLOW, 1);
            }
            break;
            case HEAT_SEEKER: {
                EntityUtils.setSpeed(livEnt, 0.45);
            }
            break;
            case CREEPILLAR: {
                Location loc = livEnt.getLocation();

                LivingEntity body1 = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.CREEPER);
                LivingEntity body2 = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.CREEPER);

                livEnt.setCustomName(ChatColor.GOLD + "Creepillar");
                body1.setCustomName(ChatColor.GOLD + "Creepillar");
                body2.setCustomName(ChatColor.GOLD + "Creepillar");

                body1.addPassenger(body2);
                livEnt.addPassenger(body1);

                double health = monsterType.health;
                livEnt.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
                body1.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health - 1);
                body2.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health - 1);

                EntityUtils.heal(livEnt);
                EntityUtils.heal(body1);
                EntityUtils.heal(body2);
            }
            break;
            case LITTLE_BOY: {
                ((Creeper) livEnt).setPowered(true);
            }
        }

        EntityUtils.setCustomMetadata(livEnt, "MonstersPlusName", monsterType.name());

        if (monsterType.health > 0) {
            livEnt.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(monsterType.health);
            EntityUtils.heal(livEnt);
            livEnt.setCustomName(monsterType.getColouredMobName());
        }
    }

}
