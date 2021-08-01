package com.funnyboyroks.monstersplus.Utils;

import java.util.*;

public class Cooldown {

    private static Map<UUID, List<CooldownItem>> COOLDOWN_MAP = new HashMap<>();

    public static CooldownItem start(UUID uuid, String key, long millis) {
        CooldownItem cdi = new CooldownItem(key, millis);
        List<CooldownItem> cds = COOLDOWN_MAP.getOrDefault(uuid, new ArrayList<>());
        cds.add(cdi);
        COOLDOWN_MAP.putIfAbsent(uuid, cds);
        return cdi;
    }

    public static CooldownItem start(UUID uuid, String key, TimeInterval timeInterval, int mult) {
        return start(uuid, key, timeInterval.getToSecondsFactor() * 1000L * mult);
    }

    public static CooldownItem start(UUID uuid, String key, TimeInterval timeInterval) {
        return start(uuid, key, timeInterval.getToSecondsFactor() * 1000L);
    }

    public static List<CooldownItem> getCooldowns(UUID uuid) {
        return COOLDOWN_MAP.getOrDefault(uuid, null);
    }

    public static CooldownItem getCooldown(UUID uuid, String key) {
        List<CooldownItem> cds = getCooldowns(uuid);
        if(cds == null) return null;
        for (CooldownItem cdi : cds) {
            if (cdi.key.equalsIgnoreCase(key)) {
                return cdi;
            }
        }
        return null;

    }

    public static boolean onCooldown(UUID uuid, String key) {
        if (getCooldowns(uuid) != null) {
            List<CooldownItem> cds = getCooldowns(uuid);
            cds.removeIf((ci) -> ci.getTimeRemaining() <= 0);
            if (cds.size() == 0) {
                COOLDOWN_MAP.remove(uuid);
            }
            return false;
        } else {
            return onCooldown(uuid, key);
        }
    }

    public static long getTimeRemaining(UUID uuid, String key) {
        CooldownItem cdi = getCooldown(uuid, key);
        if (cdi == null) return 0;
        return cdi.getTimeRemaining();
    }

    public static String getTimeFormatted(UUID uuid, String key) {
        long time = getTimeRemaining(uuid, key);
        return TimeInterval.formatTime(time, false);
    }


    private static class CooldownItem {

        public final String key;
        public final long endTime;

        public CooldownItem(String key, long time) {

            this.key = key;
            this.endTime = time + System.currentTimeMillis();
        }

        public long getTimeRemaining() {
            return Math.min(0, endTime - System.currentTimeMillis());
        }

    }

}