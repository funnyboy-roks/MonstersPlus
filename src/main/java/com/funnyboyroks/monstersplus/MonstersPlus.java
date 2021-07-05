package com.funnyboyroks.monstersplus;

import com.funnyboyroks.monstersplus.Commands.Monster;
import com.funnyboyroks.monstersplus.Commands.MonsterSpawnCommand;
import com.funnyboyroks.monstersplus.Listeners.ServerListeners;
import com.funnyboyroks.monstersplus.Listeners.MonsterListeners;
import com.funnyboyroks.monstersplus.Listeners.PlayerListeners;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MonstersPlus extends JavaPlugin {

    public static MonstersPlus instance;

    private static final String VERSION = "v0.0.1";

    @Override
    public void onEnable() {

        instance = this;

        registerListeners();
        registerCommands();

        getLogger().log(Level.INFO, "MonstersPlus " + VERSION + " loaded.");

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
}
