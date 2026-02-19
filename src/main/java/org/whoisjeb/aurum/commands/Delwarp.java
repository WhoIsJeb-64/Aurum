package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class Delwarp extends AurumCommandBase {
    private final Aurum plugin;

    public Delwarp(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!validatePlayerhood(sender)) return true;
        Player player = (Player) sender;

        String warpName;
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a warp to delete!");
            return true;
        } else {
            warpName = args[0];
        }
        if (!plugin.settings.hasProperty("general.warps." + warpName)) {
            player.sendMessage("§c[!] That warp does not exist!");
            return true;
        }
        plugin.settings.removeProperty("general.warps." + warpName);
        player.sendMessage("§2Deleted warp§a " + warpName + "§2!");
        return true;
    }
}
