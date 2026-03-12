package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.TeleportRequest;

public class TeleportaskHere
        extends AuricCommand {
    private final Aurum plugin;

    public TeleportaskHere(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isPlayer(sender)) return true;

        //Determine target
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("{thing}", "player"));
            return true;
        }
        if (getOnlineTarget(args[0]) == null) {
            sender.sendMessage(message("error.invalid").replace("{thing}", "player"));
            return true;
        }
        Player target = getOnlineTarget(args[0]);

        //Make sure the target is not the sender
        if (sender == target) {
            sender.sendMessage(message("commands.teleportask.to-self"));
            return true;
        }

        //Send the request and appropiate messages
        TeleportRequest tpRequest = new TeleportRequest(plugin, target, (Player) sender);
        tpRequest.send(false);
        sender.sendMessage(message("commands.teleportask.sender.sent"));
        return true;
    }
}
