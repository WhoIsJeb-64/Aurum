package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumUser;

public class Pay extends AuricCommand {
    private final Aurum plugin;

    public Pay(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isPlayer(sender)) return true;
        Player player = (Player) sender;

        //Make sure a target is specified
        OfflinePlayer target = (args.length >= 1) ? (OfflinePlayer) getTarget(args[0]) : null;
        if (target == null) {
            sender.sendMessage(message(command, "target-cant-be-sender"));
            return true;
        }

        //Make sure an amount is specified
        if (args.length < 2) {
            sender.sendMessage(message("error.specify").replace("a %thing%", "an amount"));
            return true;
        }

        //Get AurumUser instances
        AurumUser userTarget = new AurumUser(plugin.getUUID(target));
        userTarget.load(plugin.getUUID(target));
        AurumUser userSender = new AurumUser(player.getUniqueId());
        userSender.load(player.getUniqueId());

        //Get user balances and the amount to be paid
        double targetBalance = userTarget.getDouble("economy.balance");
        double senderBalance = userSender.getDouble("economy.balance");
        double amount = Double.parseDouble(args[1]);

        //Process payment and send appropiate messages
        if (!userSender.subtractBalance(amount, false)) {
            sender.sendMessage(message(command, "not-enough-money")
                    .replace("%balance%", String.valueOf(senderBalance)));
            return true;
        }
        sender.sendMessage(message(command, "message-sender")
                .replace("%amount%", String.valueOf(amount))
                .replace("%target%", target.getName())
                .replace("%balance%", String.valueOf(senderBalance)));
        userTarget.addBalance(amount);

        //Target only recieves a message if they're online
        if (target.isOnline()) {
            Bukkit.getPlayer(target.getName()).sendMessage(message(command, "message-target")
                    .replace("%sender%", sender.getName())
                    .replace("%amount%", String.valueOf(amount))
                    .replace("%balance%", String.valueOf(senderBalance)));
        }
        return true;
    }
}
