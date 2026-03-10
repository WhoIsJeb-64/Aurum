package org.whoisjeb.aurum.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumPunishments;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Ban extends AuricCommand {
    private final Aurum plugin;
    private final AurumPunishments punishments;

    public Ban(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
        this.punishments = plugin.punishments;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Target must be specified
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("%thing%", "player"));
            return true;
        }

        //Validate that the target exists
        OfflinePlayer target = (OfflinePlayer) getTarget(args[0]);
        if (target == null) {
            sender.sendMessage(message("error.doesnt-exist").replace("%thing%", "Player"));
            return true;
        }

        //Resolve issuer's name
        String issuer = (sender instanceof Player) ? sender.getName() : "Console";

        //Create an ArrayList from args for data handling; Purge target name
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
        argsList.remove(0);

        String durationArg = "";
        long duration = 0;
        for (String arg : argsList) {
            if (Pattern.matches("d:\\d+[a-z]", arg)) {
                //Get the quantity of units
                duration = Integer.parseInt(arg.replaceAll("\\D+",""));
                durationArg = arg;

                //Get the unit of time. Supports weeks, days, hours, and minutes.
                char unit = arg.charAt(arg.length() - 1);
                switch(unit) {
                    case 'w':
                        duration *= 604800000L;
                        break;
                    case 'd':
                        duration *= 86400000L;
                        break;
                    case 'h':
                        duration *= 3600000L;
                        break;
                    case 'm':
                        duration *= 60000L;
                        break;
                    default:
                        sender.sendMessage(message(command, "invalid-duration"));
                        return true;
                }
            }
        }

        //Remove duration argument so the rest can compose the ban reason
        if (!durationArg.isEmpty()) argsList.remove(durationArg);

        //Get ban reason
        String reason = (argsList.isEmpty()) ? "Rule breaking" : String.join(" ", argsList);

        //Issue ban and send appropiate messages
        punishments.ban(target, duration, reason, issuer);
        String message = message(command, (duration == 0L) ? "permanent" : "temporary")
                .replace("%player%", target.getName())
                .replace("%reason%", reason)
                .replace("%duration%", formatDuration(duration));

        //Should never happen, but just in case:
        if (!punishments.isBanned(plugin.utils.getUUID(target))) {
            message = message(command, "fail").replace("%player%", target.getName());
        }

        sender.sendMessage(message);
        return true;
    }

    private String formatDuration(Long raw) {
        long minutes = (raw / (1000 * 60)) % 60;
        long hours = (raw / (1000 * 60 * 60)) % 24;
        long days = (raw / (1000 * 60 * 60 * 24));

        return String.format("%02dd %02dh %02dm", days, hours, minutes);
    }
}
