package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class Warp extends AurumCommandBase {
    private final Aurum plugin;

    public Warp(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!validatePlayerhood(sender)) return true;
        Player player = (Player) sender;

        String warpName;
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a warp!");
            return true;
        } else {
            warpName = args[0];
        }
        if (!plugin.settings.hasProperty("general.warps." + warpName)) {
            player.sendMessage("§c[!] That warp does not exist!");
            return true;
        }
        if (plugin.settings.getBoolean("general.per-warp-perms", false)) {
            if (!player.hasPermission("aurum.warp." + warpName)) {
                player.sendMessage("§cYou are not authorized to use that warp!");
                return true;
            }
        }
        player.teleport(plugin.settings.getLocation("general.warps." + warpName));
        player.sendMessage("§5Teleported to warp§d " + warpName + "§5!");
        return true;
    }
}
