package com.funnyboyroks.monstersplus.Tasks;

import org.bukkit.Location;
import org.bukkit.Material;

public class CombinedTasks {

    public static void PlaceAndClean(Material blockType, Location loc, int placeDelay, int cleanDelay) {
        new PlaceBlockTask(blockType, loc, placeDelay);
        new CleanBlockTask(blockType, Material.AIR, loc, cleanDelay);
    }

}
