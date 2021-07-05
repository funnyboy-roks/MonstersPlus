package com.funnyboyroks.monstersplus.Utils;

import com.funnyboyroks.monstersplus.MonstersPlus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.stream.Collectors;

public class EntityUtils {

    public static final int POTION_DURATION = 7000000;
    private static final boolean TP_IN_SKYWORLD = false;

    public static void addLongPotion(LivingEntity e, PotionEffectType type, int level) {
        e.addPotionEffect(new PotionEffect(type, POTION_DURATION, level));
    }

    public static void setEquipment(LivingEntity ent, Material mainHand, Material offHand, Material helm, Material chest, Material leggings, Material boots) {
        EntityEquipment e = ent.getEquipment();
        e.setItemInMainHand(mainHand == null ? air() : new ItemStack(mainHand));
        e.setItemInOffHand(offHand == null ? air() : new ItemStack(offHand));
        e.setHelmet(helm == null ? air() : new ItemStack(helm));
        e.setChestplate(chest == null ? air() : new ItemStack(chest));
        e.setLeggings(leggings == null ? air() : new ItemStack(leggings));
        e.setBoots(boots == null ? air() : new ItemStack(boots));
    }

    public static void setEquipment(LivingEntity ent, ItemStack mainHand, ItemStack offHand, ItemStack helm, ItemStack chest, ItemStack leggings, ItemStack boots) {

        EntityEquipment e = ent.getEquipment();
        e.setItemInMainHand(mainHand == null ? air() : mainHand);
        e.setItemInOffHand(offHand == null ? air() : offHand);
        e.setHelmet(helm == null ? air() : helm);
        e.setChestplate(chest == null ? air() : chest);
        e.setLeggings(leggings == null ? air() : leggings);
        e.setBoots(boots == null ? air() : boots);

    }

    public static void setEquipment(LivingEntity ent, Material helm, Material chest, Material leggings, Material boots) {
        EntityEquipment e = ent.getEquipment();
        e.setHelmet(helm == null ? air() : new ItemStack(helm));
        e.setChestplate(chest == null ? air() : new ItemStack(chest));
        e.setLeggings(leggings == null ? air() : new ItemStack(leggings));
        e.setBoots(boots == null ? air() : new ItemStack(boots));
    }

    public static void setEquipment(LivingEntity ent, ItemStack helm, ItemStack chest, ItemStack leggings, ItemStack boots) {

        EntityEquipment e = ent.getEquipment();
        e.setHelmet(helm == null ? air() : helm);
        e.setChestplate(chest == null ? air() : chest);
        e.setLeggings(leggings == null ? air() : leggings);
        e.setBoots(boots == null ? air() : boots);

    }

    public static void setEquipment(LivingEntity livEnt, EquipmentSlot slot, Material mat) {
        setEquipment(livEnt, slot, new ItemStack(mat));
    }

    public static void setEquipment(LivingEntity livEnt, EquipmentSlot slot, ItemStack stack) {
        livEnt.getEquipment().setItem(slot, stack);
    }

    public static void setEquipment(LivingEntity to, LivingEntity from) {
        EntityEquipment e = from.getEquipment();
        setEquipment(
            to,
            e.getItemInMainHand(),
            e.getItemInOffHand(),
            e.getHelmet(),
            e.getChestplate(),
            e.getLeggings(),
            e.getBoots()
        );
    }

    public static void clearEquipment(LivingEntity livEnt) {
        setEquipment(livEnt, Material.AIR, null, null, null, null, null);
    }

    public static void setDropChances(LivingEntity ent, float value) {
        setDropChances(ent, value, value, value, value, value, value);
    }

    public static void setDropChances(LivingEntity ent, float mainHand, float offHand, float helm, float chest, float leggings, float boots) {
        EntityEquipment e = ent.getEquipment();
        e.setItemInMainHandDropChance(mainHand);
        e.setItemInOffHandDropChance(offHand);
        e.setHelmetDropChance(helm);
        e.setChestplateDropChance(chest);
        e.setLeggingsDropChance(leggings);
        e.setBootsDropChance(boots);
    }

