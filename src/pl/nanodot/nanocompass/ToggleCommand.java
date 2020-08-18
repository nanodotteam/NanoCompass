package pl.nanodot.nanocompass;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class ToggleCommand implements CommandExecutor {

	NanoCompass mainclass = new NanoCompass();
	boolean isEnglish = mainclass.isEnglish;
	private NanoCompass plugin;
	private FileConfiguration pluginConfig;
	
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
				if (!isEnglish) {

					if (args[0].equalsIgnoreCase("wlacz")) {
						if (pluginConfig.getStringList("disabled").contains(player.getName())) {
							List<String> playersDisabled = pluginConfig.getStringList("disabled");
							playersDisabled.remove(player.getName().toString()); // Why .toString? Player.getName returns a string already!
							pluginConfig.set("disabled", playersDisabled);
							plugin.getLogger().info("Gracz " + player.getName() + " wlaczyl kompas!");
							player.sendMessage(ChatColor.GREEN + "W³¹czono kompas.");
						} else {
							player.sendMessage(ChatColor.RED + "Kompas jest ju¿ w³¹czony!");
						}

					} else if (args[0].equalsIgnoreCase("wylacz")) {
						if (!pluginConfig.getStringList("disabled").contains(player.getName())) {

							List<String> playersDisabled = pluginConfig.getStringList("disabled");
							playersDisabled.add(player.getName().toString());

							pluginConfig.set("disabled", playersDisabled);
							plugin.getLogger().info("Gracz " + player.getName() + " wylaczyl kompas!");
							player.sendMessage(ChatColor.GREEN + "Wy³¹czono kompas.");

						} else {
							player.sendMessage(ChatColor.RED + "Kompas jest ju¿ wy³¹czony!");
						}

					} else {

						player.sendMessage(ChatColor.GREEN + "Poprawne u¿ycie: /nanocompass [wlacz|wylacz]");

					}

				}
				if (!isEnglish) {
					if (args[0].equalsIgnoreCase("on")) {

						if (pluginConfig.getStringList("disabled").contains(player.getName())) {

							List<String> playersDisabled = pluginConfig.getStringList("disabled");
							playersDisabled.remove(player.getName().toString());
							//plugin.getConfig().getStringList("disabled").remove(player.getName().toString());

							pluginConfig.set("disabled", playersDisabled);
							plugin.getLogger().info("Player " + player.getName() + " turned on compass!");
							player.sendMessage(ChatColor.GREEN + "Compass enabled.");

						} else {

							player.sendMessage(ChatColor.RED + "The compass has been enabled!");

						}

						// Disable player's compass
					} else if (args[0].equalsIgnoreCase("disable")) {

						if (!pluginConfig.getStringList("disabled").contains(player.getName())) {

							List<String> playersDisabled = pluginConfig.getStringList("disabled");
							playersDisabled.add(player.getName().toString());

							pluginConfig.set("disabled", playersDisabled);
							plugin.getLogger().info("Player " + player.getName() + " turned off compass!");
							player.sendMessage(ChatColor.RED + "Compass disabled.");

						} else {
							player.sendMessage(ChatColor.RED + "Kompas jest ju¿ wy³¹czony!");
						}

					} else {
						player.sendMessage(ChatColor.GREEN + "Correct command args: /nanocompass [on | off]");
					}
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "Komendy mog¹ u¿ywaæ tylko gracze!");
				}
			}
		}
		return false;
	}

}
