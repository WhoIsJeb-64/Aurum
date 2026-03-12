package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumUser;

public class WhoIs extends AuricCommand {
    private final Aurum plugin;

    public WhoIs(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    //TODO: Make this command actually work
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Make sure a nickname is given
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("{thing}", "nickname"));
            return true;
        }

        //Track whether any matches are found
        boolean matchFound = false;

        //Iterate through online players, sending a message for every match.
        String nickname = plugin.utils.colorize(args[0], false);
        for (AurumUser user : plugin.loadedUsers().values()) {
            if (args[0].equalsIgnoreCase(nickname)) {
                sender.sendMessage(message(command, "match")
                        .replace("{name}", user.getString("info.name")));
                matchFound = true;
            }
        }

        if (!matchFound) sender.sendMessage(message(command, "no-matches"));
        return true;
    }
}
