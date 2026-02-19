package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;

public class Tellraw extends AurumCommandBase {
    private final Aurum plugin;

    public Tellraw(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please provide a message!");
            return true;
        }

        String message = String.join(" ", args);
        Bukkit.broadcastMessage(plugin.colorize(message, true));
        return true;
    }
}
