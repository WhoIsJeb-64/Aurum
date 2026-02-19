package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;

public class Discord extends AurumCommandBase {
    private final Aurum plugin;

    public Discord(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String discordMessage = plugin.settings.getString("messages.discord");
        discordMessage = plugin.colorize(discordMessage, true);
        sender.sendMessage(discordMessage);
        return true;
    }
}
