package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.Punishments;
import java.util.Arrays;

public class Unwarn extends AurumCommandBase {
    private final Aurum plugin;
    private final Punishments punishments;

    public Unwarn(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
        this.punishments = plugin.punishments;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a player!");
            return true;
        }

        //Determine the reason
        String reason = (args.length < 2)
                ? "Rule breaking" : String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        OfflinePlayer target = (Bukkit.getPlayer(args[0]) == null) ? Bukkit.getOfflinePlayer(args[0]) : Bukkit.getPlayer(args[0]);
        plugin.punishments.unwarn(target, reason);
        String message = punishments.hasProperty("warnings." + plugin.getUUID(target))
                ? "§6Lifted§e " + target.getName() + "'s §2warning for§e " + reason + "§6!" : "§4[!] Failed to unwarn§c " + target.getName() + "§4!";
        sender.sendMessage(message);
        return true;
    }
}
