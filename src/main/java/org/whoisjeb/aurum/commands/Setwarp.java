package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class Setwarp extends AurumCommandBase {
    private final Aurum plugin;

    public Setwarp(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!validatePlayerhood(sender)) return true;

        String warpName;
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a name for the new home!");
            return true;
        } else {
            warpName = args[0];
        }

        Player player = (Player) sender;
        String playerLocation = plugin.settings.locationToString(player.getLocation());
        plugin.settings.setProperty("general.warps." + warpName, playerLocation);
        player.sendMessage("§2Set warp§a " + warpName + "§2!");
        return true;
    }
}
