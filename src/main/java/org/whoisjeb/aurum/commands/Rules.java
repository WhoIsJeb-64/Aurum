package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;

public class Rules extends AurumCommandBase {
    private final Aurum plugin;

    public Rules(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        for (Object rawLine : plugin.settings.getList("messages.rules")) {
            String line = plugin.colorize(rawLine.toString(), true);
            sender.sendMessage(line);
        }
        return true;
    }
}
