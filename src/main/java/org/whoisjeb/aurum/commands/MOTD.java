package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import java.util.List;

public class MOTD extends AuricCommand {
    private final Aurum plugin;

    public MOTD(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        List<String> motd = plugin.language.getStringList("commands.motd", null);
        motd.replaceAll(line -> line.replace("%name%", sender.getName()));
        motd.replaceAll(line -> line.replace("%nickname%", sender.getName()));
        motd.replaceAll(line -> line.replace("%prefix%", plugin.utils.getPrefix(sender.getName())));

        sendMessages(sender, motd);
        return true;
    }
}
