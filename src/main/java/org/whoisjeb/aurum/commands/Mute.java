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

public class Mute extends AuricCommand {
    private final Aurum plugin;
    private final AurumPunishments punishments;

    public Mute(Aurum plugin) {
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

        //Create an ArrayList from args for data handling
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));

        //Extract duration from arguments if given
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
        String reason = (args.length < 2) ? "Rule breaking" : String.join(" ", argsList.remove(0));

        //Issue mute and send appropiate messages
        punishments.mute(target, duration, reason, issuer);
        String message = message(command, (duration == 0) ? "permanent" : "temporary")
                .replace("%player%", target.getName())
                .replace("%reason%", reason)
                .replace("%duration%", String.valueOf(duration));

        //Should never happen, but just in case:
        if (!punishments.isBanned(plugin.getUUID(target))) {
            message = message(command, "fail").replace("%player%", target.getName());
        }

        sender.sendMessage(message);
        return true;
    }
}
