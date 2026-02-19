package org.whoisjeb.aurum.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumAPI;
import org.whoisjeb.aurum.data.Punishments;
import org.whoisjeb.aurum.data.User;
import java.util.ArrayList;
import java.util.UUID;

public class Modview extends AurumCommandBase {
    private final Aurum plugin;
    private final Punishments punishments;

    public Modview(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
        this.punishments = plugin.punishments;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Initialize target, load corresponding User object
        OfflinePlayer target =
                (args.length < 1) ? (OfflinePlayer) sender : (OfflinePlayer) getTarget(args[0]);
        UUID uuid = plugin.getUUID(target);
        User user = new User(plugin.getUUID(target));
        user.load();

        //Construct menu
        ArrayList<String> menu = new ArrayList<>();
        String prefix = plugin.getPex().getUser(target.getName()).getPrefix();
        menu.add("§7[§8============= " + plugin.colorize(prefix, true) + target.getName() + " §8=============§7]");

        //Identifying Information (UUID, IP, Nickname, Rank)
        menu.add("- §2UUID:§7 " + uuid);
        menu.add("- §2IP:§7 " + user.getIP());
        if (!user.getString("info.name").equals(target.getName()))
            menu.add("- §2Nickname: " + user.getString("info.name"));

        //Punishments (Bans/Warnings)
        if (target.isBanned())
            menu.add("- §cBanned for:§f " + AurumAPI.getBanReason(uuid) + " §cby§f " + AurumAPI.getBanIssuer(uuid));
        if (punishments.hasProperty("warnings." + uuid))
            for (String warning : punishments.getStringList("warnings." + uuid, null))
                menu.add("- §6Warned for:§f " + warning);

        //Join and last seen dates
        menu.add("- §9First Joined:§f " + user.getDate("data.firstJoin"));
        if (!target.isOnline())
            menu.add("- §9Last Seen:§f " + user.getDate("data.lastOnline"));

        sendMessages(sender, menu);
        return true;
    }
}
