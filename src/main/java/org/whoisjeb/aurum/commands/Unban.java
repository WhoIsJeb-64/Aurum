package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;

public class Unban extends AurumCommandBase {
    private final Aurum plugin;

    public Unban(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a player!");
            return true;
        }
        OfflinePlayer target = (Bukkit.getPlayer(args[0]) == null) ? Bukkit.getOfflinePlayer(args[0]) : Bukkit.getPlayer(args[0]);
        plugin.punishments.unban(target);
        String message = !target.isBanned()
                ? "§2Unbanned§a " + target.getName() + "§2!" : "§4[!] Failed to unban§c " + target.getName() + "§4!";
        sender.sendMessage(message);
        return true;
    }
}
