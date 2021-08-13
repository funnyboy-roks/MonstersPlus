package com.funnyboyroks.monstersplus.Listeners;

import com.funnyboyroks.monstersplus.CustomItemHandler;
import com.funnyboyroks.monstersplus.Data.structs.CustomItem;
import com.funnyboyroks.monstersplus.Data.structs.FishingItems;
import com.funnyboyroks.monstersplus.Data.structs.JobType;
import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
import com.funnyboyroks.monstersplus.Jobs.WitchDoctor.BrewHandler;
import com.funnyboyroks.monstersplus.MonstersPlus;
import com.funnyboyroks.monstersplus.Tasks.PlaceBlockTask;
import com.funnyboyroks.monstersplus.Utils.*;
import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobsPlayer;
import me.libraryaddict.disguise.DisguiseAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class PlayerListeners implements Listener {

    /**
     * Handles the following monster spawning:
     * <ul>
     *     <li>Fire Ant</li>
     *     <li>Toxic Ant</li>
     *     <li>Frost Ant</li>
     *     <li>Iron Guardian</li>
     * </ul>
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {

        Location loc = event.getBlock().getLocation();
        Player player = event.getPlayer();

        if (
            !Utils.isSpawnableLocation(loc) || // Not spawnable loc
                player.getGameMode() == GameMode.CREATIVE || // Player in creative
                event.isCancelled()
        ) {
            return;
        }


        switch (event.getBlock().getType()) {
            case DIRT:
            case GRASS:
                if (Utils.randomBool(0.0015)) { // 0.15% Chance
                    MonsterType.randomSpawn(MonsterType.FIRE_ANT, loc, 8, 0.5);
                    LangUtils.sendMessage(player, ChatColor.GOLD + "You destroyed a Fire Ant nest!");
                }
                break;
            case STONE:
                if (Utils.randomBool(0.0015)) { // 0.15% Chance
                    MonsterType.randomSpawn(MonsterType.TOXIC_ANT, loc, 8, 0.5);
                    LangUtils.sendMessage(player, ChatColor.GOLD + "You destroyed a Toxic Ant nest!");
                }
                break;
            case SNOW_BLOCK:
            case ICE:
                if (Utils.randomBool(0.0015)) { // 0.15% Chance
                    MonsterType.randomSpawn(MonsterType.FROST_ANT, loc, 8, 0.5);
                    LangUtils.sendMessage(player, ChatColor.GOLD + "You destroyed a Frost Ant nest!");
                }
                break;
            case IRON_ORE:
                if (Utils.randomBool(0.012)) { // 1.2% Chance
                    LivingEntity livEnt = MonsterType.IRON_GUARDIAN.spawn(loc, CreatureSpawnEvent.SpawnReason.CUSTOM);

                    EntityUtils.addLongPotion(livEnt, PotionEffectType.INVISIBILITY, 1);
                    EntityUtils.addLongPotion(livEnt, PotionEffectType.SPEED, 1);

                    EntityUtils.setEquipment(
                        livEnt,
                        null,
                        null,
                        Material.IRON_ORE,
                        null,
                        null,
                        null
                    );

                    EntityUtils.setDropChances(
                        livEnt,
                        0,
                        0,
                        1,
                        0,
                        0,
                        0
                    );

                    LangUtils.sendMessage(player, ChatColor.GOLD + "An Iron Guardian appears");

                }
                break;
        }
    }

    @EventHandler()
    public void onArrowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            CustomItemHandler.playerArrowShoot(event, (Player) event.getEntity());
        }
    }

    @EventHandler()
    public void onArrowHit(ProjectileHitEvent event) {
        switch(event.getEntity().getType()) {
            case ARROW:
                CustomItemHandler.arrowHit(event);
                break;
            case EGG:
                CustomItemHandler.eggHit(event);
                break;
        }
    }

    @EventHandler()
    public void onPlayerCraft(CraftItemEvent event) {
        if (event.isCancelled() || !(event.getWhoClicked() instanceof Player)) return;

        ItemStack stack = event.getRecipe().getResult();
        CustomItem item = CustomItemHandler.getCustomItem(stack);
        Player player = (Player) event.getWhoClicked();

        if (item == null || player.isOp()) return;

        if (!item.canUse(player)) {
            TextUtils.sendFormatted(
                player,
                "&(blue)Job: {&(white)%0} Required Level: {&(white)%1}",
                item.getJob(),
                item.getLevel()
            );
            event.setCancelled(true);
            return;
        }

        int powerstones = EntityUtils.powerstoneCount(player);
        if (powerstones < item.getPowerstones()) {
            TextUtils.sendFormatted(
                player,
                "&(red)This enchantment costs {&(gold)%0} powerstone%1, you only have {&(gold)%2}.",
                item.getPowerstones(),
                item.getPowerstones() == 1 ? "" : "s",
                powerstones
            );
            return;
        }

        EntityUtils.removePowerstones(player, item.getPowerstones());

        player.updateInventory();

    }

    @EventHandler()
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.isCancelled()) return;

        PlayerFishEvent.State state = event.getState();
        Entity caught = event.getCaught();
        Player player = event.getPlayer();
        JobsPlayer jp = PlayerUtils.getJP(player);
        Job job = Jobs.getJob(JobType.FISHERMAN.name);

        if(!PlayerUtils.hasJob(jp, JobType.FISHERMAN)) return;

        int lvl = jp.getJobProgression(job).getLevel();

        if (
            state == PlayerFishEvent.State.CAUGHT_FISH &&
            (caught instanceof Item)
        ) {
            Item item = (Item) caught;
            item.setItemStack(FishingItems.getFishedItem(lvl));
        }

    }

    @EventHandler()
    public void onPlayerEnchant(EnchantItemEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getEnchanter();
        JobsPlayer jp = PlayerUtils.getJP(player);

        if(!PlayerUtils.hasJob(jp, JobType.ENCHANTER)) return;

        PlayerUtils.addJobXp(player.getUniqueId(), JobType.ENCHANTER, event.getExpLevelCost() * 12.5);

    }

    @EventHandler()
    public void onRepair(InventoryClickEvent event) {
        if (event.isCancelled()) return;

        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        Inventory inv = event.getInventory();
        int slot = event.getSlot();

        if (inv instanceof AnvilInventory && slot == 2) {
            int prevLvl = player.getLevel();
            Bukkit.getScheduler().runTaskLater(
                MonstersPlus.instance,
                () -> {
                    int diff = prevLvl - player.getLevel();
                    List.of(JobType.ENCHANTER, JobType.BLACKSMITH)
                        .forEach(
                            (j) ->
                                PlayerUtils.addJobXp(player.getUniqueId(), j, diff * 15)
                        );
                },
                1L
            );
        }
    }

    @EventHandler()
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(
            event.useInteractedBlock() == Event.Result.DENY || event.useItemInHand() == Event.Result.DENY ||
                !event.hasBlock() ||
                event.getAction() != Action.RIGHT_CLICK_BLOCK
        ) return;

        Player player = event.getPlayer();
        ItemStack hand = event.getItem();
        Block block = event.getClickedBlock();
        World world = block.getWorld();
        ExperienceManager em = new ExperienceManager(player);

        switch(event.getAction()) {
            case RIGHT_CLICK_BLOCK: {

                switch(hand.getType()) {
                    case FLINT_AND_STEEL: {
                        if (!world.getName().toLowerCase().contains("end")) return;

                        if (block.getType() != Material.OBSIDIAN) return;
                        if (!PlayerUtils.hasJob(player, JobType.BUILDER) || PlayerUtils.getJobLvl(player, JobType.BUILDER) < 7) {
                            TextUtils.sendFormatted(
                                player,
                                "&(red)You must be at least a level {&(gold)%0 builder} to spawn an EnderDragon",
                                7
                            );
                            return;
                        }

                        int xd = 0;
                        int zd = 0;
                        if (block.getRelative(1, 0, 0).getType() == Material.OBSIDIAN) {
                            xd = 1;
                        } else if (block.getRelative(-1, 0, 0).getType() == Material.OBSIDIAN) {
                            xd = -1;
                        } else if (block.getRelative(0, 0, 1).getType() == Material.OBSIDIAN) {
                            zd = 1;
                        } else if (block.getRelative(0, 0, -1).getType() == Material.OBSIDIAN) {
                            zd = -1;
                        } else {
                            return;
                        }

                        int count = 0;
                        for (int i = 0; i < 5; ++i) {
                            for (int j = 0; j < 3; ++j) {
                                if (block.getRelative(xd * j, i, zd * j).getType() == Material.OBSIDIAN) {
                                    ++count;
                                }
                            }
                        }
                        if (count != 11) return;

                        if (Cooldown.onCooldown(player.getUniqueId(), "enderspawning") && !player.isOp()) {
                            TextUtils.sendFormatted(
                                player,
                                "&(gold)You can use this in {&(red)%0}.",
                                Cooldown.getTimeFormatted(player.getUniqueId(), "enderspawning")
                            );
                            return;
                        }

                        Cooldown.start(player.getUniqueId(), "enderspawning", TimeInterval.HOUR);

                        Block[] airToFire = new Block[]{
                            block.getRelative(xd, 1, zd),
                            block.getRelative(xd * 2, 1, zd * 2),
                            block.getRelative(xd, 3, zd),
                            block.getRelative(xd * 2, 3, zd * 2),
                            };

                        for (Block b : airToFire) {
                            if (b.isEmpty()) {
                                b.setType(Material.FIRE);
                            }
                        }

                        for (int i = 1; i < 4; ++i) {
                            Bukkit.getScheduler().runTaskLater(
                                MonstersPlus.instance,
                                () -> airToFire[2].getLocation().createExplosion(2F),
                                20 * i
                            );
                        }

                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 3; j++) {
                                Block b = block.getRelative(xd * j, i, zd * j);
                                new PlaceBlockTask(Material.AIR, b.getLocation(), 80);
                            }
                        }

                        Bukkit.getScheduler().runTaskLater(
                            MonstersPlus.instance,
                            () ->
                                world
                                    .spawnEntity(
                                        block
                                            .getRelative(0, 10, 0)
                                            .getLocation(),
                                        EntityType.ENDER_DRAGON
                                    ),
                            80
                        );
                        return;
                    }
                    case GLASS_BOTTLE:
                    case BUCKET: {
                        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                            return;
                        }
                        CustomItem item = CustomItemHandler.getCustomItem(hand);
                        if (item == null) return;
                        switch (item) {
                            case XP_BOTTLE:
                                em.changeExp(100);
                                TextUtils.sendFormatted(player, "&(aqua)Received 100 EXP.");
                                break;
                            case XP_BUCKET:
                                em.changeExp(1000);
                                TextUtils.sendFormatted(player, "&(aqua)Received 1000 EXP.");
                                break;
                            default:
                                return;
                        }
                        event.setCancelled(true);
                        ItemUtils.changeAmount(hand, -1);
                        return;
                    }
                }

                switch(block.getType()) {
                    case ENCHANTING_TABLE: {
                        if (hand.getType() != Material.BUCKET && hand.getType() != Material.GLASS_BOTTLE) return;

                        event.setCancelled(true);

                        if(CustomItemHandler.isCustomItem(hand)) {
                            TextUtils.sendFormatted(player, "&(red)Use a normal bucket or glass bottle to store your exp.");
                            return;
                        }
                        switch (hand.getType()) {
                            case GLASS_BOTTLE: {
                                if(em.getCurrentExp() >= 100) {
                                    ItemStack stack = CustomItem.XP_BOTTLE.getItem();
                                    ItemUtils.setItemStackLore(stack, ChatColor.GRAY + "100 Experience", "&9MonstersPlus");
                                    world.dropItemNaturally(block.getLocation(), stack);
                                    em.changeExp(-100);
                                } else {
                                    TextUtils.sendFormatted(
                                        player,
                                        "&(red)You must have 100 EXP to create 1 %0",
                                        CustomItem.XP_BOTTLE.getName()
                                    );
                                }
                            }
                            break;
                            case BUCKET: {
                                if (em.getCurrentExp() >= 1000) {
                                    ItemStack stack = CustomItem.XP_BUCKET.getItem();
                                    ItemUtils.setItemStackLore(stack, ChatColor.GRAY + "1000 experience", "&9MonstersPlus");
                                    world.dropItemNaturally(block.getLocation(), stack);
                                    em.changeExp(-1000);
                                } else {
                                    TextUtils.sendFormatted(player, "&(red)You must have 1000 EXP to create 1 %0", CustomItem.XP_BUCKET.getName());
                                    return;
                                }
                            }
                            break;
                        }
                        ItemUtils.changeAmount(hand, -1);
                        return;
                    }
                    case WATER_CAULDRON:
                    case CAULDRON: {
                        BrewHandler.rightClickCauldron(event);
                    }
                }
            }
        }

    }

    @EventHandler()
    public void onMobSpawn(CreatureSpawnEvent event) {
        if(event.isCancelled()) return;

        LivingEntity livEnt = event.getEntity();

        if(livEnt instanceof EnderDragon && !event.getLocation().getWorld().getName().toLowerCase().contains("end")) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        if(event.isCancelled()) return;

        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player target = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();

            if (DisguiseAPI.isDisguised(target)) {
                TextUtils.sendFormatted(
                    target,
                    "&(red)Your disguise has been blown due to PvP."
                );
                DisguiseAPI.undisguiseToAll(target);
            }
            if (DisguiseAPI.isDisguised(damager)) {
                TextUtils.sendFormatted(
                    damager,
                    "&(red)Your disguise has been blown due to PvP."
                );
                DisguiseAPI.undisguiseToAll(damager);
            }
        }
    }

}
