package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.TeleportRequest;

public class Teleportask extends AuricCommand {
    private final Aurum plugin;

    public Teleportask(Aurum plugin) {
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
            sender.sendMessage(message(command, "to-self"));
            return true;
        }

        //Send the request and appropiate messages
        TeleportRequest tpRequest = new TeleportRequest(plugin, (Player) sender, target);
        tpRequest.send(true);
        sender.sendMessage(message(command, "sender.sent")
                .replace("{name}", target.getName())
                .replace("{nickname}", target.getDisplayName())
                .replace("{color}", plugin.utils.getColor(target.getName()))
                .replace("{prefix}", plugin.utils.getPrefix(target.getName())));
        return true;
    }
}
