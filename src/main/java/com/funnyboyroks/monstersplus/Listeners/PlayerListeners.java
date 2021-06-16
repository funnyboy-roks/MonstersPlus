package com.funnyboyroks.monstersplus.Listeners;

import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
import com.funnyboyroks.monstersplus.Utils.EntityUtils;
import com.funnyboyroks.monstersplus.Utils.LangUtils;
import com.funnyboyroks.monstersplus.Utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
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

                    Zombie zombie = (Zombie) livEnt;

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

                    EntityUtils.setEquipmentDropChances(
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

}
