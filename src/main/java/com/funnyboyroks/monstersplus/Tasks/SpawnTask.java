package com.funnyboyroks.monstersplus.Tasks;

import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
import com.funnyboyroks.monstersplus.MonstersPlus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class SpawnTask {

    private final int ticks;
    private final MonsterType type;
    private final Location loc;

    private int task;

    public SpawnTask(int ticks, MonsterType type, Location loc) {

        this.ticks = ticks;
        this.type = type;
        this.loc = loc;

        createMonsterDelayed();
    }

    public void createMonsterDelayed() {
        this.task = Bukkit.getScheduler().scheduleSyncDelayedTask(
            MonstersPlus.instance,
            () -> {
                LivingEntity livEnt = type.spawn(loc, CreatureSpawnEvent.SpawnReason.CUSTOM);

                double speed = 0;
                switch (type) {
                    case FIRE_ANT:
                        speed = 1.3;
                        break;
                    case TOXIC_ANT:
                        speed = 1.1;
                        break;
                    case FROST_ANT:
                        speed = 0.9;
                        break;
                }
                livEnt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);

            },
            ticks
        );
    }

    public int getTask() {
        return task;
    }
}
