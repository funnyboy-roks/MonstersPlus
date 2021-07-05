package com.funnyboyroks.monstersplus.Tasks;

import com.funnyboyroks.monstersplus.MonstersPlus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class PlaceBlockTask {


    private final Material mat;
    private final Location loc;
    private final int delay;
    private int task;

    public PlaceBlockTask(Material mat, Location loc, int delay) {
        this.mat = mat;
        this.loc = loc;
        this.delay = delay;

        run();
    }

    public void run() {
        this.task = Bukkit.getScheduler().scheduleSyncDelayedTask(
            MonstersPlus.instance,
            () -> {
                loc.getBlock().setType(mat);
            },
            delay
        );
    }

    public int getTask() {
        return task;
    }
}
