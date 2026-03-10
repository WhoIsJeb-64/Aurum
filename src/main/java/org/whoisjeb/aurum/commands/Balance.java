package org.whoisjeb.aurum.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumUser;

public class Balance extends AuricCommand {
    private final Aurum plugin;

    public Balance(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //If no target is specified, the sender must be a player for execution to continue
        OfflinePlayer target;
        String message = message("error.must-be-player");
        if (args.length < 1 && !(sender instanceof Player)) {
            sender.sendMessage(message);
            return true;
        }
        else if (args.length < 1) {
            target = (OfflinePlayer) sender;
            message = message(command, "self");
        }
        else {
            target = (OfflinePlayer) getTarget(args[0]);
            message = message(command, "other");
        }

        //Get balance from target's AurumUser object
        AurumUser user = new AurumUser(plugin.utils.getUUID(target));
        user.load(plugin.utils.getUUID(target));
        double balance = user.getDouble("economy.balance");

        //Send appropiate message
        sender.sendMessage(message
                .replace("%balance%", String.valueOf(balance))
                .replace("%name%", target.getName()));
        return true;
    }
}
