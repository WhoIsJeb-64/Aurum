package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;

public class Tellraw extends AurumCommandBase {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Tellraw(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
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
