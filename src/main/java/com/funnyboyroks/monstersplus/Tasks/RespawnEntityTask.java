package com.funnyboyroks.monstersplus.Tasks;

import com.funnyboyroks.monstersplus.MonstersPlus;
import com.funnyboyroks.monstersplus.Utils.EntityUtils;
import com.funnyboyroks.monstersplus.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnEntityTask extends BukkitRunnable {

    private LivingEntity livEnt;
    private double health;
    private String msg;
    private int delay;

    public RespawnEntityTask(LivingEntity livEnt, double health, String msg, int delay) {
        this.livEnt = livEnt;
        this.health = health;
        this.msg = msg;
        this.delay = delay;
    }

    @Override
    public void run() {
        if (!EntityUtils.isTriggered(livEnt)) {
            EntityUtils.setTriggered(livEnt, true);
            Utils.messagePlayers(msg, livEnt.getLocation().getNearbyPlayers(12));
            respawnEntity(delay, livEnt, health);
        }
    }

    public void respawnEntity(int delay, LivingEntity le, double health) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(MonstersPlus.instance, () -> {
            if (livEnt.isDead()) {
                Location loc = livEnt.getLocation();
                LivingEntity newLivEnt = (LivingEntity) loc.getWorld().spawnEntity(loc, livEnt.getType());

                EntityUtils.setTriggered(newLivEnt, true);
                EntityUtils.setMaxHealth(newLivEnt, health);
                EntityUtils.heal(newLivEnt);

                newLivEnt.setCustomName(livEnt.getCustomName());
                newLivEnt.setCustomNameVisible(true);
                newLivEnt.addPotionEffects(livEnt.getActivePotionEffects());

                EntityUtils.setEquipment(
                    newLivEnt,
                    livEnt
                );

            }
        }, delay);
    }
}
