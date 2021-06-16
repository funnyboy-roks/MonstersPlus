package com.funnyboyroks.monstersplus.Utils;

import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
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
    private static final boolean TP_IN_SKYWORLD = false;

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
                    break;
                case HAWKEYE:
                    break;
                case POCUS:
                    break;
                case INFEARNO:
                    break;
                case HADAMARD:
                    break;
                case LESATH:
                    break;
                case SHELOB:
                    break;
                case RAIKOU:
                    break;
                case ENTEI:
                    break;
                case SUICUNE:
                    break;
                case BARKIRA:
                    break;
                case ACHILLES:
                    break;
                case ARACHNE:
                    break;
                case CHARLOTTE:
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

}
