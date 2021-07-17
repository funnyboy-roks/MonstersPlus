package com.funnyboyroks.monstersplus.Commands;

import com.funnyboyroks.monstersplus.Data.structs.CustomItem;
import com.funnyboyroks.monstersplus.MonstersPlus;
import com.funnyboyroks.monstersplus.Utils.ItemUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Monster implements TabCompleter, CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        player.getInventory().addItem(CustomItem.valueOf(args[0].toUpperCase().replaceAll("-", "_")).getItem());
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 1) {
            return Arrays.stream(CustomItem.values()).map(Enum::name).map(String::toLowerCase).map(n -> n.replaceAll("_", "-")).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
