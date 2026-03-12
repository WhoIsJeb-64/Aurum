package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumPunishments;
import java.util.Arrays;

public class Unwarn extends AuricCommand {
    private final Aurum plugin;
    private final AurumPunishments punishments;

    public Unwarn(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
        this.punishments = plugin.punishments;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("{thing}", "player"));
            return true;
        }

        //Determine the reason
        String reason = (args.length < 2)
                ? "Rule breaking" : String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        OfflinePlayer target = (Bukkit.getPlayer(args[0]) == null) ? Bukkit.getOfflinePlayer(args[0]) : Bukkit.getPlayer(args[0]);
        plugin.punishments.unwarn(target, reason);
        String message = message(command, (!punishments.hasProperty("warnings." + plugin.utils.getUUID(target)) ? "run" : "fail"))
                .replace("{target}", target.getName());
        sender.sendMessage(message);
        return true;
    }
}
