package org.whoisjeb.aurum.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class Spawn extends AurumCommandBase {
    private final Aurum plugin;

    public Spawn(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!validatePlayerhood(sender)) return true;
        Player player = (Player) sender;

        Location spawn = plugin.settings.getLocation("general.spawn");
        player.teleport(spawn);
        player.sendMessage("§5Teleported to world spawn!");
        return true;
    }
}
