package com.funnyboyroks.monstersplus.Listeners;

import com.funnyboyroks.monstersplus.Data.structs.MonsterType;
import com.funnyboyroks.monstersplus.Utils.MonsterUpdater;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

public class MonsterListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        boolean isProjectile = false;

        if (event.isCancelled()) {
            return;
        }

        Entity damager = event.getDamager();
        Entity target = event.getEntity();

        if (damager == target) {
            event.setCancelled(true);
            return;
        }
        if (damager instanceof Projectile) {
            damager = (LivingEntity) (((Projectile) damager).getShooter());
            isProjectile = true;
        }

        if (damager instanceof LivingEntity && target instanceof LivingEntity) {
            LivingEntity livEntDmg = (LivingEntity) damager;
            LivingEntity livEntTgt = (LivingEntity) target;

            MonsterUpdater.update(livEntDmg, livEntTgt, isProjectile);
            MonsterType.updateMonsterMetaName(livEntDmg);
            MonsterType.updateMonsterMetaName(livEntTgt);

        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityTarget(EntityTargetLivingEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getEntity() instanceof LivingEntity) {
            if (event.getTarget() instanceof Player) {
                MonsterUpdater.update(event);
                MonsterType.updateMonsterMetaName(((LivingEntity) event.getEntity()));
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreeperExplode(EntityExplodeEvent event) {
        MonsterUpdater.update(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDeath(EntityDeathEvent event) {
        MonsterUpdater.update(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileFire(ProjectileLaunchEvent event) {
        MonsterUpdater.update(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onTame(EntityTameEvent event) {
        if (event.isCancelled()) {
            return;
        }

        event.setCancelled(event.isCancelled() || MonsterType.isMonster(event.getEntity()));
    }
}
