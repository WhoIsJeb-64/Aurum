package org.whoisjeb.aurum.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.Punishments;
import java.util.Arrays;

public class Warn extends AurumCommandBase {
    private final Aurum plugin;
    private final Punishments punishments;

    public Warn(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
        this.punishments = plugin.punishments;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Determine who will be warned
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a player!");
            return true;
        }
        OfflinePlayer target = (OfflinePlayer) getTarget(args[0]);
        if (target == null) {
            sender.sendMessage("§c[!] No match was found!");
            return true;
        }

        //Determine the reason
        String reason = (args.length < 2)
                ? "Rule breaking" : String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        //Issue warning and send appropiate messages
        punishments.warn(target, reason);
        String message = punishments.hasProperty("warnings." + plugin.getUUID(target))
                ? "§6Warned§e " + target.getName() + " §2for§e " + reason + "§6!" : "§4[!] Failed to warn§c " + target.getName() + "§4!";
        sender.sendMessage(message);
        return true;
    }
}
