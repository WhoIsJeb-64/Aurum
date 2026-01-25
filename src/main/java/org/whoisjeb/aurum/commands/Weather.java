package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import java.util.logging.Logger;

public class Weather extends AurumCommand {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Weather(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        World world = (sender instanceof Player) ? ((Player) sender).getWorld() : Bukkit.getWorlds().get(0);
        if (args.length < 1) {
            args = new String[]{world.hasStorm() ? "clear" : "rain"};
        }
        if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("sun")) {
            world.setStorm(false);
            sender.sendMessage("§3Set the weather to clear!");
        } else if (args[0].equalsIgnoreCase("rain") || args[0].equalsIgnoreCase("storm")) {
            world.setStorm(true);
            sender.sendMessage("§3Set the weather to rain!");
        } else {
            sender.sendMessage("§c[!] Invalid weather! Valid weathers: clear, rain");
        }
        return true;
    }
}
