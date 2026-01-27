package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import java.util.logging.Logger;

public class Teleport extends AurumCommand {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Teleport(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isSenderPlayer(sender)) return true;
        Player player = (Player) sender;

        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a player or position!");
            return true;
        }
        if (Bukkit.getPlayer(args[0]) != null) {
            player.teleport(Bukkit.getPlayer(args[0]));
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage("§c[!] Incomplete position / Invalid player!");
            return true;
        }
        player.teleport(new Location(player.getWorld(),
                Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
        return true;
    }
}
