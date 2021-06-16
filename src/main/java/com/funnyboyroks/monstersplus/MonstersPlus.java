package com.funnyboyroks.monstersplus;

import com.funnyboyroks.monstersplus.Listeners.MainListeners;
import com.funnyboyroks.monstersplus.Listeners.MonsterListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MonstersPlus extends JavaPlugin {

    public static MonstersPlus instance;

    private static final String VERSION = "v0.0.1";

    @Override
    public void onEnable() {

        instance = this;

        registerCommands();

        getLogger().log(Level.INFO, "MonstersPlus Version " + VERSION + " loaded.");

    }

    public void registerCommands() {

    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new MainListeners(), instance);
        Bukkit.getPluginManager().registerEvents(new MonsterListeners(), instance);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
