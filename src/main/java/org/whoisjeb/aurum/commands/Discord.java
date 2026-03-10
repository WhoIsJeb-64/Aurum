package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import java.util.List;

public class Discord extends AuricCommand {
    private final Aurum plugin;

    public Discord(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Send appropiate message
        List<String> message = plugin.language.getStringList("commands.discord", null);
        sendMessages(sender, message);
        return true;
    }
}
