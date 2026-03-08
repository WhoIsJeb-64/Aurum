package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class Teleport extends AuricCommand {
    private final Aurum plugin;

    public Teleport(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isPlayer(sender)) return true;
        Player player = (Player) sender;

        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("%thing%", "player or position"));
            return true;
        }
        if (getOnlineTarget(args[0]) != null) {
            player.teleport(Bukkit.getPlayer(args[0]));
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage(message("error.invalid").replace("%thing%", "player or position"));
            return true;
        }
        player.teleport(new Location(player.getWorld(),
                Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
        return true;
    }
}
