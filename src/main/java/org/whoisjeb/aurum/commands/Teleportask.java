package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import org.whoisjeb.aurum.data.TeleportRequest;

public class Teleportask extends AurumCommandBase {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Teleportask(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isSenderPlayer(sender)) return true;
        Player player = (Player) sender;

        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a player!");
            return true;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage("§c[!] Invalid player!");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (isTargetSender(sender, target)) {
            player.sendMessage("§c[!] You cannot send a teleport request to yourself!");
            return true;
        }

        TeleportRequest tpRequest = new TeleportRequest(plugin, player, target);
        tpRequest.initialize();
        player.sendMessage("§5Sent a teleport request to§d " + args[0] + "§5!");
        return true;
    }
}
