package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import org.whoisjeb.aurum.data.User;
import java.util.UUID;
import java.util.logging.Logger;

public class Nickname extends AurumCommand {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Nickname(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
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

        UUID uuid = player.getUniqueId();
        User user = new User(plugin, uuid, plugin.userdataDir(uuid)).loadIfUnloaded(player);
        if (args[0].equals("clear")) {
            user.removeProperty("info.nickname");
            player.setDisplayName(player.getName());
            user.save();
            player.sendMessage("§2Set nickname successfully!");
        }
        else if (args[0].equals("set")) {
            String nickname = plugin.processColor(args[1], player.hasPermission("aurum.color"));
            user.setProperty("info.nickname", nickname);
            player.setDisplayName(settings.getString("general.nickname-prefix") + nickname);
            user.save();
            player.sendMessage("§2Cleared nickname successfully!");
        }
        else {
            player.sendMessage("§c[!] Usage: /nickname <set|clear> <nickname|> <player>");
        }
        return true;
    }
}
