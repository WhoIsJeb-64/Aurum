package org.whoisjeb.aurum.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumPunishments;
import java.util.Arrays;

public class Warn extends AuricCommand {
    private final Aurum plugin;
    private final AurumPunishments punishments;

    public Warn(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
        this.punishments = plugin.punishments;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Determine who will be warned
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("{thing}", "player"));
            return true;
        }
        OfflinePlayer target = (OfflinePlayer) getTarget(args[0]);
        if (target == null) {
            sender.sendMessage(message("error.doesnt-exist").replace("{thing}", "That player"));
            return true;
        }

        //Determine the reason
        String reason = (args.length < 2)
                ? "Rule breaking" : String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        //Issue warning and send appropiate messages
        punishments.warn(target, reason);
        String message = message(command, (punishments.hasProperty("warnings." + plugin.utils.getUUID(target)) ? "run" : "fail"))
                .replace("{target}", target.getName());
        sender.sendMessage(message);
        return true;
    }
}
