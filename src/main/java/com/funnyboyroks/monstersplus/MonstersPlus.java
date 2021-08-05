package com.funnyboyroks.monstersplus;

import com.funnyboyroks.monstersplus.Commands.CommandJobEnchant;
import com.funnyboyroks.monstersplus.Commands.Monster;
import com.funnyboyroks.monstersplus.Commands.CommandCustomSpawn;
import com.funnyboyroks.monstersplus.Data.structs.CustomItem;
import com.funnyboyroks.monstersplus.Listeners.MonsterListeners;
import com.funnyboyroks.monstersplus.Listeners.PlayerListeners;
import com.funnyboyroks.monstersplus.Listeners.ServerListeners;
import com.google.gson.Gson;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MonstersPlus extends JavaPlugin {

    public static MonstersPlus instance;
    private static Gson gson;
    public static WorldGuardPlugin worldGuard;
    public static CoreProtectAPI coreProtect;

    private static final String VERSION = "v0.0.1";

    public MonstersPlus() {
        instance = this;
        gson = new Gson();
    }

    @Override
    public void onEnable() {

        registerListeners();
        registerCommands();

        registerIntegrations();

        getLogger().log(Level.INFO, "MonstersPlus " + VERSION + " loaded.");

        CustomItem.registerRecipes();

    }

    public void registerCommands() {

        Server sv = Bukkit.getServer();
        sv.getPluginCommand("monster").setExecutor(new Monster());
        sv.getPluginCommand("jobenchant").setExecutor(new CommandJobEnchant());
        sv.getPluginCommand("customspawn").setExecutor(new CommandCustomSpawn());

    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ServerListeners(), instance);
        Bukkit.getPluginManager().registerEvents(new MonsterListeners(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), instance);
    }

    public void registerIntegrations() {
        worldGuard = getWordGuard();
        coreProtect = getCoreProtect();
    }

    private WorldGuardPlugin getWordGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        if(plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

    private CoreProtectAPI getCoreProtect() {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

        if (plugin == null || !(plugin instanceof CoreProtect)) {
            return null;
        }

        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (!CoreProtect.isEnabled() || CoreProtect.APIVersion() < 2) {
            return null;
        }

        return CoreProtect;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Gson getGson() {
        return gson;
    }
}
