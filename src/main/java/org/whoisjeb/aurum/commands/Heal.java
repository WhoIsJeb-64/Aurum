package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class Heal extends AuricCommand {
    private final Aurum plugin;

    public Heal(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Get target, who is the sender if none are specified
        Player target = (args.length >= 1) ? getOnlineTarget(args[0]) : (Player) sender;

        //Heal target and send appropiate messages
        target.setHealth(20);
        sender.sendMessage(message(command, "sender").replace("%target%", target.getName()));
        if (target != sender) target.sendMessage(message(command, "target"));
        return true;
    }
}
