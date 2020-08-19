package com.github.nanodot.nanocompass;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ToggleCommandCompleter implements TabCompleter {
    private FileConfiguration langConfig = NanoCompass.languageConfig;
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> possibles = new ArrayList<>();
        if(args.length == 1) {
            possibles.add(langConfig.getString("turn_on"));
            possibles.add(langConfig.getString("turn_off"));
        }
        return possibles;
    }
}
