package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.TeleportRequest;

public class Teleportask extends AurumCommandBase {
    private final Aurum plugin;

    public Teleportask(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!validatePlayerhood(sender)) return true;

        //Determine target
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a player!");
            return true;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage("§c[!] Invalid player!");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);

        //Make sure the target is not the sender
        if (isTargetSender(sender, target)) {
            sender.sendMessage("§c[!] You cannot send a teleport request to yourself!");
            return true;
        }

        //Send the request and appropiate messages
        TeleportRequest tpRequest = new TeleportRequest(plugin, (Player) sender, target);
        tpRequest.send();
        sender.sendMessage("§5Sent a teleport request to§d " + target.getName() + "§5!");
        return true;
    }
}
