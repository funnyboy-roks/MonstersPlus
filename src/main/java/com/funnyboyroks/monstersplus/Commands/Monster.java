package com.funnyboyroks.monstersplus.Commands;

import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
import com.funnyboyroks.monstersplus.Utils.LangUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Monster implements TabCompleter, CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            LangUtils.sendMessage(sender, ChatColor.RED + "You must be ingame to use this command.");
            return true;
        }
        try {
            MonsterType
                .valueOf(args[0].toUpperCase().replaceAll("-", "_"))
                .spawn(((Player) sender).getLocation(), CreatureSpawnEvent.SpawnReason.COMMAND);

        } catch(IllegalArgumentException e) {
            return false;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Arrays.stream(MonsterType.values())
            .map(Enum::name)
            .map(s ->
                s.toLowerCase()
                    .replaceAll("_", "-")
            )
            .collect(Collectors.toList());
    }
}
