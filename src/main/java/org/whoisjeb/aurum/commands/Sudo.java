package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import java.util.Arrays;

public class Sudo extends AurumCommandBase {
    private final Aurum plugin;

    public Sudo(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a victim!");
            return true;
        }
        Player victim;
        if (Bukkit.getPlayer(args[0]) != null) victim = Bukkit.getPlayer(args[0]);
        else {
            sender.sendMessage("§c[!] Invalid player!");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage("§c[!] Please specify a command or message!");
            return true;
        }

        String[] sudoContent;
        sudoContent = Arrays.copyOfRange(args, 1, args.length);
        if (sudoContent[0].startsWith("c:")) {
            sudoContent[0] = sudoContent[0].substring(2);
            String message = plugin.chatFormat(victim, String.join(" ", sudoContent));
            Bukkit.getServer().broadcastMessage(message);
        }
        else {
            Bukkit.getServer().dispatchCommand(victim, String.join(" ", sudoContent));
        }
        return true;
    }
}
