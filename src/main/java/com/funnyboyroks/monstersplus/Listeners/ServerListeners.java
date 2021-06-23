package com.funnyboyroks.monstersplus.Listeners;

import com.funnyboyroks.monstersplus.Events.ZombieSpawnEvent;
import com.funnyboyroks.monstersplus.Utils.EntityUtils;
import com.funnyboyroks.monstersplus.Utils.Utils;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class ServerListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if(!Utils.isSpawnableLocation(event.getLocation())) {
            return;
        }

        LivingEntity livEnt = event.getEntity();

        switch(event.getSpawnReason()) {
            case SPAWNER:
                return;
            case SPAWNER_EGG:
                if(!EntityUtils.nearbyOp(event.getLocation(), 10)) {
                    return;
                }
                break;
        }

        if (livEnt instanceof Zombie && !(livEnt instanceof PigZombie)) {
            new ZombieSpawnEvent(livEnt);
        } else if (livEnt instanceof Skeleton) {
            // TODO: `new SkeletonSpawnEvent(livEnt);`
        } else if (livEnt instanceof CaveSpider) {
            // TODO: `new CaveSpiderSpawnEvent(livEnt);`
        } else if (livEnt instanceof Spider) {
            // TODO: `new SpiderSpawnEvent(livEnt);`
        } else if (livEnt instanceof Creeper) {
            // TODO: `new CreeperSpawnEvent(livEnt);`
        } else if (livEnt instanceof Horse) {
            // TODO: `new HorseSpawnEvent(livEnt);`
        }


    }

}
