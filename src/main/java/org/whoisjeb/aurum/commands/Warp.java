package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import java.util.logging.Logger;

public class Warp implements CommandExecutor {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Warp(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            log.info("That command may only be used by a player!");
            return true;
        }
        String warpName;
        if (strings.length < 1) {
            commandSender.sendMessage("§c[!] Please specify a warp!");
            return true;
        } else {
            warpName = strings[0];
        }
        Player player = (Player) commandSender;
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
