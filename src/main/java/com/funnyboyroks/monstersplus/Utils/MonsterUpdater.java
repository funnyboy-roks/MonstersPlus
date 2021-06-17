package com.funnyboyroks.monstersplus.Utils;

import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
import com.funnyboyroks.monstersplus.Tasks.CombinedTasks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class MonsterUpdater {

    private static final int COLD_BOMB_RADIUS = 5;
    private static final int ROCK_SCORP_DAMAGE = 2;
    private static final double TRACK_STOP_CHANCE = 10;

    /**
     * Handles:
     * <ul>
     *     <li>Holy Skeleton</li>
     *     <li>Reforming Creeper</li>
     * </ul>
     */
    public static void update(EntityDeathEvent event) {
        int r = 30;

        LivingEntity livEnt = event.getEntity();

        if (livEnt.getCustomName() != null) {
            List<Player> nearbyPlayers = new ArrayList<>(event.getEntity().getLocation().getNearbyPlayers(r));
            if (!nearbyPlayers.isEmpty() && MonsterType.isMonster(livEnt)) {
                switch (MonsterType.getMonsterType(livEnt)) {
                    case HOLY_SKELETON:
                        respawnHolySkeleton(livEnt);
                        break;
                    case REFORMING_CREEPER:
                        respawnReformingCreeper(livEnt);
                        break;
                }
            }
        }

    }

    public static void update(LivingEntity livEnt, LivingEntity target, TargetReason reason) {

    }

    public static void update(LivingEntity damager, LivingEntity target, boolean isProjectile) {
        // TODO: Do this:
        // if (MonsterList.hasCustomName(damager) && !damager.isCustomNameVisible() && !(target instanceof Player)) {
        // 	MonsterList.updateCustomName(damager);
        // } else if (MonsterList.hasCustomName(target) && !target.isCustomNameVisible() && !(target instanceof Player)) {
        // 	MonsterList.updateCustomName(target);
        // }

        // TODO: updateMonsterTracking(damager);
        // TODO: updateMonsterTracking(target);

        MonsterType damagerType = MonsterType.getMonsterType(damager);
        MonsterType targetType = MonsterType.getMonsterType(target);

        boolean dmgrIsPlayer = damager instanceof Player;
        boolean targIsPlayer = target instanceof Player;

        if (damagerType != null) {
            switch (damagerType) {
                case SCORPION:
                    EntityUtils.potionEffectChance(target, PotionEffectType.POISON, 0, 70, 0.65);
                    break;
                case ARSONIC_ARCHER:
                    EntityUtils.potionEffectChance(target, PotionEffectType.POISON, 1, 100, 1);
                    break;
                case FIRE_ANT:
                    EntityUtils.burn(target, 80, 0.5);
                    break;
                case TOXIC_ANT:
                    EntityUtils.potionEffectChance(target, PotionEffectType.POISON, 0, 80, 0.5);
                    break;
                case FROST_ANT:
                    EntityUtils.potionEffectChance(target, PotionEffectType.SLOW, 2, 80, 0.5);
                    break;
                case LEGOLAS:
                    legolasAttack(damager, target);
                    break;
                case HAWKEYE:
                    EntityUtils.teleport(damager, target, 0.15);
                    break;
                case POCUS:
                    pocusAttack(damager, target);
                    break;
                case INFEARNO:
                    infearnoAttack(damager, target);
                    break;
                case HADAMARD:
                    hadamardAttack(damager, target);
                    break;
                case LESATH:
                    lesathAttack(damager, target);
                    break;
                case SHELOB:
                case ARACHNE:
                case CHARLOTTE:
                    EntityUtils.damage(damager, target, 7);
                    break;
                case RAIKOU:
                    raikouAttack(damager, target);
                    break;
                case ENTEI:
                    enteiAttack(damager, target);
                    break;
                case SUICUNE:
                    suicuneAttack(damager, target);
                    break;
                case BARKIRA:
                    barkiraAttack(damager, target);
                    break;
                case ACHILLES:
                    EntityUtils.potionEffectChance(target, PotionEffectType.SLOW, 2, 120, 1);
                    break;
                case SKELETON_SNIPER:
                    EntityUtils.potionEffectChance(target, PotionEffectType.BLINDNESS, 10, 50, 1);
                    break;
                case BROWN_RECLUSE:
                    EntityUtils.potionEffectChance(target, PotionEffectType.CONFUSION, 12, 300, 0.65);
                    break;
                case LUNATIC:
                    EntityUtils.potionEffectChance(target, PotionEffectType.CONFUSION, 8, 200, 0.65);
                    break;
                case THE_RESTLESS:
                    EntityUtils.potionEffectChance(target, PotionEffectType.SLOW, 5, 200, 0.5);
                    break;
                case BURNING_WALKER:
                    EntityUtils.burn(target, 80, 1);
                    break;
//            case BURNT_GHOUL:
//                break;
                case PYRE:
                case ARCHFIEND:
                    EntityUtils.burn(target, 160, 1);
                    break;
                case ARCTIC_ZED:
                case ICED_SHARPSHOOTER:
                case SPINECHILLER:
                case ICE_SPIDER:
                    EntityUtils.potionEffectChance(target, PotionEffectType.SLOW, 2, 60, 1);
                    break;
                case CRAZED_SKELETON:
                    EntityUtils.potionEffectChance(target, PotionEffectType.POISON,1, 60, 1);
                    break;
                case FILTH_PURCH:
                    EntityUtils.potionEffectChance(target, PotionEffectType.WITHER, 1, 4 * 20, 100);
                    break;
                case DAZZLER:
                    EntityUtils.potionEffectChance(target, PotionEffectType.CONFUSION, 50, 16 * 20, 100);
                    break;
                case FAMISHED_WALKER:
                    EntityUtils.potionEffectChance(target, PotionEffectType.HUNGER, 2, 8 * 20, 100);
                    break;
                case SUMO_SKELETON:
                    break;

            }
        } else {

            switch(targetType) {

            case STINK_SWARM:
                break;
            case ATILLA:
                break;
            case SLEEPY_HOLLOW:
                break;
            case LEGOLAS:
                break;
            case HAWKEYE:
                break;
            case CAPTAIN_AHAB:
                break;
            case POCUS:
                break;
            case HOCUS:
                break;
            case SHELOB:
                break;
            case RAIKOU:
                break;
            case TICKLES:
                break;
            case ENTEI:
                break;
            case SUICUNE:
                break;
            case BARKIRA:
                break;
            case HYDE:
                break;
            case JUGGLER:
                break;
            case ACHILLES:
                break;
            case PIGLET:
                break;
            case WILBUR:
                break;
            case ARACHNE:
                break;
            case CHARLOTTE:
                break;
            case STARVING_CREEPER:
                break;
            case FAMISHED_LURKER:
                break;
            case ROCK_SCORPION:
                break;
            case BURNING_WALKER:
                break;
            case BURNT_GHOUL:
                break;
            case HOT_BONES:
                break;
            case DESERT_SCORPION:
                break;
            case COLD_CORPSE:
                break;
            case FROSTED_BITER:
                break;
            case SWAMP_WITCH:
                break;
            case WARLOCK:
                break;
            case DARK_MAGE:
                break;
            }

        }
    }



    private static void respawnHolySkeleton(LivingEntity livEnt) {
        // TODO: RespawnEntityTask
    }

    private static void respawnReformingCreeper(LivingEntity livEnt) {
        // TODO: RespawnEntityTask
    }

    private static void legolasAttack(LivingEntity damager, LivingEntity target) {
        EntityUtils.potionEffectChance(target, PotionEffectType.SLOW, 1, 8 * 20, 0.35); // Slowness 2 for 8 seconds 35%
        EntityUtils.potionEffectChance(target, PotionEffectType.CONFUSION, 15, 12 * 20, 1); // Nausea 16 for 12 seconds 100%

        // Disabled in original code.  Implemented here for future enabling, if chosen
//        if(Utils.randomBool(0.5)) {
//            EntityUtils.pull(target, damager.getLocation(), 3);
//        }
    }

    private static void pocusAttack(LivingEntity damager, LivingEntity target) {
        EntityUtils.teleport(damager, target);
        EntityUtils.potionEffectChance(target, PotionEffectType.HARM, 2, 0, 1);
        EntityUtils.potionEffectChance(target, PotionEffectType.WITHER, 4, 6 * 20, 1);
    }


    private static void infearnoAttack(LivingEntity damager, LivingEntity target) {
        EntityUtils.burn(target, 10 * 20); // Fire for 10 sec
        EntityUtils.damage(damager, target, 7);
        EntityUtils.potionEffectChance(target, PotionEffectType.SLOW, 1, 5 * 20, 1); // Slowness 2 5 sec 100%
    }

    private static void lesathAttack(LivingEntity damager, LivingEntity target) {
        EntityUtils.damage(damager, target, 6);
        EntityUtils.potionEffectChance(target, PotionEffectType.POISON, 3, 6 * 20, 1); // Poison 4 6 sec 100%

        Location loc = target.getLocation();
        if(Utils.randomBool(0.5) && Utils.isSpawnableLocation(loc) && loc.getBlock().isEmpty()){
            CombinedTasks.PlaceAndClean(Material.COBWEB, loc, 0, 60);
        }
    }

    private static void hadamardAttack(LivingEntity damager, LivingEntity target) {
        EntityUtils.damage(damager, target, 5);
        EntityUtils.potionEffectChance(target, PotionEffectType.SLOW, 2, 10 * 20, 1); // Slowness 3 10 sec 100%
        EntityUtils.potionEffectChance(target, PotionEffectType.CONFUSION, 20, 60 * 20, 1); // Nausea 21 1 min 100%
        EntityUtils.potionEffectChance(target, PotionEffectType.POISON, 2, 10 * 20, 1); // Poison 3 10 sec 100%
    }

    private static void raikouAttack(LivingEntity damager, LivingEntity target) {
        EntityUtils.damage(damager, target, 6);
        if(Utils.randomBool(0.75)) {
            Utils.lightning(target.getLocation());
            Utils.lightning(target.getLocation());
            EntityUtils.damage(damager, target, 3);
        }
    }

    private static void enteiAttack(LivingEntity damager, LivingEntity target) {
        EntityUtils.damage(damager, target, 6);
        EntityUtils.burn(target, 8 * 20);
        if(Utils.randomBool(0.4) &&
            Utils.isSpawnableLocation(target.getLocation()) &&
            target.getLocation().getBlock().isEmpty() &&
            (target instanceof Player) &&
            Utils.isBuildLocation((Player) target, target.getLocation())
        ) {
            CombinedTasks.PlaceAndClean(Material.LAVA, target.getLocation(), 0, 60);
        }
    }

    private static void suicuneAttack(LivingEntity damager, LivingEntity target) {
        EntityUtils.damage(damager, target, 8);
        EntityUtils.potionEffectChance(target, PotionEffectType.SLOW, 1, 10 * 20, 1);
        EntityUtils.potionEffectChance(target, PotionEffectType.SLOW_DIGGING, 2, 8 * 20, 1);
        if(Utils.randomBool(0.33)) {
            createIceRing(target, 2, true);
        }
    }

    private static void createIceRing(LivingEntity target, int second, boolean useSlow) {
        // TODO: write this (https://github.com/nathank33/MonstersPlus/blob/5d91cc42615335ca3ec4192cd26478ad45472d9b/src/me/coolade/monstersplus/monsters/MonsterUpdater.java#L448)
    }

    private static void barkiraAttack(LivingEntity damager, LivingEntity target) {
        EntityUtils.damage(damager, target, 6);
        if (Utils.randomBool(0.5)) {
            target.getLocation().createExplosion(1.6F, false, false);
        }

    }
}
