package org.whoisjeb.aurum.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class Setspawn extends AuricCommand {
    private final Aurum plugin;

    public Setspawn(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isPlayer(sender)) return true;

        //Get sender location to which spawn will be set
        Player player = (Player) sender;
        Location location = player.getLocation();
        plugin.settings.setProperty("data.spawn", location);

        //Set the actual world spawn within the vanilla system
        location.getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        player.sendMessage(message(command));
        return true;
    }
}
