package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class Spawn extends AuricCommand {
    private final Aurum plugin;

    public Spawn(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isPlayer(sender)) return true;
        Player player = (Player) sender;
        String path = "data.spawn";

        //Initialize spawn if it's null
        if (plugin.settings.getLocation(path) == null) {
            Location spawn = Bukkit.getWorlds().get(0).getSpawnLocation();
            plugin.settings.setProperty(path, spawn);
        }

        Location spawn = plugin.settings.getLocation(path);
        player.teleport(spawn);
        player.sendMessage(message(command));
        return true;
    }
}
