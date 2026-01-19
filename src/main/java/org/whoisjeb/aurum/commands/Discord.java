package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import java.util.logging.Logger;

public class Discord implements CommandExecutor {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Discord(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String discordMessage = settings.getString("messages.discord");
        if (discordMessage == null) {
            sender.sendMessage("§4[!] Discord Message is null!");
            log.warning("[Aurum] Discord Message is null!");
        } else {
            discordMessage = plugin.processColor(discordMessage, true);
            sender.sendMessage(discordMessage);
        }
        return true;
    }
}
