package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import java.util.logging.Logger;

public class Setspawn extends AurumCommand {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Setspawn(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isSenderPlayer(sender)) {
            return true;
        }
        Player player = (Player) sender;
        Location location = player.getLocation();
        String newSpawn = settings.locationToString(location);
        settings.setProperty("general.spawn", newSpawn);
        location.getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        player.sendMessage("§5Set world spawn successfully!");
        return true;
    }
}
