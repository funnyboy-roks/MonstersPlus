package com.funnyboyroks.monstersplus.Utils;

import com.funnyboyroks.monstersplus.MonstersPlus;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class EntityUtils {
    public static final int POTION_DURATION = 7000000;
    private static final boolean TP_IN_SKYWORLD = false;

    public static void addLongPotion(LivingEntity e, PotionEffectType type, int level) {
        e.addPotionEffect(new PotionEffect(type, POTION_DURATION, level));
    }

    public static void setEquipment(LivingEntity ent, Material mainHand, Material offHand, Material helm, Material chest, Material leggings, Material boots) {
        EntityEquipment e = ent.getEquipment();
        e.setItemInMainHand (mainHand == null ? air() : new ItemStack(mainHand));
        e.setItemInOffHand  (offHand  == null ? air() : new ItemStack(offHand));
        e.setHelmet         (helm     == null ? air() : new ItemStack(helm));
        e.setChestplate     (chest    == null ? air() : new ItemStack(chest));
        e.setLeggings       (leggings == null ? air() : new ItemStack(leggings));
        e.setBoots          (boots    == null ? air() : new ItemStack(boots));
    }

    public static void setEquipmentDropChances(LivingEntity ent, float mainHand, float offHand, float helm, float chest, float leggings, float boots) {
        EntityEquipment e = ent.getEquipment();
        e.setItemInMainHandDropChance(mainHand);
        e.setItemInOffHandDropChance(offHand);
        e.setHelmetDropChance(helm);
        e.setChestplateDropChance(chest);
        e.setLeggingsDropChance(leggings);
        e.setBootsDropChance(boots);
    }

    private static ItemStack air() {
        return new ItemStack(Material.AIR);
    }

    public static void potionEffectChance(LivingEntity ent, PotionEffectType type, int strength, int duration, double chance) {
        if(Utils.randomBool(chance)) {
            ent.addPotionEffect(
                new PotionEffect(
                    type,
                    duration,
                    strength
                )
            );
        }
    }

    public static void burn(LivingEntity ent, int duration) {
        ent.setFireTicks(duration);
    }

    public static void burn(LivingEntity ent, int duration, double chance){
        if(Utils.randomBool(chance)) {
            burn(ent, duration);
        }
    }

    public static void damage(LivingEntity damager, LivingEntity target, double dmg) {
        target.damage(dmg);
        target.setLastDamageCause(new EntityDamageByEntityEvent(damager, target, EntityDamageEvent.DamageCause.CUSTOM,dmg));
    }

    public static void pull(LivingEntity livEnt, Location loc, double mag) {
        if (livEnt.getWorld() == loc.getWorld()) {
            Vector playerVec = livEnt.getLocation().toVector();
            Vector destinationVec = playerVec.subtract(loc.toVector());
            Vector normVec = destinationVec.normalize();
            livEnt.setVelocity(playerVec.add(normVec.multiply(mag * -1)));
        }
    }

    public static void teleport(Entity from, Entity to) {
        if(!(from.getWorld().getName().toLowerCase().contains("skyworld") && !TP_IN_SKYWORLD)) {
            from.teleport(to.getLocation());
        }
    }
    public static void teleport(Entity from, Entity to, double chance) {
        if(Utils.randomBool(chance)) {
            teleport(from, to);
        }
    }

    public static void setCustomMetadata(LivingEntity livEnt, String key, String value) {
        livEnt.setMetadata(key, new FixedMetadataValue(MonstersPlus.instance, value));
    }

    public static String getCustomMetadata(LivingEntity livEnt, String key) {
        List<MetadataValue> data = livEnt.getMetadata(key);
        return data.isEmpty() ? null : data.get(0).asString();
    }

    public static boolean hasCustomMetadata(LivingEntity livEnt, String key) {
        return getCustomMetadata(livEnt, key) != null;
    }

}
