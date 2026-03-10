package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import java.util.Arrays;

public class Sudo extends AuricCommand {
    private final Aurum plugin;

    public Sudo(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Make sure a target is specified
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("%thing%", "target"));
            return true;
        }

        //Make sure the target is an online player
        Player target;
        if (getOnlineTarget(args[0]) != null) target = getOnlineTarget(args[0]);
        else {
            sender.sendMessage(message("error.invalid").replace("%thing%", "player"));
            return true;
        }

        //Make sure a command or message is specified
        if (args.length < 2) {
            sender.sendMessage(message("error.specify").replace("%thing%", "command or message"));
            return true;
        }

        //Extract sudo's content from the full arguments, which includes the target
        String[] sudoContent;
        sudoContent = Arrays.copyOfRange(args, 1, args.length);

        //Logic for sudo'ing a chat message
        if (sudoContent[0].startsWith("c:")) {
            sudoContent[0] = sudoContent[0].substring(2);
            String message = plugin.utils.formatChat(target, String.join(" ", sudoContent), false);
            plugin.getServer().broadcastMessage(message);
        }
        //Logic for sudo'ing a command
        else {
            plugin.getServer().dispatchCommand(target, String.join(" ", sudoContent));
        }
        return true;
    }
}
