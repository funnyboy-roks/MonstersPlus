package com.funnyboyroks.monstersplus.Tasks;

import com.funnyboyroks.monstersplus.MonstersPlus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class CleanBlockTask {


    private final Material fromMat;
    private final Material toMat;
    private final Location loc;
    private final int delay;
    private int task;

    public CleanBlockTask(Material fromMat, Material toMat, Location loc, int delay) {
        this.fromMat = fromMat;
        this.toMat = toMat;
        this.loc = loc;
        this.delay = delay;

        run();
    }

    public void run() {
        this.task = Bukkit.getScheduler().scheduleSyncDelayedTask(
            MonstersPlus.instance,
            () -> {
                if(loc.getBlock().getType().equals(fromMat)) {
                    loc.getBlock().setType(toMat);
                }
            },
            delay
        );
    }

    public int getTask() {
        return task;
    }
}
