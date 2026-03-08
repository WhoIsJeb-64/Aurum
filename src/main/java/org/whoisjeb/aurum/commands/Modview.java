package org.whoisjeb.aurum.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumPunishments;
import org.whoisjeb.aurum.data.AurumUser;
import java.util.ArrayList;
import java.util.UUID;

public class Modview extends AuricCommand {
    private final Aurum plugin;
    private final AurumPunishments punishments;

    public Modview(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
        this.punishments = plugin.punishments;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Get target and their AurumUser instance
        OfflinePlayer target =
                (args.length < 1) ? (OfflinePlayer) sender : (OfflinePlayer) getTarget(args[0]);
        UUID uuid = plugin.getUUID(target);
        AurumUser user = new AurumUser(plugin.getUUID(target));
        user.load(uuid, false);

        //Get target's prefix and group color
        String prefix = (plugin.getPex().getUser(target.getName()).getPrefix() != null)
                ? plugin.getPex().getUser(target.getName()).getPrefix() : null;
        String color = (plugin.getPex().getUser(target.getName()).getOption("color") != null)
                ? plugin.getPex().getUser(target.getName()).getOption("color") : null;

        //Initialize menu
        ArrayList<String> menu = new ArrayList<>();
        menu.add(message(command, "header")
                .replace("%prefix%", (prefix != null) ? prefix : "")
                .replace("%color%", (color != null) ? color : "")
                .replace("%name%", target.getName()));

        //Identifying Information (UUID, IP, Nickname)
        menu.add("- §2UUID:§7 " + uuid);
        menu.add("- §2IP:§7 " + user.getIP());
        if (!user.getString("info.name").equals(target.getName()))
            menu.add("- §2Nickname: " + user.getString("info.name"));

        //Punishments (Bans/Warnings)
        if (target.isBanned())
            menu.add("- §cBanned for:§f " +
                    punishments.getString("bans." + uuid + ".reason") + " §cby§f " + punishments.getString("bans." + uuid + ".issuer"));
        if (punishments.hasProperty("warnings." + uuid))
            for (String warning : punishments.getStringList("warnings." + uuid, null))
                menu.add("- §6Warned for:§f " + warning);

        //Join and last seen dates
        menu.add("- §9First Joined:§f " + user.getFormattedDate("data.firstJoin", "MMM. d, yyyy"));
        if (!target.isOnline())
            menu.add("- §9Last Seen:§f " + user.getFormattedDate("data.lastOnline", "MMM. d, yyyy"));

        //Print menu after all logic is run
        sendMessages(sender, menu);
        return true;
    }
}
