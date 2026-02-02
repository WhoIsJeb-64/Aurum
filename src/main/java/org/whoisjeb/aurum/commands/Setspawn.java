package org.whoisjeb.aurum.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;

public class Setspawn extends AurumCommandBase {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Setspawn(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isSenderPlayer(sender)) return true;

        Player player = (Player) sender;
        Location location = player.getLocation();
        String newSpawn = settings.locationToString(location);
        settings.setProperty("general.spawn", newSpawn);
        location.getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        player.sendMessage("§5Set world spawn successfully!");
        return true;
    }
}
