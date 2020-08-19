package com.github.nanodot.nanocompass;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class YawEvent implements Listener {
    private final FileConfiguration pluginConfig;
    private final String[] compassDirections;
    private final int compassTemplateViewSide;

    public YawEvent(NanoCompass plugin) {
        pluginConfig = plugin.getPluginConfig();
        compassDirections = splitWithColours(Objects.requireNonNull(pluginConfig.getString("compass_template")));
        compassTemplateViewSide = (pluginConfig.getInt("compass_template_view") - 1) / 2;
    }

    // Send actionbar with direction to player when he move
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        List<String> playersDisabled = pluginConfig.getStringList("disabled");

        if(!playersDisabled.contains(player.getName())) {
            String compassPart = getCompassPart(player);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(compassPart));
        }
    }

    // Calculate and return the direction
    private String getCompassPart(Player player) {
        // south 0, west 90, north 180, east 270
        float playerYaw = player.getLocation().getYaw();
        int compassCenter = Math.floorMod(Math.round(playerYaw / (360f / compassDirections.length)), compassDirections.length);
        StringBuilder compassLine = new StringBuilder();
        for(int i = -compassTemplateViewSide; i <= compassTemplateViewSide; i++) {
            compassLine.append(getCompassChar(compassCenter + i));
        }
        return compassLine.toString();
    }

    private String getCompassChar(int charNum) {
        int normalizedCharNum;
        if(charNum < 0) {
            normalizedCharNum = compassDirections.length - 1 + charNum;
        } else if(charNum > compassDirections.length - 1) {
            normalizedCharNum = charNum % compassDirections.length;
        } else {
            normalizedCharNum = charNum;
        }
        return compassDirections[normalizedCharNum];
    }

    private String[] splitWithColours(String toSplit) {
        String[] splitted = toSplit.split("");
        ArrayList<String> formatted = new ArrayList<>();
        StringBuilder latestFormat = new StringBuilder();
        int i = 0;
        while(i < splitted.length) {
            if(splitted[i].equals("&")) {
                latestFormat.append(ChatColor.COLOR_CHAR).append(splitted[i + 1]);
                i += 2;
            } else {
                while(i < splitted.length && !splitted[i].equals("&")) {
                    formatted.add(latestFormat + splitted[i]);
                    i++;
                }
                latestFormat = new StringBuilder();
            }
        }
        return formatted.toArray(new String[0]);
    }
}
