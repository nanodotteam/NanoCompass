package pl.krzmaciek.mcompass;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ToggleCommand implements CommandExecutor {

	private MCompass plugin;
	private FileConfiguration pluginConfig;
	
	public ToggleCommand(MCompass plugin) {
		this.plugin = plugin;
		this.pluginConfig = plugin.getPluginConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("mcompass")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				
				if(args[0].equalsIgnoreCase("wlacz")) {
					
					plugin.getLogger().info("Gracz " + player.getName() + " wlaczyl kompas!");
					
					if(pluginConfig.getStringList("disabled").contains(player.getName())) {
						
						List<String> playersDisabled = pluginConfig.getStringList("disabled");
						playersDisabled.remove(player.getName().toString());
						//plugin.getConfig().getStringList("disabled").remove(player.getName().toString());
						
						pluginConfig.set("disabled", playersDisabled);
						
					} else {
						
						player.sendMessage(ChatColor.RED + "Kompas jest ju¿ w³¹czony!");
						
					}
					
				} else if (args[0].equalsIgnoreCase("wylacz")) {
					
					plugin.getLogger().info("Gracz " + player.getName() + " wylaczyl kompas!");
					
					if(!pluginConfig.getStringList("disabled").contains(player.getName())) {
						
						List<String> playersDisabled = pluginConfig.getStringList("disabled");
						playersDisabled.add(player.getName().toString());
						
						pluginConfig.set("disabled", playersDisabled);
						
					} else {
						player.sendMessage(ChatColor.RED + "Kompas jest ju¿ wy³¹czony!");
					}
					
				} else {
					
					player.sendMessage(ChatColor.GREEN + "Poprawne u¿ycie: /mcompass [wlacz|wylacz]");
					
				}
				
			} else {
				sender.sendMessage(ChatColor.DARK_RED + "Komendy mog¹ u¿ywaæ tylko gracze!");
			}
 		}
		
		return false;
	}

}
