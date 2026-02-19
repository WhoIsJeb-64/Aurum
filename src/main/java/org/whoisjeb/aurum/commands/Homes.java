package org.whoisjeb.aurum.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.User;

public class Homes extends AurumCommandBase {
    private final Aurum plugin;

    public Homes(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Determine whose homes are being queried
        OfflinePlayer target = (args.length < 1) ? (Player) sender : (OfflinePlayer) getTarget(args[0]);
        User user = new User(plugin.uuidManager.getUUIDFromUsername(target.getName()));
        user.load();

        //Retrieve target player's homes
        if (user.getKeys("homes") == null || user.getKeys("homes").isEmpty()) {
            String message = isTargetSender(sender, target) ? "§6You have no homes!" : "§6" + target.getName() + " has no homes!";
            sender.sendMessage("§6You have no homes!");
            return true;
        }

        //Construct menu
        int homesCount = user.getKeys("homes").size();
        StringBuilder homesList = new StringBuilder("§e" + homesCount + "/" + user.getMaxHomes() + " §6Homes:§e ");
        int i = 1;
        for (String key : user.getKeys("homes")) {
            homesList.append(key);
            if (i < homesCount) {
                homesList.append("§6,§e ");
            }
            i++;
        }

        //Process the color of, then send, the final menu
        sender.sendMessage(homesList.toString());
        return true;
    }
}
