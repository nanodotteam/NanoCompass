package pl.krzmaciek.nanocompass;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class YawEvent implements Listener {
	
	private String south = ChatColor.YELLOW + "" + ChatColor.BOLD + "S";
	private String north = ChatColor.RED + "" + ChatColor.BOLD + "N";
	private String east = ChatColor.GREEN + "" + ChatColor.BOLD + "E";
	private String west = ChatColor.BLUE + "" + ChatColor.BOLD + "W";
			
	private FileConfiguration pluginConfig;
	
	public YawEvent(NanoCompass plugin) {
		pluginConfig = plugin.getPluginConfig();
	}
	
	private String getCardinalDirection(Player player) {
		double rotation = (player.getLocation().getYaw() - 90) % 360;
		
		if (rotation < 0) {
			rotation += 360;
		}
		
		if (0 <= rotation && rotation < 22.5) {
			return west; // west
		} else if (22.5 <= rotation && rotation < 67.5) {
			return north+west; // northwest
		} else if (67.5 <= rotation && rotation < 112.5) {
			return north; // north
		} else if (112.5 <= rotation && rotation < 157.5) {
			return north+east; // northeast
		} else if (157.5 <= rotation && rotation < 202.5) {
			return east; // east
		} else if (202.5 <= rotation && rotation < 247.5) {
			return south+east;// southeast
		} else if (247.5 <= rotation && rotation < 292.5) {
			return south; // south
		} else if (292.5 <= rotation && rotation < 337.5) {
			return south+west; // southwest
		} else if (337.5 <= rotation && rotation < 360.0) {
			return west; // west
		} else {
			return "";
		}				
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		
		List<String> playersDisabled = pluginConfig.getStringList("disabled");
		
		if(!playersDisabled.contains(player.getName())) {
			
			String cardinalDirection = getCardinalDirection(player);

			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "-[---- " + cardinalDirection + ChatColor.GRAY + " ----]-"));                       
		}
		
	}
	
// Alternative way to send actionbar to player
//
//	public void sendActionbar(Player player, String msg) {
//		
//		msg = ChatColor.GRAY + "[======  " + msg + ChatColor.GRAY + "  ======]";
//		IChatBaseComponent icbc = ChatSerializer.a("{\"text\":\"" + msg + "\"}");
//		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR, icbc, 20, 60, 20);
//		
//		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
//		
//	}		
//}
	
}
