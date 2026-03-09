package org.whoisjeb.aurum.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumUser;

public class Homes extends AuricCommand {
    private final Aurum plugin;

    public Homes(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Sender must be authorized to view to others' homes
        OfflinePlayer target = (args.length < 1) ? (Player) sender : (OfflinePlayer) getTarget(args[0]);
        if (sender == target && !sender.hasPermission("aurum.home.others")) {
            sender.sendMessage(message(command, "deny-other"));
            return true;
        }

        //Get target's AurumUser instance
        AurumUser user = new AurumUser(plugin.uuidManager.getUUIDFromUsername(target.getName()));
        user.load(plugin.getUUID(target), false);

        //If the target has no homes, send a different message and return
        if (user.getKeys("homes") == null || user.getKeys("homes").isEmpty()) {
            String message = message(command, "no-homes." + ((sender == target) ? "sender" : "target"));
            sender.sendMessage(message.replace("%target%", target.getName()));
            return true;
        }

        //Construct menu
        int homesCount = user.getKeys("homes").size();
        StringBuilder menu = new StringBuilder(message(command, "head")
                .replace("%homesCount%", String.valueOf(homesCount))
                .replace("%maxHomes%", String.valueOf(user.getMaxHomes())));
        int i = 1;
        for (String key : user.getKeys("homes")) {
            menu.append((i < homesCount)
                    ? message(command, "body").replace("%home%", key)
                    : message(command, "tail").replace("%home%", key));
            i++;
        }

        //Print menu
        sender.sendMessage(menu.toString());
        return true;
    }
}
