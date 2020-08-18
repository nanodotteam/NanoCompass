package com.github.nanodot.nanocompass;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

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

        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length != 1) {
                sender.sendMessage(colour(languageConfig.getString("use")));
                return true;
            }

            // Enable player's compass
            if (args[0].equalsIgnoreCase(languageConfig.getString("turn_on"))) {
                if (pluginConfig.getStringList("disabled").contains(player.getName())) {
                    List<String> playersDisabled = pluginConfig.getStringList("disabled");
                    playersDisabled.remove(player.getName());
                    pluginConfig.set("disabled", playersDisabled);
                    player.sendMessage(colour(languageConfig.getString("compass_enabled")));
                } else {
                    player.sendMessage(colour(languageConfig.getString("already_enabled")));
                }

            } else if (args[0].equalsIgnoreCase(languageConfig.getString("turn_off"))) {
                if (!pluginConfig.getStringList("disabled").contains(player.getName())) {

                    List<String> playersDisabled = pluginConfig.getStringList("disabled");
                    playersDisabled.add(player.getName());

                    pluginConfig.set("disabled", playersDisabled);
                    player.sendMessage(colour(languageConfig.getString("compass_disabled")));

                } else {
                    player.sendMessage(colour(languageConfig.getString("already_disabled")));
                }

            } else {

                player.sendMessage(colour(languageConfig.getString("use")));
            }
        } else {
            sender.sendMessage(colour(languageConfig.getString("player_only_command")));
        }

        return false;
    }

    private String colour(String toColour) {
        return ChatColor.translateAlternateColorCodes('&', toColour);
    }
}
