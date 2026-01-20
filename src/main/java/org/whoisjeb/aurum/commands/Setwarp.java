package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;

import java.util.logging.Logger;

public class Setwarp implements CommandExecutor {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Setwarp(Aurum plugin, AurumSettings settings) {
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
            commandSender.sendMessage("§c[!] Please specify a name for the new home!");
            return true;
        } else {
            warpName = strings[0];
        }
        Player player = (Player) commandSender;
        String playerLocation = settings.locationToString(player.getLocation());
        settings.setProperty("general.warps." + warpName, playerLocation);
        settings.save();
        player.sendMessage("§2Set warp§a " + warpName + "§2!");
        return true;
    }
}
