package com.funnyboyroks.monstersplus.Commands;

import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
import com.funnyboyroks.monstersplus.Events.*;
import com.funnyboyroks.monstersplus.Utils.LangUtils;
import com.funnyboyroks.monstersplus.Utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MonsterSpawnCommand implements TabCompleter, CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player) && args.length < 4) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        // Player player = (Player)sender;
        if (!sender.isOp() && !sender.hasPermission("monsersplus.customspawn")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        } else if (args.length < 1) {
            return false;
        } else {
            int amount = 1;
            double percentage = 100;

            try {
                if (args.length > 1) {
                    amount = Integer.parseInt(args[1]);
                }
                if (args.length > 2) {
                    percentage = Double.parseDouble(args[2]);
                }
            } catch (NumberFormatException nfe) {
                // player.sendMessage(ChatColor.RED + "Invalid numbers
                // specified for amount or health percentage.");
                // return;
            }

            // Spawn the monster based on type.
            Location loc = null;
            if (sender instanceof Player) {
                loc = ((Player) sender).getLocation();
            }
            if (args.length > 3) {
                try {
                    Player player2 = Bukkit.getPlayer(args[3]);
                    if (player2 != null) {
                        loc = player2.getLocation();
                    } else {
                        String[] commas = args[3].split(",");
                        if (commas.length == 4) {
                            World world = Bukkit.getWorld(commas[0]);
                            if (world != null) {
                                loc = new Location(world, Double.parseDouble(commas[1]),
                                    Double.parseDouble(commas[2]), Double.parseDouble(commas[3]));
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
                if (loc == null) {
                    return false;
                }
            }
            loc = loc.add(0, 3, 0);
            MonsterType type;
            try {
                type = MonsterType.valueOf(args[0].toUpperCase().replaceAll("-", "_"));
            } catch (IllegalArgumentException e) {
                LangUtils.sendMessage(sender, ChatColor.RED + "Invalid monster name.");
                return true;
            }

            for (int i = 0; i < amount; i++) {
                LivingEntity lent = spawnCustomMonster(type, loc);
                lent.setHealth(lent.getHealth() * (percentage / 100));
            }

            sender.sendMessage(ChatColor.AQUA + "Monster successfully spawned.");
        }
        return true;
    }

    public static LivingEntity spawnCustomMonster(MonsterType type, Location loc) {
        LivingEntity livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, type.baseEntity, CreatureSpawnEvent.SpawnReason.COMMAND);

        switch (type.baseEntity) {
            case SKELETON:
                new SkeletonSpawnEvent(livEnt, type);
                break;
            case ZOMBIE:
                new ZombieSpawnEvent(livEnt, type);
                break;
            case CREEPER:
                new CreeperSpawnEvent(livEnt, type);
                break;
            case HORSE:
                new HorseSpawnEvent(livEnt, type);
                break;
            case SPIDER:
                new SpiderSpawnEvent(livEnt, type);
                break;
            default:
                livEnt.remove();
                livEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, type.baseEntity);
        }
        return livEnt;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        switch (args.length) {
            case 1:
                return Utils.filterEnum(MonsterType.values(), args[0]);
            case 2:
                return Collections.singletonList("[amount]");
            case 3:
                return Collections.singletonList("[percentage]");
            case 4:
                return Bukkit
                    .getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .filter(n ->
                        n.toLowerCase()
                            .startsWith(args[3].toLowerCase())
                    )
                    .collect(Collectors.toList());
        }
        return null;
    }
}
