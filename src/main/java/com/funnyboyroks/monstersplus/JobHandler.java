package com.funnyboyroks.monstersplus;

import com.funnyboyroks.monstersplus.Data.DataHandler;
import com.funnyboyroks.monstersplus.Data.structs.JobType;
import com.funnyboyroks.monstersplus.Data.structs.PlayerJob;
import com.funnyboyroks.monstersplus.Data.structs.PluginData;
import org.bukkit.entity.Player;

public class JobHandler {

    private final MonstersPlus plugin;
    private final DataHandler dh;
    private final PluginData pluginData;

    public JobHandler() {
        this.plugin = MonstersPlus.instance;
        this.dh = MonstersPlus.getDataHandler();
        this.pluginData = dh.getPluginData();
    }


}
