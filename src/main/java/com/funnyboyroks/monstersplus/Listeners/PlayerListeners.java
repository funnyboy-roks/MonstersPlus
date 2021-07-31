package com.funnyboyroks.monstersplus.Listeners;

import com.funnyboyroks.monstersplus.Data.structs.*;
import com.funnyboyroks.monstersplus.MonstersPlus;
import com.funnyboyroks.monstersplus.Utils.EntityUtils;
import com.funnyboyroks.monstersplus.Utils.LangUtils;
import com.funnyboyroks.monstersplus.Utils.TextUtils;
import com.funnyboyroks.monstersplus.Utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

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

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        MonstersPlus.getDataHandler().playerJoin(event.getPlayer().getUniqueId());
    }

    @EventHandler()
    public void onArrowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            MonstersPlus.getCustomItemHandler().playerArrowShoot(event, (Player) event.getEntity());
        }
    }

    @EventHandler()
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntity().getType() == EntityType.ARROW) {
            MonstersPlus.getCustomItemHandler().arrowHit(event);
        }
    }

    @EventHandler()
    public void onPlayerCraft(CraftItemEvent event) {
        if (event.isCancelled() || !(event.getWhoClicked() instanceof Player)) return;

        ItemStack stack = event.getRecipe().getResult();
        CustomItem item = MonstersPlus.getCustomItemHandler().getCustomItem(stack);
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
        OfflineMPPlayer mpp = MonstersPlus.getDataHandler().getOfflinePlayer(player);

        if(!mpp.hasJob(JobType.FISHERMAN)) return;

        int lvl = mpp.getJob(JobType.FISHERMAN).getLevel();

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
        OfflineMPPlayer mpp = MonstersPlus.getDataHandler().getOfflinePlayer(player);

        if(!mpp.hasJob(JobType.ENCHANTER)) return;

//        mpp.getJob(JobType.ENCHANTER).

    }

}
