package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumUser;
import java.io.File;
import java.util.*;

public class Balancetop extends AuricCommand {
    private final Aurum plugin;

    public Balancetop(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Initialize entries and the userdata files
        HashMap<String, Double> entries = new HashMap<>();
        File[] dir = new File(plugin.getDataFolder(), "userdata").listFiles();

        //Validate that the userdata directory is not null
        if (dir == null) {
            sender.sendMessage(message(command, "no-users"));
            return true;
        }
        for (File file : dir) {
            //String.substring() is used to remove the file extension, .yml
            UUID uuid = UUID.fromString(file.getName().substring(0, (file.getName().length() - 4)));
            AurumUser user = new AurumUser(uuid);
            user.load(uuid);
            entries.put(user.getString("info.name"), user.getDouble("economy.balance"));
        }

        //Sort entries, purging it of broke players
        entries = sortedEntries(entries);

        //Determine target page and page count, correct target if it's too high
        int page = (args.length >= 1) ? Integer.parseInt(args[0]) : 1;
        int pageCount = (entries.size() / 10) + 1;
        if (page < 1 || page > pageCount) page = 1;

        //For tracking total money in economy
        double serverTotal = 0.00;

        //Construct menu
        ArrayList<String> menu = new ArrayList<>();
        menu.add(message(command, "header")
                .replace("%page%", String.valueOf(page))
                .replace("%pageCount%", String.valueOf(pageCount)));
        int i = 1;
        for (Map.Entry<String, Double> entry : entries.entrySet()) {
            //Only print the correct range of entries
            if ((i - 1) >= ((page * 10) - 10) && (i - 1) < (page * 10)) {
                menu.add(message(command, "line")
                        .replace("%name%", entry.getKey())
                        .replace("%rank%", String.valueOf(i))
                        .replace("%color%", plugin.getPex().getUser(entry.getKey()).getOption("color"))
                        .replace("%prefix%", plugin.getPex().getUser(entry.getKey()).getPrefix())
                        .replace("%balance%", String.valueOf(entry.getValue())));
                i++;
            }
            serverTotal += entry.getValue();
        }

        //Server total
        menu.add(message(command, "server-total").replace("%total%", String.valueOf(serverTotal)));

        sendMessages(sender, menu);
        return true;
    }

    private static HashMap<String, Double> sortedEntries(HashMap<String, Double> entries) {
        //Create a list from elements of HashMap, then sort it
        List<Map.Entry<String, Double>> list = new LinkedList<>(entries.entrySet());
        list.sort(Map.Entry.comparingByValue());

        //Reverse list so that the highest balances are the highest ranks
        Collections.reverse(list);

        //Put the data from sorted list into hashmap
        HashMap<String, Double> sortedEntries = new LinkedHashMap<>();
        for (Map.Entry<String, Double> aa : list)
            if (aa.getValue() > 0) sortedEntries.put(aa.getKey(), aa.getValue());

        return sortedEntries;
    }
}
