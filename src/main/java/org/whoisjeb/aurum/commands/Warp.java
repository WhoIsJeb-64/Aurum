package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;

public class Warp extends AurumCommandBase {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Warp(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isSenderPlayer(sender)) return true;
        Player player = (Player) sender;

        String warpName;
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a warp!");
            return true;
        } else {
            warpName = args[0];
        }
        if (!settings.hasProperty("general.warps." + warpName)) {
            player.sendMessage("§c[!] That warp does not exist!");
            return true;
        }
        if (settings.getBoolean("general.per-warp-perms", false)) {
            if (!player.hasPermission("aurum.warp." + warpName)) {
                player.sendMessage("§cYou are not authorized to use that warp!");
                return true;
            }
        }
        player.teleport(settings.getLocation("general.warps." + warpName));
        player.sendMessage("§5Teleported to warp§d " + warpName + "§5!");
        return true;
    }
}
