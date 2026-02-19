package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class Heal extends AurumCommandBase {
    private final Aurum plugin;

    public Heal(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player target = (args.length >= 1) ? getOnlineTarget(args[0]) : (Player) sender;

        target.setHealth(20);
        sender.sendMessage("§5Healed§d " + target.getDisplayName() + "§5!");
        if (target != sender) target.sendMessage("§dYou have been healed!");
        return true;
    }
}
