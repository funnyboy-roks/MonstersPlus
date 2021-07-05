package com.funnyboyroks.monstersplus.Tasks;

import com.funnyboyroks.monstersplus.MonstersPlus;
import com.funnyboyroks.monstersplus.Utils.EntityUtils;
import com.funnyboyroks.monstersplus.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class VortexCreepTask {

    private final int vradius;
    private final LivingEntity livEnt;

    public VortexCreepTask(LivingEntity livEnt, int hradius, int vradius, int pullAmount, int startDelay, int repeatDelay, double pullPower, String message) {
        this.livEnt = livEnt;
        this.vradius = vradius;
        if (message != null) {
            Utils.messagePlayers(message, livEnt.getLocation().getNearbyPlayers(hradius, vradius, hradius));
        }

        for (int i = 0; i < pullAmount; i++) {
            startVortex(i * repeatDelay + startDelay, pullPower, livEnt, hradius, vradius);
        }
    }

    public VortexCreepTask(LivingEntity lent, int hradius, int vradius, int pullAmount, int startDelay, int repeatDelay, double pullPower) {
        this(lent, hradius, vradius, pullAmount, startDelay, repeatDelay, pullPower, null);
    }

    public void startVortex(int delay, double power, LivingEntity le, int hradius, int vradius) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(MonstersPlus.instance, () -> {
            if (!livEnt.isDead()) {
                for (Player player : le.getLocation().getNearbyPlayers(hradius, vradius, hradius)) {
                    EntityUtils.pull(player, le.getLocation(), power);
                }
            }
        }, delay);
    }

    public boolean checkHeightDifference(Location loc1, Location loc2) {
        if (!loc1.getWorld().equals(loc2.getWorld())) {
            return false;
        }

        return Math.abs(loc1.getY() - loc2.getY()) < vradius;
    }
}
