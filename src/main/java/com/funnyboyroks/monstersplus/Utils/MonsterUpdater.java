package com.funnyboyroks.monstersplus.Utils;

import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
import com.funnyboyroks.monstersplus.MonstersPlus;
import com.funnyboyroks.monstersplus.Tasks.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

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
        LivingEntity livEnt = event.getEntity();

        if (EntityUtils.hasCustomName(livEnt)) {
            List<Player> nearbyPlayers = new ArrayList<>(event.getEntity().getLocation().getNearbyPlayers(30));
            if (!nearbyPlayers.isEmpty() && MonsterType.isMonster(livEnt)) {
                switch (MonsterType.getMonsterType(livEnt)) {
                    case HOLY_SKELETON:
                        new RespawnEntityTask(
                            livEnt,
                            MonsterType.HOLY_SKELETON.health,
                            ChatColor.GOLD + "The Holy Skeleton is Resurrecting",
                            80
                        ).runTask(MonstersPlus.instance);
                        break;
                    case REFORMING_CREEPER:
                        new RespawnEntityTask(
                            livEnt,
                            MonsterType.REFORMING_CREEPER.health,
                            ChatColor.GOLD + "The Creeper begins to reform",
                            80
                        ).runTask(MonstersPlus.instance);
                        break;
                }
            }
        }

    }

    public static void update(EntityTargetLivingEntityEvent event) {
        LivingEntity livEnt = event.getTarget();

        // Tracking Events
        if (EntityUtils.hasCustomName(livEnt) && !livEnt.isCustomNameVisible()) {
            EntityUtils.updateCustomName(livEnt);
        }

        if (!MonsterType.isMonster(livEnt)) {
            return;
        }
        MonsterType type = MonsterType.getMonsterType(livEnt);

        switch (type) {
            case VORTEX_CREEP:
                if (!EntityUtils.isTriggered(livEnt)) {
                    EntityUtils.setTriggered(livEnt, true);
                    new VortexCreepTask(
                        livEnt,
                        13,
                        6,
                        10,
                        0,
                        20,
                        0.36,
                        ChatColor.RED + "Vortex Creep begins to power up!");
                    // Reset the creeper
                    EntityUtils.setTriggeredDelayed((10 * 20 + 30), livEnt, false);
                }
                break;
            case ATILLA:
                new TntThrowTask(
                    livEnt,
                    25,
                    80,
                    -1,
                    35,
                    0,
                    100
                );
                break;
        }

    }

    public static void update(LivingEntity damager, LivingEntity target, boolean isProjectile) {
        if (
            EntityUtils.hasCustomName(damager) &&
                !damager.isCustomNameVisible() &&
                !(target instanceof Player)
        ) {
            EntityUtils.updateCustomName(damager);
        } else if (
            EntityUtils.hasCustomName(target) &&
                !target.isCustomNameVisible() &&
                !(target instanceof Player)
        ) {
            EntityUtils.updateCustomName(target);
        }

        updateMonsterTracking(damager);
        updateMonsterTracking(target);

        MonsterType damagerType = MonsterType.getMonsterType(damager);
        MonsterType targetType = MonsterType.getMonsterType(target);

        Location damagerLoc = damager.getLocation();
        Location targetLoc = target.getLocation();

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
                    EntityUtils.potionEffectChance(target, PotionEffectType.SLOW, 1, 8 * 20, 0.35); // Slowness 2 for 8 seconds 35%
                    EntityUtils.potionEffectChance(target, PotionEffectType.CONFUSION, 15, 12 * 20, 1); // Nausea 16 for 12 seconds 100%

                    // Disabled in original code.  Implemented here for future enabling, if chosen
//                    if(Utils.randomBool(0.5)) {
//                        EntityUtils.pull(target, damager.getLocation(), 3);
//                    }
                    break;
                case HAWKEYE:
                    EntityUtils.teleport(damager, target, 0.15);
                    break;
                case POCUS:
                    EntityUtils.teleport(damager, target);
                    EntityUtils.potionEffectChance(target, PotionEffectType.HARM, 2, 0, 1);
                    EntityUtils.potionEffectChance(target, PotionEffectType.WITHER, 4, 6 * 20, 1);
                    break;
                case INFEARNO:
                    EntityUtils.burn(target, 10 * 20); // Fire for 10 sec
                    EntityUtils.damage(damager, target, 7);
                    EntityUtils.potionEffectChance(target, PotionEffectType.SLOW, 1, 5 * 20, 1); // Slowness 2 5 sec 100%
                    break;
                case HADAMARD:
                    EntityUtils.damage(damager, target, 5);
                    EntityUtils.potionEffectChance(target, PotionEffectType.SLOW, 2, 10 * 20, 1); // Slowness 3 10 sec 100%
                    EntityUtils.potionEffectChance(target, PotionEffectType.CONFUSION, 20, 60 * 20, 1); // Nausea 21 1 min 100%
                    EntityUtils.potionEffectChance(target, PotionEffectType.POISON, 2, 10 * 20, 1); // Poison 3 10 sec 100%
                    break;
                case LESATH:
                    EntityUtils.damage(damager, target, 6);
                    EntityUtils.potionEffectChance(target, PotionEffectType.POISON, 3, 6 * 20, 1); // Poison 4 6 sec 100%

                    Location loc = target.getLocation();
                    if (Utils.randomBool(0.5) && Utils.isSpawnableLocation(loc) && loc.getBlock().isEmpty()) {
                        CombinedTasks.PlaceAndClean(Material.COBWEB, loc, 0, 60);
                    }
                    break;
                case SHELOB:
                case ARACHNE:
                case CHARLOTTE:
                    EntityUtils.damage(damager, target, 7);
                    break;
                case RAIKOU:
                    EntityUtils.damage(damager, target, 6);
                    if (Utils.randomBool(0.75)) {
                        Utils.lightning(target.getLocation());
                        Utils.lightning(target.getLocation());
                        EntityUtils.damage(damager, target, 3);
                    }
                    break;
                case ENTEI:
                    EntityUtils.damage(damager, target, 6);
                    EntityUtils.burn(target, 8 * 20);
                    if (Utils.randomBool(0.4) &&
                        Utils.isSpawnableLocation(target.getLocation()) &&
                        target.getLocation().getBlock().isEmpty() &&
                        (target instanceof Player) &&
                        Utils.isBuildLocation((Player) target, target.getLocation())
                    ) {
                        CombinedTasks.PlaceAndClean(Material.LAVA, target.getLocation(), 0, 60);
                    }
                    break;
                case SUICUNE:
                    EntityUtils.damage(damager, target, 8);
                    EntityUtils.potionEffectChance(target, PotionEffectType.SLOW, 1, 10 * 20, 1);
                    EntityUtils.potionEffectChance(target, PotionEffectType.SLOW_DIGGING, 2, 8 * 20, 1);
                    if (Utils.randomBool(0.33)) {
                        createIceRing(target, 2, true);
                    }
                    break;
                case BARKIRA:
                    EntityUtils.damage(damager, target, 6);
                    if (Utils.randomBool(0.5)) {
                        target.getLocation().createExplosion(1.6F, false, false);
                    }

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
                    EntityUtils.potionEffectChance(target, PotionEffectType.POISON, 1, 60, 1);
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
                    EntityUtils.liftUpward(target, 0.6, 50);
                    break;

            }
        } else {

            if (targetType == null || !(damager instanceof Player)) {
                return;
            }

            switch (targetType) {

                case STINK_SWARM:
                    double updatedHealth = MonsterType.STINK_SWARM.health - 1;
                    if (target.getHealth() > updatedHealth) {
                        target.setHealth(updatedHealth);
                    }
                    if (updatedHealth > 0) {
                        EntityUtils.setMaxHealth(target, updatedHealth);
                    }

                    int rand = (int) (Math.random() * 4) + 1;
                    for (int i = 0; i < rand; i++) {
                        LivingEntity swarm = (LivingEntity) targetLoc.getWorld().spawnEntity(targetLoc, EntityType.ZOMBIE, CreatureSpawnEvent.SpawnReason.CUSTOM);

                        Zombie swarmZomb = (Zombie) swarm;
                        if (Utils.randomBool()) {
                            swarmZomb.setBaby();
                        }
                        swarmZomb.setHealth(3);
                        EntityUtils.heal(swarmZomb);
                        swarmZomb.setCustomName(ChatColor.GREEN + "Stink Swarm");
                    }
                    break;
                case ATILLA:
                    EntityUtils.damage(damager, target, 2);
                    break;
                case SLEEPY_HOLLOW:
                    EntityUtils.liftUpward(damager, 1, .33);
                    break;
                case LEGOLAS:
                    EntityUtils.damage(target, damager, 1);
                    if (Utils.randomBool(0.3)) {
                        EntityUtils.potionEffectChance(damager, PotionEffectType.SLOW, 3, 4 * 20, 1);
                    }
                    break;
                case HAWKEYE:
                case ENTEI:
                case TICKLES:
                case SUICUNE:
                case BARKIRA:
                case POCUS:
                    EntityUtils.teleport(damager, target, 0.25);
                    break;
                case CAPTAIN_AHAB:
                    EntityUtils.teleport(damager, target, 0.2);

                    if (!EntityUtils.isTriggered(target)) {
                        if (Utils.randomBool(.15)) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(
                                MonstersPlus.instance,
                                () -> target.setVelocity(new Vector(0, 1.6, 0))
                            );

                            EntityUtils.setTriggered(target, true);
                            new VortexCreepTask(target, 15, 10, 7, 2, 2, 0.55);
                            EntityUtils.setTriggeredDelayed(16, target, false);

                            for (Player player : target.getLocation().getNearbyPlayers(15)) {
                                EntityUtils.potionEffectChance(player, PotionEffectType.POISON, 2, 4 * 20, 1);
                                EntityUtils.damage(target, player, 4);
                            }
                        }
                    }
                    break;
                case HOCUS:
                    EntityUtils.teleport(target, damager, 0.25);
                    break;
                case SHELOB:

                    final int hrange = 10;
                    final int vrange = 3;
                    final int total = 10;

                    if (Utils.randomBool(0.4)) {
                        for (int i = 0; i < total; i++) {
                            Location blockLoc = new Location(
                                damagerLoc.getWorld(),
                                Utils.randomInt(hrange, (int) damagerLoc.getX()) - hrange / 2,
                                Utils.randomInt(vrange, (int) damagerLoc.getX()) - vrange / 2,
                                Utils.randomInt(hrange, (int) damagerLoc.getX()) - hrange / 2
                            );

                            if (
                                Utils.randomBool(0.65) &&
                                    Utils.isSpawnableLocation(blockLoc) &&
                                    damagerLoc.getWorld().getBlockAt(blockLoc).isEmpty()
                            ) {
                                CombinedTasks.PlaceAndClean(Material.COBWEB, blockLoc, 2, 60);
                            }
                        }
                    }
                    break;
                case RAIKOU:
                    EntityUtils.teleport(target, damager, 30);
                    Location raikouLoc = damager.getLocation();
                    if (Utils.randomBool(0.33)) {
                        raikouLoc.getWorld().strikeLightning(raikouLoc);
                        raikouLoc.getWorld().strikeLightning(raikouLoc);
                        EntityUtils.damage(damager, target, 4);
                    }
                    break;
                case HYDE:
                    EntityUtils.teleport(target, damager, 33);
                    EntityUtils.damage(damager, target, 3);
                    if (Utils.randomBool(0.5)) {
                        if (
                            Utils.isSpawnableLocation(damager.getLocation()) &&
                                Utils.isBuildLocation((Player) damager, damager.getLocation())
                        ) {
                            for (int i = 0; i < 5; i++) {
                                int x = Utils.randomInt(10, -5);
                                int z = Utils.randomInt(10, -5);
                                Vector vec = damager
                                    .getLocation()
                                    .toVector()
                                    .subtract(
                                        target
                                            .getLocation()
                                            .clone()
                                            .add(
                                                x,
                                                i * 3,
                                                z
                                            )
                                            .toVector()
                                    );
                                EntityUtils.shootWithAngles(
                                    target,
                                    target.getLocation().clone().add(
                                        x,
                                        i * 3,
                                        z
                                    ),
                                    vec,
                                    1,
                                    new int[]{15},
                                    EntityType.DRAGON_FIREBALL);
                            }
                        }
                    }
                    break;
                case JUGGLER:
                    if (!target.getWorld().getBlockAt(target.getLocation().clone().add(0, -1, 0)).isEmpty()) {
                        EntityUtils.heal(target);
                    }

                    if (isProjectile) {
                        target.damage(50);
                        EntityUtils.damage(damager, target, 50);
                        Location loc = target.getLocation();
                        if (!target.isDead()) {
                            loc.createExplosion(1.1F, false, false);
                            target.setVelocity(
                                new Vector(
                                    Math.random() - 0.5,
                                    1.3,
                                    Math.random() - 0.5
                                )
                            );
                            target.getWorld().playEffect(target.getLocation(), Effect.POTION_BREAK, 3);
                        }
                    } else {
                        EntityUtils.heal(target);
                        if (Utils.randomBool(0.15)) {
                            damager.sendMessage(ChatColor.GOLD + "Hint: Keep shooting Juggler with arrows.");
                        }
                    }
                    break;
                case ACHILLES:
                    EntityUtils.teleport(target, damager, 15);
                    if (Utils.randomBool(0.15)) {
                        for (Player player : target.getLocation().getNearbyPlayers(15)) {
                            EntityUtils.potionEffectChance(player, PotionEffectType.CONFUSION, 45, 60 * 20, 1);
                            EntityUtils.damage(target, damager, 4);
                        }
                    }
                    break;
                case PIGLET:
                    EntityUtils.teleport(target, damager, 35);
                    for (Player player : target.getLocation().getNearbyPlayers(20)) {
                        EntityUtils.damage(target, player, 8);
                    }
                    break;
                case WILBUR:
                    EntityUtils.teleport(target, damager, 33);
                    if (Utils.randomBool(0.15)) {
                        for (int i = 0; i < 5; i++) {
                            Location blockLoc = new Location(
                                damagerLoc.getWorld(),
                                Utils.randomInt(13, (int) damagerLoc.getX()) - 6.5,
                                Utils.randomInt(3, (int) damagerLoc.getX()) - 1.5,
                                Utils.randomInt(13, (int) damagerLoc.getX()) - 6.5
                            );

                            if (damagerLoc.getWorld().getBlockAt(blockLoc).isEmpty()) {
                                LivingEntity piglin = (LivingEntity) blockLoc.getWorld().spawnEntity(blockLoc,
                                    EntityType.ZOMBIFIED_PIGLIN);

                                EntityUtils.setEquipment(piglin, EquipmentSlot.HAND, Material.IRON_AXE);
                                EntityUtils.setDropChance(piglin, EquipmentSlot.HAND, 0);
                            }
                        }
                    }
                    break;
                case ARACHNE:
                    EntityUtils.teleport(target, damager, 33);
                    if (Utils.randomBool(0.15)) {
                        for (int i = 0; i < 5; i++) {
                            Location blockLoc = new Location(
                                damagerLoc.getWorld(),
                                Utils.randomInt(13, (int) damagerLoc.getX()) - 6.5,
                                Utils.randomInt(3, (int) damagerLoc.getX()) - 1.5,
                                Utils.randomInt(13, (int) damagerLoc.getX()) - 6.5
                            );

                            if (damagerLoc.getWorld().getBlockAt(blockLoc).isEmpty()) {
                                blockLoc.getWorld().spawnEntity(blockLoc, EntityType.CAVE_SPIDER);
                            }
                        }
                    }
                    break;
                case CHARLOTTE:
                    EntityUtils.teleport(target, damager, 33);
                    final int radius = 15;

                    if (Utils.randomBool(0.15)) {
                        for (Player player : target.getLocation().getNearbyPlayers(radius)) {
                            Location loc = player.getLocation();
                            if (
                                Utils.isSpawnableLocation(loc) &&
                                    loc.getWorld().getBlockAt(loc).isEmpty()
                            ) {
                                CombinedTasks.PlaceAndClean(Material.COBWEB, loc, 1, 60);
                            }
                            EntityUtils.burn(player, 6 * 20);
                        }
                    }
                    break;
                case STARVING_CREEPER:
                case FAMISHED_LURKER:
                    EntityUtils.potionEffectChance(damager, PotionEffectType.HUNGER, 2, 160, 0.5);
                    break;
                case ROCK_SCORPION:
                    EntityUtils.damage(target, damager, ROCK_SCORP_DAMAGE);
                    break;
                case BURNING_WALKER:
                case BURNT_GHOUL:
                case HOT_BONES:
                case DESERT_SCORPION:
                    EntityUtils.burn(damager, 2 * 20, 0.4);
                    break;
                case COLD_CORPSE:
                case FROSTED_BITER:
                    EntityUtils.potionEffectChance(damager, PotionEffectType.SLOW, 1, 60, 0.5);
                    break;
                case SWAMP_WITCH:
                    EntityUtils.potionEffectChance(damager, PotionEffectType.WITHER, 1, 6 * 20, 0.2);
                    break;
                case WARLOCK:
                    EntityUtils.potionEffectChance(damager, PotionEffectType.CONFUSION, 50, 30 * 20, 0.25);
                    break;
                case DARK_MAGE:
                    EntityUtils.potionEffectChance(damager, PotionEffectType.BLINDNESS, 80, 5 * 20, 0.5);
                    break;
            }

        }
    }

    public static void update(EntityExplodeEvent event) {
        if (event.isCancelled() || !(event.getEntity() instanceof Creeper)) return;

        Creeper creeper = (Creeper) event.getEntity();
        Location loc = event.getLocation();

        MonsterType type = MonsterType.getMonsterType(creeper);
        if (type == null) return;

        switch (type) {
            case COLD_BOMB:
                EntityUtils.potionEffectAOE(loc, 6, PotionEffectType.SLOW, 2, 4 * 20);
                break;
            case DROOPY:
                EntityUtils.potionEffectAOE(loc, 6, PotionEffectType.BLINDNESS, 15, 7 * 20);
                break;
            case GAS_BAG:
                EntityUtils.potionEffectAOE(loc, 6, PotionEffectType.POISON, 1, 6 * 20);
                break;
            case HOT_HEAD:
                EntityUtils.burnAOE(loc, 5, 7 * 20);
                break;
            case LITTLE_BOY:
                loc.createExplosion(4, false, false);
                break;
            case QUAKER:
                EntityUtils.liftUpwardAOE(loc, 10, 1.25);
                break;
        }
    }

    public static void update(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Arrow) {
            ProjectileSource projSource = event.getEntity().getShooter();
            if (!(projSource instanceof LivingEntity)) return;

            LivingEntity livEnt = (LivingEntity) projSource;
            MonsterType sourceType = MonsterType.getMonsterType(livEnt);
            if (sourceType == MonsterType.HAWKEYE) {
                WitherSkull skull = livEnt.getWorld().spawn(event.getEntity().getLocation(), WitherSkull.class);
                skull.setVelocity(event.getEntity().getVelocity());
                skull.setShooter(livEnt);
            }
        }
    }

    public static void createIceRing(LivingEntity target, int second, boolean useSlow) {
        Location loc = target.getLocation();
        Block centreBlock = loc.getBlock();
        List<Block> blocks = Utils.relativeBlocks(centreBlock);
        blocks.forEach(b -> {
            if (
                Utils.isSpawnableLocation(b.getLocation()) &&
                    b.isEmpty()
            ) {
                CombinedTasks.PlaceAndClean(Material.ICE, b.getLocation(), 0, second * 20);
                new CleanBlockTask(Material.WATER, Material.AIR, b.getLocation(), second * 20 * 2);
            }
        });

        if (useSlow) {
            EntityUtils.potionEffectChance(target, PotionEffectType.SLOW, 5, 2 * 20, 1);
        }
    }

    public static void updateMonsterTracking(LivingEntity lent) {
        if (!(lent instanceof Monster)) {
            return;
        }
        Monster mob = (Monster) lent;
        if (Utils.randomBool(TRACK_STOP_CHANCE)) {
            mob.setTarget(lent);
        }
    }

}
