package com.github.nanodot.nanocompass;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class YawEvent implements Listener {
    private final String[] compassDirections = "S====W====N====E====".split("");
    private final FileConfiguration pluginConfig;

    public YawEvent(NanoCompass plugin) {
        pluginConfig = plugin.getPluginConfig();
    }

    // Calculate and return the direction
    private String getCompassPart(Player player) {
        // south 0, west 90, north 180, east 270
        float playerYaw = player.getLocation().getYaw();
        int compassCenter = Math.floorMod(Math.round(playerYaw / 18), compassDirections.length);
        StringBuilder compassLine = new StringBuilder();
        for(int i = -3; i <= 3; i++) {
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
}
