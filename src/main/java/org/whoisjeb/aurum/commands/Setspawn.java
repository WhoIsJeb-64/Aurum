package org.whoisjeb.aurum.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class Setspawn extends AurumCommandBase {
    private final Aurum plugin;

    public Setspawn(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!validatePlayerhood(sender)) return true;

        Player player = (Player) sender;
        Location location = player.getLocation();
        String newSpawn = plugin.settings.locationToString(location);
        plugin.settings.setProperty("general.spawn", newSpawn);
        plugin.settings.save();
        location.getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        player.sendMessage("§5Set world spawn successfully!");
        return true;
    }
}
