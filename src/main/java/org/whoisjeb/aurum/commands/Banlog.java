package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumPunishments;
import java.util.ArrayList;
import java.util.UUID;

public class Banlog extends AuricCommand {
    private final Aurum plugin;
    private final AurumPunishments punishments;
    private static String issuer(UUID uuid) {
        return "bans." + uuid + ".issuer";
    }
    private static String reason(UUID uuid) {
        return "bans." + uuid + ".reason";
    }
    private String activeMarker(String name) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        return punishments.isBanned(plugin.utils.getUUID(player)) ? "§c[A]§f " : "§7[E]§f ";
    }

    public Banlog(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
        this.punishments = plugin.punishments;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Check if there are any listed bans at all to prevent any errors
        if (!punishments.hasProperty("bans")) {
            sender.sendMessage(message(command, "no-bans"));
            return true;
        }

        //Populate an ArrayList with the uuids for data retrieval
        ArrayList<String> menu = new ArrayList<>();
        ArrayList<UUID> banEntries = new ArrayList<>();
        for (Object entry : punishments.getKeys("bans")) {
            banEntries.add(UUID.fromString(entry.toString()));
        }
        if (banEntries.isEmpty()) {
            sender.sendMessage(message(command, "no-bans"));
            return true;
        }

        //Determine target page and page count, correct target if it's too high
        int page = (args.length >= 1) ? Integer.parseInt(args[0]) : 1;
        int pageCount = (banEntries.size() / 10) + 1;
        if (page < 1 || page > pageCount) page = 1;

        //Construct menu
        menu.add(message(command, "header")
                .replace("%page%", String.valueOf(page))
                .replace("%pageCount%", String.valueOf(pageCount)));
        int i = 1;
        for (UUID uuid : banEntries) {
            //Only print the correct range of entries
            if ((i - 1) >= ((page * 10) - 10) && (i - 1) < (page * 10)) {
                menu.add(message(command, "line")
                        .replace("%activeMarker%", activeMarker(plugin.utils.getUsername(uuid)))
                        .replace("%player%", plugin.utils.getUsername(uuid))
                        .replace("%reason%", punishments.getString(reason(uuid)))
                        .replace("%issuer%", punishments.getString(issuer(uuid))));
                i++;
            }
        }
        sendMessages(sender, menu);
        return true;
    }
}
