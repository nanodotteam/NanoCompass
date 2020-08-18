package pl.nanodot.nanocompass;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class NanoCompass extends JavaPlugin {

	private FileConfiguration pluginConfig;
	public boolean isEnglish = true;
	
	@Override
	public void onEnable() {

		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		pluginConfig = this.getConfig();

		if(this.getConfig().getBoolean("english")){
			isEnglish = true;
		}
		if(!this.getConfig().getBoolean("english")){
			isEnglish = false;
		}
		// If plugin-enable setting in config is true then register event and command
		if(this.getConfig().getBoolean("plugin-enabled")) {
			getServer().getPluginManager().registerEvents(new YawEvent(this), this);
			getServer().getPluginCommand("nanocompass").setExecutor(new ToggleCommand(this));
		}
			getLogger().info("Plugin zostal uruchomiony!");

	}
	
	@Override
	public void onDisable() {
		this.saveConfig();
		getLogger().info("Plugin zostal wylaczony!");
	}
	
	public FileConfiguration getPluginConfig() {
		return pluginConfig;
	}
	
}
