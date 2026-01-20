package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import java.util.logging.Logger;

public class Delwarp extends AurumCommand {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Delwarp(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isSenderPlayer(sender)) {
            return true;
        }
        String warpName;
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a warp to delete!");
            return true;
        } else {
            warpName = args[0];
        }
        Player player = (Player) sender;
        if (!settings.hasProperty("general.warps." + warpName)) {
            player.sendMessage("§c[!] That warp does not exist!");
            return true;
        }
        settings.removeProperty("general.warps." + warpName);
        settings.save();
        player.sendMessage("§2Deleted warp§a " + warpName + "§2!");
        return true;
    }
}
