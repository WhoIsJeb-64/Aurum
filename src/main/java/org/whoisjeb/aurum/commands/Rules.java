package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;

public class Rules extends AurumCommandBase {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Rules(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        for (Object rawLine : settings.getList("messages.rules")) {
            String line = plugin.colorize(rawLine.toString(), true);
            sender.sendMessage(line);
        }
        return true;
    }
}
