package com.funnyboyroks.monstersplus;

import com.funnyboyroks.monstersplus.Data.structs.CustomItem;
import com.funnyboyroks.monstersplus.Tasks.CombinedTasks;
import com.funnyboyroks.monstersplus.Utils.*;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class CustomItemHandler {

    public static final Map<UUID, Integer> ARROW_CYCLE_INSTANCES = new HashMap<>();
    public static final Map<UUID, Pair<CustomItem, UUID>> PROJ_INSTANCES = new HashMap<>();
    public static final List<UUID> ANVILS = new ArrayList<>();

    public static CustomItem getCustomItem(ItemStack stack) {
        if(!stack.hasItemMeta()) return null;

        ItemMeta meta = stack.getItemMeta();
        List<Component> lore = meta.lore();

        return Arrays.stream(CustomItem.values())
            .filter((e) -> e.matches(stack))
            .findFirst()
            .orElse(null);
    }

    public static boolean isCustomItem(ItemStack stack) {
        return getCustomItem(stack) != null;
    }

    public static void playerArrowShoot(EntityShootBowEvent event, Player player) {
        Entity projEnt = event.getProjectile();
        ItemStack arrow = event.getConsumable();

        if (arrow == null || arrow.getType() != Material.ARROW || !(projEnt instanceof Projectile) || !isCustomItem(arrow)) {
            return;
        }
        event.setConsumeItem(true);

        Projectile proj = (Projectile) projEnt;
        CustomItem type = getCustomItem(arrow);

        if (Utils.isPvpLocation(proj.getLocation()) && Utils.isBuildLocation(player, proj.getLocation())) {

            switch (type) {
                case FIREBALL_ARROW:
                    proj.remove();
                    proj = player.launchProjectile(LargeFireball.class);
                    break;
                case COMET_ARROW:
                    proj.remove();
                    proj = player.launchProjectile(SmallFireball.class);
                    break;
                case SKULL_ARROW:
                    proj.remove();
                    proj = player.launchProjectile(WitherSkull.class);
                    break;
            }
        }
        PROJ_INSTANCES.put(proj.getUniqueId(), new Pair<>(type, player.getUniqueId()));
    }

    public static void arrowHit(ProjectileHitEvent event) {
        Projectile arrow = event.getEntity();
        Entity target = event.getHitEntity();

        if (!PROJ_INSTANCES.containsKey(arrow.getUniqueId())) return;

        CustomItem type = PROJ_INSTANCES.get(arrow.getUniqueId()).getFirst();
        Player shooter = Bukkit.getPlayer(PROJ_INSTANCES.get(arrow.getUniqueId()).getSecond());
        Location loc = arrow.getLocation();

        if (shooter == null || !Utils.isPvpLocation(loc)) return;

        event.setCancelled(true);
        switch (type) {
            case REPULSE_ARROW: {
                arrow.getNearbyEntities(9, 9, 9).forEach(e -> {
                    if (!(e instanceof LivingEntity)) return;
                    if (!(e instanceof Player) || e.getUniqueId() != shooter.getUniqueId()) {
                        Vector distVec = e.getLocation()
                            .toVector()
                            .clone()
                            .subtract(
                                arrow.getLocation()
                                    .toVector()
                            );
                        e.setVelocity(
                            distVec.clone()
                                .normalize()
                                .multiply(1.5)
                                .multiply((1 / (distVec.length() * .2)))
                        );
                    }
                });
                if (Utils.randomBool()) arrow.remove();
            }
            break;
            case VORTEX_ARROW: {
                arrow.getNearbyEntities(9, 9, 9).forEach(e -> {
                    if (!(e instanceof LivingEntity)) return;
                    if (!(e instanceof Player) || e.getUniqueId() != shooter.getUniqueId()) {
                        Vector distVec = arrow.getLocation()
                            .toVector()
                            .clone()
                            .subtract(
                                e.getLocation()
                                    .toVector()
                            );
                        e.setVelocity(
                            distVec.clone()
                                .normalize()
                                .multiply(.3)
                        );
                    }
                });
                if (Utils.randomBool()) arrow.remove();
            }
            break;
            case TORCH_ARROW: {
                if (Utils.isBuildLocation(shooter, loc)) {
                    BlockIterator iterator = new BlockIterator(loc.getWorld(), arrow.getLocation().toVector(), arrow.getVelocity().normalize(), 0, 4);
                    Block hitBlock = null;
                    while (iterator.hasNext()) {
                        hitBlock = iterator.next();
                        if (!hitBlock.getType().isEmpty()) break;
                    }
                    if (hitBlock == null) break;
                    Location newLoc = loc.getBlock().getLocation().subtract(hitBlock.getLocation());

                    // This following code is a little strange, but it's direct from the old, with just a few changes.
                    double[] vals = {newLoc.getX(), newLoc.getY(), newLoc.getZ()};
                    int totalTries = 7; // If the randomization does not get it
                    // correct in 3 tries than it cancels.
                    while (totalTries > 0) {
                        double largestVal = 0;
                        for (double val : vals) {
                            double temp = Math.abs(val);
                            if (temp > largestVal) {
                                largestVal = temp;
                            }
                        }

                        // Get rid of the values that are smaller than the
                        // largestval
                        for (int i = 0; i < vals.length; i++) {
                            // Use this math.abs so that we retain the negative sign
                            // if necessary
                            if (Math.abs(vals[i]) >= 1) {
                                vals[i] /= Math.abs(vals[i]);
                            }
                        }

                        // If there are still multiple values of 1 or -1, we need to
                        // get it so that there is only one
                        int totalOnesFound = 0;
                        for (double val : vals) {
                            if ((int) Math.abs(val) == 1) {
                                totalOnesFound++;
                            }
                        }
                        // randomly set one of the ones to 0
                        while (totalOnesFound > 1) {
                            Random random = new Random();
                            int rand = random.nextInt(vals.length);
                            if (Math.abs(vals[rand]) == 1) {
                                vals[rand] = 0;
                                totalOnesFound--;
                            }
                        }
                        Block relBlock = hitBlock.getRelative((int) vals[0], (int) vals[1], (int) vals[2]);
                        if (relBlock.isReplaceable() || relBlock.isEmpty()) {
                            relBlock.setType(Material.TORCH);
                            arrow.remove();
                            return;
                        } else {
                            totalTries--;
                        }
                    }
                }
            }
            break;
            case EXPLOSIVE_ARROW: {
                arrow.remove();
                loc.createExplosion(2.6f, false, false);
            }
            break;
            case FIREBALL_ARROW: {
                arrow.remove();
                loc.createExplosion(2.4f, false, false);
            }
            break;
            case COMET_ARROW: {
                for (int i = 0; i < 4; i++) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(
                        MonstersPlus.instance,
                        () -> {
                            Location tempLoc = loc.add(0, 8, 0);
                            LargeFireball fball = loc.getWorld().spawn(tempLoc, LargeFireball.class);
                            fball.setVelocity(new Vector(0, -4, 0));
                            PROJ_INSTANCES.put(fball.getUniqueId(), new Pair<>(CustomItem.FIREBALL_ARROW, shooter.getUniqueId()));
                        },
                        10 * (i + 1)
                    );
                }
            }
            break;
            case GRAPPLE_ARROW: {
                if(!Utils.isBuildLocation(shooter, loc) || !Utils.isPearlAllowed(shooter, loc)) return;

                arrow.remove();
                BlockIterator iterator = new BlockIterator(loc.getWorld(), loc.toVector(), arrow.getVelocity().normalize(), 0, 4);
                Block block = null;
                Block prevBlock = null;

                // Find the block directly in line of the arrow
                while (iterator.hasNext()) {
                    prevBlock = block;
                    block = iterator.next();
                    if (block.getType() != Material.AIR) {
                        // Go forward 1 additional time so that we make sure we are into the wall.
                        if (iterator.hasNext()) {
                            block = iterator.next();
                        }
                        break;
                    }
                }

                if(prevBlock == null) prevBlock = block;

                shooter.teleport(prevBlock.getLocation());

            }
            break;
            case NET_ARROW: {
                arrow.remove();
                Block block = loc.getBlock();

                int r = 2;
                for (int x = -r + 1; x <= r; x++) {
                    for (int y = -r + 1; y <= r; y++) {
                        for (int z = -r + 1; z <= r; z++) {
                            Block loopBlock = block.getRelative(x, y, z);
                            if(loopBlock.isEmpty() && Utils.isBuildLocation(shooter, loopBlock.getLocation()) && !loopBlock.getRelative(BlockFace.DOWN).isEmpty()) {
                                CombinedTasks.PlaceAndClean(Material.COBWEB, loopBlock.getLocation(), 0, 30);
                            }
                        }
                    }
                }

            }
            break;
            case BLIZZARD_ARROW: {
                arrow.getNearbyEntities(8, 8, 8).forEach(e -> {
                    if (!(e instanceof LivingEntity)) return;
                        // Make sure that we arent changing the shooter's speed
                    if (!(e instanceof Player) || e.getUniqueId() != shooter.getUniqueId()) {
                        LivingEntity livEnt = (LivingEntity) e;
                        MonsterUpdater.createIceRing(livEnt, 3, false);

                        if (livEnt instanceof Player) {
                            EntityUtils.potionEffectChance(livEnt, PotionEffectType.SLOW, 5,30, 1);
                        } else {
                            EntityUtils.potionEffectChance(livEnt, PotionEffectType.SLOW, 5,60, 1);
                        }
                    }
                });
            }
            break;
            default: event.setCancelled(false);
        }


    }

    public static void eggHit(ProjectileHitEvent event) {
        Entity egg = event.getEntity();
        if (!PROJ_INSTANCES.containsKey(egg.getUniqueId())) return;

        Location loc = egg.getLocation();
        Pair<CustomItem, UUID> p = PROJ_INSTANCES.get(egg.getUniqueId());
        Player player = Bukkit.getPlayer(p.getSecond());
        CustomItem item = p.getFirst();
        PROJ_INSTANCES.remove(egg.getUniqueId());
        if ((!Utils.isPvpLocation(loc) || !Utils.isFlagAllowed(loc, DefaultFlag.USE, player)) && !player.isOp()) return;

        List<LivingEntity> nearby6 = loc.getNearbyEntities(6, 6, 6)
            .stream()
            .filter(e -> e instanceof LivingEntity)
            .map(e -> (LivingEntity) e)
            .collect(Collectors.toList());
        switch(item) {
            case SURVIVALIST_BOMB: {
                    nearby6.forEach(livEnt -> {
                    EntityUtils.potionEffectChance(livEnt, PotionEffectType.BLINDNESS, 80, 4 * 20, 1);
                    EntityUtils.potionEffectChance(livEnt, PotionEffectType.NIGHT_VISION, 80, 4 * 20, 1);
                    EntityUtils.potionEffectChance(livEnt, PotionEffectType.SLOW, 80, 4 * 20, 1);
                    }
                );
            }
            break;
            case WARRIOR_BOMB: {
                nearby6.forEach(livEnt -> {
                    EntityUtils.burn(livEnt, 8 * 20, 1);
                });
                LivingEntity livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIFIED_PIGLIN);
                EntityUtils.setEquipment(
                    livEnt,
                    EquipmentSlot.HAND,
                    Material.DIAMOND_SWORD
                );
                EntityUtils.setDropChances(livEnt, 0f);
                EntityUtils.ArmorType.GOLD.apply(livEnt);

                Bukkit.getScheduler().runTaskLater(MonstersPlus.instance, livEnt::remove, 10 * 20);

                if (nearby6.size() > 0) {
                    ((Monster) livEnt).setTarget(Utils.choseRandom(nearby6));
                }
            }
            break;
            case WITCH_DOCTOR_BOMB: {
                nearby6.forEach(livEnt -> {
                    EntityUtils.potionEffectChance(livEnt, PotionEffectType.WITHER, 1, 4 * 20, 1);
                });

                for (int i = 0; i < 3; i++) {
                    LivingEntity livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
                    EntityUtils.setEquipment(livEnt, EquipmentSlot.HAND, Material.BOW);
                    EntityUtils.setDropChance(livEnt, EquipmentSlot.HAND, 0f);
                    EntityUtils.setMaxHealth(livEnt,100);
                    EntityUtils.heal(livEnt);
                    Bukkit.getScheduler().runTaskLater(MonstersPlus.instance, livEnt::remove, 10 * 20);
                    if (nearby6.size() > 0) {
                        ((Monster) livEnt).setTarget(Utils.choseRandom(nearby6));
                    }
                }
            }
            break;
            case MINER_BOMB: {
                float SIZE = 2F;
                int DELAY = 3;
                for (int i = 0; i < 26; i += 2) {
                    Location loc2 = loc.clone().add(i, 0, 0);
                    delayedExplosion(loc2, SIZE, DELAY * i);

                    loc2 = loc.clone().add(-i, 0, 0);
                    delayedExplosion(loc2, SIZE, DELAY * i);

                    loc2 = loc.clone().add(0, 0, i);
                    delayedExplosion(loc2, SIZE, DELAY * i);

                    loc2 = loc.clone().add(0, 0, -i);
                    delayedExplosion(loc2, SIZE, DELAY * i);

                    loc2 = loc.clone().add(i, 0, i);
                    delayedExplosion(loc2, SIZE, DELAY * i);

                    loc2 = loc.clone().add(i, 0, -i);
                    delayedExplosion(loc2, SIZE, DELAY * i);

                    loc2 = loc.clone().add(-i, 0, i);
                    delayedExplosion(loc2, SIZE, DELAY * i);

                    loc2 = loc.clone().add(-i, 0, -i);
                    delayedExplosion(loc2, SIZE, DELAY * i);
                }
            }
            break;
            case FISHERMAN_BOMB: {
                nearby6.forEach(livEnt -> Utils.lightning(livEnt.getLocation()));
                for (int i = -5; i <= 5; i++) {
                    for (int j = -5; j <= 5; j++) {
                        if (Utils.randomBool(.1)) {
                            int finalJ = j;
                            int finalI = i;
                            Bukkit.getScheduler().runTaskLater(
                                MonstersPlus.instance,
                                () -> {
                                    Utils.lightning(loc.clone().add(finalI, 0, finalJ));
                                },
                                Utils.randomInt(150)
                            );
                        }
                    }
                }
            }
            break;
            case BLACKSMITH_BOMB: {
                World world = loc.getWorld();
                for (int i = -5; i <= 5; i++) {
                    for (int j = -5; j <= 5; j++) {
                        if (Utils.randomBool(.65)) {
                            FallingBlock ent = world.spawnFallingBlock(loc.clone().add(i, 20, j), Material.ANVIL.createBlockData());
                            ent.setFallDistance(50F);
                            ent.setDropItem(false);
                            ANVILS.add(ent.getUniqueId());
                        }
                    }
                }
            }
            break;
        }
    }

    private static void delayedExplosion(Location loc, float power, int delay) {
        Bukkit.getScheduler().runTaskLater(
            MonstersPlus.instance,
            () -> {
                loc.createExplosion(power);
            },
            delay
        );
    }

    /**
     * <b>THIS METHOD IS NOT COMPLETE.</b><br>
     * I have honestly no idea what it's supposed to do, but it's not used. <br>
     * Original: ArrowListener#621
     *
     * @param player Player
     * @param stack  ItemStack
     * @return idk
     */
    private ItemStack arrowCycleCheck(Player player, ItemStack stack) {
        if (!ARROW_CYCLE_INSTANCES.containsKey(player.getUniqueId())) {
            return stack;
        }
        player.updateInventory();
        PlayerInventory inv = player.getInventory();

        int savedSlot = ARROW_CYCLE_INSTANCES.get(player.getUniqueId());
        int firstArrowSlot = inv.first(Material.ARROW);

        if (savedSlot == firstArrowSlot) {
            return stack;
        }

        ItemStack slotItem = inv.getItem(savedSlot);
        if (slotItem == null || slotItem.getType() != Material.ARROW) {
            return stack;
        }

        return stack;
    }


    @EventHandler()
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Projectile proj = event.getEntity();
        if(!(proj.getShooter() instanceof Player)) return;

        Player player = (Player) proj.getShooter();
        ItemStack stack = player.getInventory().getItemInMainHand();

        CustomItem item = CustomItemHandler.getCustomItem(stack);
        if(item == null) return;

        if(Cooldown.onCooldown(player.getUniqueId(), "bombs")) {
            TextUtils.sendFormatted(
                player,
                "&(red)You must wait {&(gold)%0} before using another bomb.",
                Cooldown.getTimeFormatted(player.getUniqueId(), "bombs")
            );
            event.setCancelled(true);
            ItemUtils.changeAmount(stack, +1);
            return;
        }
        Cooldown.start(player.getUniqueId(), "bombs", TimeInterval.SECOND, 10);
        PROJ_INSTANCES.put(proj.getUniqueId(), new Pair<>(item, player.getUniqueId()));
    }
}