    public static void setDropChance(LivingEntity livEnt, EquipmentSlot slot, float chance) {
        livEnt.getEquipment().setDropChance(slot, chance);
    }

    public static void setSpeed(LivingEntity livEnt, double value) {
        livEnt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(value);
    }

    private static ItemStack air() {
        return new ItemStack(Material.AIR);
    }

    public static void potionEffectChance(LivingEntity ent, PotionEffectType type, int strength, int duration, double chance) {
        if (Utils.randomBool(chance)) {
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

    public static void burn(LivingEntity ent, int duration, double chance) {
        if (Utils.randomBool(chance)) {
            burn(ent, duration);
        }
    }

    public static void burn(LivingEntity ent) {
        burn(ent, POTION_DURATION);
    }

    public static void damage(LivingEntity damager, LivingEntity target, double dmg) {
        target.damage(dmg);
        target.setLastDamageCause(new EntityDamageByEntityEvent(damager, target, EntityDamageEvent.DamageCause.CUSTOM, dmg));
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
        if (!(from.getWorld().getName().toLowerCase().contains("skyworld") && !TP_IN_SKYWORLD)) {
            from.teleport(to.getLocation());
        }
    }

    public static void teleport(Entity from, Entity to, double chance) {
        if (Utils.randomBool(chance)) {
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

    public static boolean nearbyOp(Location loc, int radius) {
        return loc.getNearbyPlayers(radius).stream().anyMatch(Player::isOp);
    }

    public static void heal(LivingEntity livEnt) {
        livEnt.setHealth(livEnt.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
    }

    public static void setMaxHealth(LivingEntity livEnt, double value) {
        livEnt.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value);
    }

    public static void setAttack(LivingEntity livEnt, double value) {
        livEnt.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(value);
    }

    public static List<LivingEntity> nearbyMonsters(Location loc, double rad) {
        return loc.getNearbyEntities(rad, rad, rad)
            .stream()
            .filter(
                e -> e instanceof Monster
            )
            .map(e -> (Monster) e)
            .collect(Collectors.toList());
    }

    public static boolean isTriggered(LivingEntity livEnt) {
        List<MetadataValue> data = livEnt.getMetadata("triggered");
        return data.size() > 0 && data.get(0).asBoolean();
    }

    public static void setTriggered(LivingEntity lent, boolean value) {
        lent.setMetadata("triggered", new FixedMetadataValue(MonstersPlus.instance, value));
    }

    /**
     * Shoots a projectile
     *
     * @param projType - EntityType (SNOWBALL, FIREBALL, WITHER_SKULL, SMALL_FIREBALL, DRAGON_FIREBALL == "LargeFireball")
     */
    public static void shootWithAngles(LivingEntity player, Location loc, Vector dir, int speed, int[] angles, EntityType projType) {
        // making sure that the vector is length 1
        dir.normalize();

        // Trick to get a vector pointing in the player's view direction,
        // but on the x-z-plane only and without problems when looking straight up (x, z = 0 then)
        Vector dirY = (new Location(loc.getWorld(), 0, 0, 0, loc.getYaw(), 0)).getDirection().normalize();
        for (int angle : angles) {
            Vector vec;
            if (angle != 0) {
                vec = rotateYAxis(dirY, angle);
                vec.multiply(Math.sqrt(vec.getX() * vec.getX() + vec.getZ() * vec.getZ())).subtract(dirY);
                vec = dir.clone().add(vec).normalize();
            } else {
                vec = dir.clone();
            }

            Projectile proj = null;
            World world = loc.getWorld();
            switch (projType) {
                case ARROW:
                    proj = world.spawn(loc, Arrow.class);
                    break;
                case SNOWBALL:
                    proj = world.spawn(loc, Snowball.class);
                    break;
                case FIREBALL:
                    proj = world.spawn(loc, Fireball.class);
                    break;
                case WITHER_SKULL:
                    proj = world.spawn(loc, WitherSkull.class);
                    break;
                case SMALL_FIREBALL:
                    proj = world.spawn(loc, SmallFireball.class);
                    break;
                case DRAGON_FIREBALL:
                    proj = world.spawn(loc, LargeFireball.class);
                    break;
            }

            proj.setShooter(player);
            proj.setVelocity(vec.clone().multiply(speed));
        }
    }

    private static Vector rotateYAxis(Vector dir, double angle) {
        double angleR = Math.toRadians(angle);
        double x = dir.getX();
        double z = dir.getZ();
        double cos = Math.cos(angleR);
        double sin = Math.sin(angleR);
        return (new Vector(x * cos + z * (-sin), 0.0, x * sin + z * cos)).normalize();
    }

    public static void liftUpward(LivingEntity livEnt, double power) {
        livEnt.setVelocity(new Vector(0, power, 0));
    }

    public static void liftUpward(LivingEntity livEnt, double power, double chance) {
        if (Utils.randomBool(chance)) {
            liftUpward(livEnt, power);
        }
    }

    public static boolean hasCustomName(LivingEntity livEnt) {
        return livEnt.getCustomName() != null && !livEnt.getCustomName().isEmpty();
    }

    public static void updateCustomName(LivingEntity livEnt) {
        livEnt.setCustomNameVisible(
            !(livEnt instanceof Player)
        );
    }

    public static void setTriggeredDelayed(int ticks, LivingEntity le, boolean b) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(MonstersPlus.instance, () -> EntityUtils.setTriggered(le, b), ticks);
    }

    public static void potionEffectAOE(Location loc, int r, PotionEffectType type, int strength, int duration) {
        loc.getNearbyEntities(r, r, r)
            .forEach(e -> {
                if (e instanceof LivingEntity) {
                    potionEffectChance((LivingEntity) e, type, strength, duration, 1);
                }
            });
    }

    public static void burnAOE(Location loc, int r, int duration) {
        loc.getNearbyEntities(r, r, r)
            .forEach(e -> {
                if (e instanceof LivingEntity) {
                    burn((LivingEntity) e, duration);
                }
            });
    }

    public static void liftUpwardAOE(Location loc, int r, double power) {
        loc.getNearbyEntities(r, r, r)
            .forEach(e -> {
                if (e instanceof LivingEntity) {
                    liftUpward((LivingEntity) e, power);
                }
            });
    }




    public enum ArmorType {
        LEATHER(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS),
        IRON(Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS),
        CHAINMAIL(Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS),
        GOLD(Material.GOLDEN_HELMET, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS, Material.GOLDEN_BOOTS),
        DIAMOND(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS),
        NETHERITE(Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS),
        ;

        public final ItemStack head;
        public final ItemStack chest;
        public final ItemStack legs;
        public final ItemStack boots;

        ArmorType(Material head, Material chest, Material legs, Material boots) {
            this.head = new ItemStack(head);
            this.chest = new ItemStack(chest);
            this.legs = new ItemStack(legs);
            this.boots = new ItemStack(boots);
        }

        public ItemStack head() {
            return head.clone();
        }

        public ItemStack chest() {
            return chest.clone();
        }

        public ItemStack legs() {
            return legs.clone();
        }

        public ItemStack boots() {
            return boots.clone();
        }

        public void apply(LivingEntity livEnt) {
            EntityEquipment eq = livEnt.getEquipment();
            eq.setHelmet(head());
            eq.setChestplate(chest());
            eq.setLeggings(legs());
            eq.setBoots(boots());
        }

        public void apply(LivingEntity livEnt, int lvl, Enchantment... enchants) {
            ItemStack h = head();
            ItemStack c = chest();
            ItemStack l = legs();
            ItemStack b = boots();

            for (Enchantment e : enchants) {
                ItemUtils.forceEnchant(h, e, lvl);
                ItemUtils.forceEnchant(c, e, lvl);
                ItemUtils.forceEnchant(l, e, lvl);
                ItemUtils.forceEnchant(b, e, lvl);
            }

            EntityEquipment eq = livEnt.getEquipment();
            eq.setHelmet(h);
            eq.setChestplate(c);
            eq.setLeggings(l);
            eq.setBoots(b);
        }
    }
}
