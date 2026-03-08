package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;

public class MOTD extends AuricCommand {
    private final Aurum plugin;

    public MOTD(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        sendMessages(sender, plugin.language.getStringList("commands.motd", null));
        return true;
    }
}
