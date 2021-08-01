package com.funnyboyroks.monstersplus.Utils;

import com.funnyboyroks.monstersplus.MonstersPlus;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.association.RegionAssociable;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static final String FULL_BLOCK = "\u2588";
    public static final String HALF_BLOCK = "\u258C";


    public static boolean isSpawnableLocation(Location loc) {
        if (!loc.getWorld().getName().equalsIgnoreCase("world")) { // Not in 'world'
            return false;
        }

        // TODO: WorldGuard Integration

        // TODO: Factions Integration

        return true;
    }

    public static boolean isBuildLocation(Player player, Location loc) {
        if (!loc.getWorld().getName().equalsIgnoreCase("world")) {
            return false;
        }

        if (player.isOp()) {
            return true;
        }

        // TODO: WorldGuard Integration

        // TODO: Factions Integration

        return true;
    }

    public static boolean isPvpLocation(Location loc) {
        // TODO: WorldGuard Integration

        // TODO: Factions Integration

        return true;
    }

    public static boolean isPearlAllowed(Player shooter, Location loc) {
        if(shooter.isOp()) return true;

        // TODO: WorldGuard Integration

        return true;
    }

    public static boolean randomBool() {
        return randomBool(0.5);
    }

    public static boolean randomBool(double chance) {
        return Math.random() < chance;
    }

    public static void lightning(Location loc) {
        loc.getWorld().strikeLightning(loc);
    }

    public static void lightning(Location loc, double chance) {
        if (randomBool(chance)) {
            loc.getWorld().strikeLightning(loc);
        }
    }

    public static List<String> filterEnum(Enum[] enums, String v) {
        return Arrays.stream(enums)
            .map(Enum::name)
            .map(String::toLowerCase)
            .map(s -> s.replaceAll("_", "-"))
            .filter(s -> s.startsWith(
                v
                    .toLowerCase()
                    .replaceAll("_", "-")
                )
            )
            .collect(Collectors.toList());
    }

    public static int randomInt(int range, int offset) {
        return (int) (Math.random() * range) + offset;
    }

    public static int randomInt(int range) {
        return randomInt(range, 0);
    }


    public static void messagePlayers(String msg, Collection<Player> nearbyPlayers) {
        nearbyPlayers.forEach(
            p -> LangUtils.sendMessage(p, msg)
        );
    }

    public static <T> T choseRandom(T[] array) {
        return array[randomInt(array.length)];
    }

    public static <T> T choseRandom(List<T> list) {
        return list.get(randomInt(list.size()));
    }

    public static boolean isFlagAllowed(Location loc, StateFlag flag, Player player) {
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        RegionManager rm = MonstersPlus.worldGuard.getRegionManager(loc.getWorld());
        RegionContainer container = WorldGuardPlugin.inst().getRegionContainer();
        return container.createQuery().testState(loc, localPlayer, flag);
    }
}
