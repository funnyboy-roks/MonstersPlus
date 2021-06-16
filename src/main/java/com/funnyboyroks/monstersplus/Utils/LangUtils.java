package com.funnyboyroks.monstersplus.Utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LangUtils {

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

    public static void sendMessage(Player sender, String message) {
        sender.sendMessage(message);
    }

}
