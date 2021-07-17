package com.funnyboyroks.monstersplus.Data;

import com.funnyboyroks.monstersplus.Data.structs.PlayerData;
import com.funnyboyroks.monstersplus.Data.structs.PluginData;
import com.funnyboyroks.monstersplus.MonstersPlus;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;
import java.util.logging.Level;

public class DataHandler implements Listener {

    private static final String DATA_FILE_NAME = "data.json";
    private final File dataFile;
    private final MonstersPlus plugin;
    private PluginData pluginData;

    public DataHandler() {
        this.plugin = MonstersPlus.instance;
        this.dataFile = new File(
            plugin.getDataFolder(),
            DATA_FILE_NAME
        );
    }

    public void loadData() {
        Bukkit.getLogger().log(Level.INFO, "Loading plugin data...");
        pluginData = loadJson();
    }

    public void saveData() {
        Bukkit.getLogger().log(Level.INFO, "Saving plugin data...");
        saveJson();
    }

    public static boolean createFile(File file) throws IOException {
        if (file.exists()) {
            return false;
        }
        file = file.getAbsoluteFile();
        file.getParentFile().mkdirs();
        return file.createNewFile();
    }

    public static void writeUTF8(String data, File file) throws IOException {
        if (!file.exists()) {
            createFile(file);
        }
        FileOutputStream fostream = new FileOutputStream(file);
        fostream.write(data.getBytes(StandardCharsets.UTF_8));
        fostream.flush();
        fostream.close();
    }

    public static String readUTF8(File file) throws IOException {
        if (!file.exists())
            return "";
        return new String(
            Files.readAllBytes(file.toPath()),
            StandardCharsets.UTF_8
        );
    }


    public PluginData loadJson() {
        try {
            if (!dataFile.exists()) {
                createFile(dataFile);
                return new PluginData();
            }
            PluginData obj = MonstersPlus.getGson().fromJson(readUTF8(dataFile), PluginData.class);
            return obj == null ? new PluginData() : obj;
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            return new PluginData();
        }
    }

    public void saveJson() {
        try {
            if (!dataFile.exists()) {
                createFile(dataFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // If, for some reason, the data is missing, don't overwrite the file as to not lose any data
        if (pluginData == null) {
            return;
        }

        try {
            writeUTF8(
                MonstersPlus.getGson().toJson(pluginData),
                dataFile
            );
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Unable to save plugin data!");
            e.printStackTrace();
        }

    }

    public PluginData getPluginData() {
        return pluginData;
    }

    public void playerJoin(UUID uuid) {
        pluginData.getPlayerMap().putIfAbsent(uuid, new PlayerData());
    }
}

