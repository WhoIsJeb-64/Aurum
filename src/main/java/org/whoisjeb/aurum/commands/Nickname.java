package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.User;

public class Nickname extends AurumCommandBase {
    private final Aurum plugin;

    public Nickname(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§c[!] Usage: /nickname <set|clear> <nickname|> <player>");
            return true;
        }
        if (args.length < 2 && !args[0].equals("clear")) {
            sender.sendMessage("§c[!] Please specify your desired nickname!");
            return true;
        }

        String subject = (args.length >= 3) ? args[2] : sender.getName();

        Player player;
        try {
            player = Bukkit.getPlayer(subject);
        } catch (Exception e) {
            sender.sendMessage("§c[!] Invalid Player!");
            return true;
        }
        if (Bukkit.getPlayer(subject) == sender && !sender.hasPermission("aurum.nickname.others")) {
            sender.sendMessage("§c[!] You are not authorized to modify the nicknames of others!");
            return true;
        }

        User user = new User(player.getUniqueId()).loadIfUnloaded(player);
        if (args[0].equals("clear")) {
            user.removeProperty("info.nickname");
            player.setDisplayName(player.getName());
            player.sendMessage("§2Set nickname successfully!");
        }
        else if (args[0].equals("set")) {
            String nickname = plugin.colorize(args[1], player.hasPermission("aurum.color"));
            user.setProperty("info.nickname", nickname);
            player.setDisplayName(plugin.settings.getString("general.nickname-prefix") + nickname);
            player.sendMessage("§2Cleared nickname successfully!");
        }
        else {
            player.sendMessage("§c[!] Usage: /nickname <set|clear> <nickname|> <player>");
        }
        return true;
    }
}
