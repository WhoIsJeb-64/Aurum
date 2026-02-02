package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;

public class Discord extends AurumCommandBase {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Discord(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String discordMessage = settings.getString("messages.discord");
        discordMessage = plugin.colorize(discordMessage, true);
        sender.sendMessage(discordMessage);
        return true;
    }
}
