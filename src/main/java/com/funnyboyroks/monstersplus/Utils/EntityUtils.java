package com.funnyboyroks.monstersplus.Utils;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityUtils {
    public static final int POTION_DURATION = 7000000;

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

}
