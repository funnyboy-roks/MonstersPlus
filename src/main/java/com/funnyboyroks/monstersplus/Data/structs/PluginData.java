package com.funnyboyroks.monstersplus.Data.structs;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PluginData {

    private final Map<UUID, OfflineMPPlayer> playerMap;

    public PluginData() {
        playerMap = new HashMap<>();
    }

    public Map<UUID, OfflineMPPlayer> getPlayerMap() {
        return playerMap;
    }

    public OfflineMPPlayer getPlayerData(UUID uuid) {
        return playerMap.get(uuid);
    }

}
