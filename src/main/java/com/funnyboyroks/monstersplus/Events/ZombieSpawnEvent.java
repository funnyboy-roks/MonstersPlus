package com.funnyboyroks.monstersplus.Events;

import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class ZombieSpawnEvent {

    public final LivingEntity livEnt;
    public final MonsterType monsterType;

    public ZombieSpawnEvent(LivingEntity livEnt) {
        this.livEnt = livEnt;
        monsterType = MonsterType.generateMonster(EntityType.ZOMBIE, livEnt.getLocation().getBlock().getBiome());
    }

}
