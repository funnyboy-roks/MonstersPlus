package com.funnyboyroks.monstersplus.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class Utils {


    public static boolean isSpawnableLocation(Location loc) {
        if(!loc.getWorld().getName().equalsIgnoreCase("world")) { // Not in 'world'
            return false;
        }

        // TODO: WorldGuard Integration

        // TODO: Factions Integration

        return true;
    }

    public static boolean randomBool() {
        return randomBool(0.5);
    }

    public static boolean randomBool(double chance) {
         return Math.random() < chance;
    }

}
