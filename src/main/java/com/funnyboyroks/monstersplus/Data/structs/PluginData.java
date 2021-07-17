package com.funnyboyroks.monstersplus.Data.structs;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PluginData {

    private final Map<UUID, PlayerData> playerMap;

    public PluginData() {
        playerMap = new HashMap<>();
    }

    public Map<UUID, PlayerData> getPlayerMap() {
        return playerMap;
    }

    public PlayerData getPlayerData(UUID uuid) {
        return playerMap.get(uuid);
    }

}
