package com.github.nanodot.nanocompass;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class ToggleCommand implements CommandExecutor {
    private NanoCompass plugin;
    private FileConfiguration pluginConfig;
    private FileConfiguration languageConfig = NanoCompass.languageConfig;

    public ToggleCommand(NanoCompass plugin) {
        this.plugin = plugin;
        this.pluginConfig = plugin.getPluginConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("nanocompass")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;

                // Enable player's compass
                if (args[0].equalsIgnoreCase(languageConfig.getString("turn_on"))) {
                    if (pluginConfig.getStringList("disabled").contains(player.getName())) {
                        List<String> playersDisabled = pluginConfig.getStringList("disabled");
                        playersDisabled.remove(player.getName());
                        pluginConfig.set("disabled", playersDisabled);
                        player.sendMessage(ChatColor.GREEN + languageConfig.getString("compass_enabled"));
                    } else {
                        player.sendMessage(ChatColor.RED + languageConfig.getString("already_enabled"));
                    }

                } else if (args[0].equalsIgnoreCase(languageConfig.getString("turn_off"))) {
                    if (!pluginConfig.getStringList("disabled").contains(player.getName())) {

                        List<String> playersDisabled = pluginConfig.getStringList("disabled");
                        playersDisabled.add(player.getName());

                        pluginConfig.set("disabled", playersDisabled);
                        player.sendMessage(ChatColor.RED + languageConfig.getString("compass_disabled"));

                    } else {
                        player.sendMessage(ChatColor.RED + languageConfig.getString("already_disabled"));
                    }

                } else {

                    player.sendMessage(ChatColor.GREEN + languageConfig.getString("use"));
                }
            }
        }
        return false;
    }
}
