package com.github.nanodot.nanocompass;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class NanoCompass extends JavaPlugin {

    private FileConfiguration pluginConfig;
    public static FileConfiguration languageConfig;
    private final String[] neededTexts = {"load_message", "turn_on", "turn_off", "use", "compass_disabled",
    "compass_enabled", "already_enabled", "already_disabled"};

    @Override
    public void onEnable() {

        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        pluginConfig = this.getConfig();

        String languageCode = pluginConfig.getString("language");
        readLanguageConfig(languageCode);

        for(String neededText : neededTexts) {
            if(languageConfig.getString(neededText) == null) {
                getLogger().warning("No translation " + neededText);
                getLogger().warning("Disabling plugin!");
                getPluginLoader().disablePlugin(this);
                return;
            }
        }

        getServer().getPluginManager().registerEvents(new YawEvent(this), this);
        getServer().getPluginCommand("nanocompass").setExecutor(new ToggleCommand(this));
        getLogger().info(languageConfig.getString("load_message"));
    }

    @Override
    public void onDisable() {
        this.saveConfig();
        getLogger().info("Plugin zostal wylaczony!");
    }

    private void readLanguageConfig(String languageCode) {
        File languageFile = new File(getDataFolder(), languageCode + ".yml");
        if(!languageFile.exists()) {
            languageFile.getParentFile().mkdirs();
            saveResource(languageCode + ".yml", false);
        }
        FileConfiguration languageConfig = new YamlConfiguration();
        try {
            languageConfig.load(languageFile);
            this.languageConfig = languageConfig;
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        this.languageConfig = languageConfig;
    }

    public FileConfiguration getPluginConfig() {
        return pluginConfig;
    }
}
