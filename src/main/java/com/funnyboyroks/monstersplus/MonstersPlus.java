package com.funnyboyroks.monstersplus;

import com.funnyboyroks.monstersplus.Commands.Monster;
import com.funnyboyroks.monstersplus.Commands.MonsterSpawnCommand;
import com.funnyboyroks.monstersplus.Data.DataHandler;
import com.funnyboyroks.monstersplus.Data.structs.CustomItem;
import com.funnyboyroks.monstersplus.Data.structs.PluginData;
import com.funnyboyroks.monstersplus.Listeners.MonsterListeners;
import com.funnyboyroks.monstersplus.Listeners.PlayerListeners;
import com.funnyboyroks.monstersplus.Listeners.ServerListeners;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MonstersPlus extends JavaPlugin {

    public static MonstersPlus instance;

    private static JobHandler jobHandler;
    private static DataHandler dataHandler;
    private static CustomItemHandler customItemHandler;
    private static Gson gson;

    private static PluginData pluginData;

    private static final String VERSION = "v0.0.1";

    public MonstersPlus() {
        instance = this;
        dataHandler = new DataHandler();
        jobHandler = new JobHandler();
        customItemHandler = new CustomItemHandler();
        gson = new Gson();
        pluginData = dataHandler.getPluginData();
    }

    @Override
    public void onEnable() {

        registerListeners();
        registerCommands();

        getLogger().log(Level.INFO, "MonstersPlus " + VERSION + " loaded.");

        CustomItem.registerRecipes();

        dataHandler.loadData();


    }

    public void registerCommands() {

        Server sv = Bukkit.getServer();
        sv.getPluginCommand("monster").setExecutor(new Monster());
        sv.getPluginCommand("customspawn").setExecutor(new MonsterSpawnCommand());

    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ServerListeners(), instance);
        Bukkit.getPluginManager().registerEvents(new MonsterListeners(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), instance);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JobHandler getJobHandler() {
        return jobHandler;
    }

    public static Gson getGson() {
        return gson;
    }

    public static DataHandler getDataHandler() {
        return dataHandler;
    }

    public static PluginData getPluginData() {
        return pluginData;
    }

    public static CustomItemHandler getCustomItemHandler() {
        return customItemHandler;
    }
}
