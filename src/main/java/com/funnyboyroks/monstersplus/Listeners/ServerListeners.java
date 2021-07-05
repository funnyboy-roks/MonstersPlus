package com.funnyboyroks.monstersplus.Listeners;

import com.funnyboyroks.monstersplus.Events.*;
import com.funnyboyroks.monstersplus.Utils.EntityUtils;
import com.funnyboyroks.monstersplus.Utils.Utils;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class ServerListeners implements Listener {

    public static final int MOBSPAWNER_RAD = 25;
    public static final int MOBSPAWNER_MAX = 40;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!Utils.isSpawnableLocation(event.getLocation())) {
            return;
        }

        LivingEntity livEnt = event.getEntity();
        livEnt.setCustomNameVisible(false);

        switch (event.getSpawnReason()) {
            case SPAWNER:
                if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                    if (EntityUtils.nearbyMonsters(event.getLocation(), MOBSPAWNER_RAD).size() > MOBSPAWNER_MAX) {
                        event.setCancelled(true);
                    }
                }
                return;
            case SPAWNER_EGG:
                if (!EntityUtils.nearbyOp(event.getLocation(), 10)) {
                    return;
                }
                break;
            case COMMAND:
            case CUSTOM:
                return;
        }

        if (livEnt instanceof Zombie && !(livEnt instanceof PigZombie)) {
            new ZombieSpawnEvent(livEnt);
        } else if (livEnt instanceof Skeleton) {
            new SkeletonSpawnEvent(livEnt);
        } else if (livEnt instanceof CaveSpider) {
            // ADD IF NEEDED: new CaveSpiderSpawnEvent(livEnt);
        } else if (livEnt instanceof Spider) {
            new SpiderSpawnEvent(livEnt);
        } else if (livEnt instanceof Creeper) {
            new CreeperSpawnEvent(livEnt);
        } else if (livEnt instanceof Horse) {
            new HorseSpawnEvent(livEnt);
        }


    }

}
