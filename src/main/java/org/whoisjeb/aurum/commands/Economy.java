package org.whoisjeb.aurum.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumUser;

public class Economy extends AuricCommand {
    private final Aurum plugin;

    public Economy(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Make sure a subcommand is specified
        String message = message("error.command-usage").replace("{usage}", command.getUsage());
        if (args.length < 1) {
            sender.sendMessage(message);
            return true;
        }

        //Logic for the subcommands give, take and set
        if (args[0].equalsIgnoreCase("give")|| args[0].equalsIgnoreCase("take") || args[0].equalsIgnoreCase("set")) {
            OfflinePlayer target = (args.length >= 2) ? (OfflinePlayer) getTarget(args[1]) : null;
            if (target == null) {
                sender.sendMessage(message("error.command-usage")
                        .replace("{usage}", command.getUsage()));
                return true;
            }

            //Get AurumUser object
            AurumUser user = new AurumUser(plugin.utils.getUUID(target));
            user.load(plugin.utils.getUUID(target));
            double balance = 0;

            //Add the value of args[2] to the target's balance
            if (args[0].equalsIgnoreCase("give") && args.length >= 3) {
                balance = Double.parseDouble(args[2]);
                user.setProperty("economy.balance", user.getDouble("economy.balance") + balance);
            }
            //Take the value of args[2] from the target's balance
            else if (args[0].equalsIgnoreCase("take") && args.length >= 3) {
                balance = Double.parseDouble(args[2]);
                user.setProperty("economy.balance", user.getDouble("economy.balance") - balance);
            }
            //Set the target's balance to the value of args[2]
            else if (args[0].equalsIgnoreCase("set") && args.length >= 3) {
                user.setProperty("economy.balance", Double.parseDouble(args[2]));
            }

            //Send appropiate message based on subcommand
            message = message(command, args[0])
                    .replace("{target}", target.getName())
                    .replace("{amount}", String.valueOf(balance))
                    .replace("{newBalance}", String.valueOf(user.getDouble("economy.balance")));
            sender.sendMessage(message);
            return true;
        }

        sender.sendMessage(message);
        return true;
    }
}
