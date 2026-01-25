package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import org.whoisjeb.aurum.data.User;
import java.util.Map;
import java.util.UUID;

public class WhoIs extends AurumCommand {
    private final Aurum plugin;
    private final AurumSettings settings;

    public WhoIs(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify the nickname!");
            return true;
        }
        String nickname = args[0];
        boolean matchesFound = false;
        for (Map.Entry<UUID, User> entry : plugin.loadedUsers().entrySet()) {
            User user = entry.getValue();
            if (user.getString("info.nickname").equalsIgnoreCase(nickname)) {
                sender.sendMessage("§6Match:§e " + user.getString("info.name"));
                matchesFound = true;
            }
        }
        if (!matchesFound) {
            sender.sendMessage("&6No Matches Found!");
        }
        return true;
    }
}
