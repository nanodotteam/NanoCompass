package com.github.nanodot.nanocompass;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class ToggleCommand implements CommandExecutor {
    boolean isEnglish;
    private NanoCompass plugin;
    private FileConfiguration pluginConfig;

    public ToggleCommand(NanoCompass plugin) {
        this.plugin = plugin;
        this.pluginConfig = plugin.getPluginConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(pluginConfig.getBoolean("english")){
            isEnglish = true;
        } else{
            isEnglish = false;
        }

        if(command.getName().equalsIgnoreCase("nanocompass")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;

                // Enable player's compass
                if (!isEnglish) {


                    // POLISH vvvvv



                    if (args[0].equalsIgnoreCase("wlacz")) {
                        if (pluginConfig.getStringList("disabled").contains(player.getName())) {
                            List<String> playersDisabled = pluginConfig.getStringList("disabled");
                            playersDisabled.remove(player.getName().toString()); // Why .toString? Player.getName returns a string already!
                            pluginConfig.set("disabled", playersDisabled);
                            plugin.getLogger().info("Gracz " + player.getName() + " wlaczyl kompas!");
                            player.sendMessage(ChatColor.GREEN + "W��czono kompas.");
                        } else {
                            player.sendMessage(ChatColor.RED + "Kompas jest ju� w��czony!");
                        }

                    } else if (args[0].equalsIgnoreCase("wylacz")) {
                        if (!pluginConfig.getStringList("disabled").contains(player.getName())) {

                            List<String> playersDisabled = pluginConfig.getStringList("disabled");
                            playersDisabled.add(player.getName().toString());

                            pluginConfig.set("disabled", playersDisabled);
                            plugin.getLogger().info("Gracz " + player.getName() + " wylaczyl kompas!");
                            player.sendMessage(ChatColor.RED + "Wy��czono kompas.");

                        } else {
                            player.sendMessage(ChatColor.RED + "Kompas jest ju� wy��czony!");
                        }

                    } else {

                        player.sendMessage(ChatColor.GREEN + "Poprawne u�ycie: /nanocompass [wlacz|wylacz]");

                    }


                    // POLISH ^^^^^

                }
                if (isEnglish) {

                    if (args[0].equalsIgnoreCase("on")) {

                        if (pluginConfig.getStringList("disabled").contains(player.getName())) {
                            List<String> playersDisabled = pluginConfig.getStringList("disabled");
                            playersDisabled.remove(player.getName().toString());
                            pluginConfig.set("disabled", playersDisabled);
                            plugin.getLogger().info("Player " + player.getName() + " turned on compass!");
                            player.sendMessage(ChatColor.GREEN + "Compass enabled.");
                        } else {
                            player.sendMessage(ChatColor.RED + "Compass is already enabled!");
                        }

                        // Disable player's compass
                    } else if (args[0].equalsIgnoreCase("off")) {

                        if (!pluginConfig.getStringList("disabled").contains(player.getName())) {

                            List<String> playersDisabled = pluginConfig.getStringList("disabled");
                            playersDisabled.add(player.getName().toString());

                            pluginConfig.set("disabled", playersDisabled);
                            plugin.getLogger().info("Player " + player.getName() + " turned off compass!");
                            player.sendMessage(ChatColor.RED + "Compass disabled.");

                        } else {
                            player.sendMessage(ChatColor.RED + "Compass is already off!");
                        }

                    } else {
                        player.sendMessage(ChatColor.GREEN + "Correct command args: /nanocompass [on | off]");
                    }
                }

            }
        }
        return false;
    }
}
