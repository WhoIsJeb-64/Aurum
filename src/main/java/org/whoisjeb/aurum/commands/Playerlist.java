package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.*;

public class Playerlist extends AuricCommand {
    private final Aurum plugin;

    public Playerlist(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Get amount of players online and the server's player cap
        int playerCount = Bukkit.getOnlinePlayers().length;
        int maxPlayers = Bukkit.getMaxPlayers();

        //Send a different message if there are 0 players online
        if (playerCount == 0) {
            sender.sendMessage(message(command, "no-players"));
            return true;
        }

        //Sort players by rank
        HashMap<Player, Integer> players = sort(Bukkit.getOnlinePlayers());

        //Construct and print menu using the default format
        if (!plugin.settings.getBoolean("compact-playerlist", false)) {
            //Determine target page and page count, correct target if it's too high
            int page = (args.length >= 1) ? Integer.parseInt(args[0]) : 1;
            int pageCount = (playerCount / 10) + 1;
            if (page < 1 || page > pageCount) page = 1;

            ArrayList<String> menu = new ArrayList<>();
            menu.add(message(command, "header")
                    .replace("%playerCount%", String.valueOf(playerCount))
                    .replace("%maxPlayers%", String.valueOf(maxPlayers))
                    .replace("%page%", String.valueOf(page))
                    .replace("%pageCount%", String.valueOf(pageCount)));
            int i = 1;
            for (Player player : players.keySet()) {
                //Only print the correct range of entries
                if ((i - 1) >= ((page * 10) - 10) && (i - 1) < (page * 10)) {
                    menu.add(message(command, "line")
                            .replace("%prefix%", plugin.utils.getPrefix(player.getName()))
                            .replace("%color%", plugin.utils.getColor(player.getName()))
                            .replace("%name%", player.getName()));
                }
                i++;
            }
            sendMessages(sender, menu);
        }

        //Construct and print menu using the compact format
        else {
            StringBuilder menu = new StringBuilder(message(command, "compact.head")
                    .replace("%playerCount%", String.valueOf(playerCount))
                    .replace("%maxPlayers%", String.valueOf(maxPlayers)));
            int i = 1;
            for (Player player : Bukkit.getOnlinePlayers()) {
                menu.append((i < playerCount)
                        ? message(command, "compact.body")
                        .replace("%name%", player.getName())
                        .replace("%color%", plugin.utils.getColor(player.getName()))
                        : message(command, "compact.tail")
                        .replace("%name%", player.getName())
                        .replace("%color%", plugin.utils.getColor(player.getName())));
                i++;
            }
            sender.sendMessage(menu.toString());
        }
        return true;
    }

    private HashMap<Player, Integer> sort(Player[] players) {
        //Populate HashMap with players and their group's ranks
        HashMap<Player, Integer> hierarchy = new HashMap<>();
        for (Player player : players) {
            hierarchy.put(player, Integer.valueOf(PermissionsEx.getPermissionManager().getUser(player).getOption("rank")));
        }

        //Create a list from elements of HashMap, then sort it
        List<Map.Entry<Player, Integer>> list = new LinkedList<>(hierarchy.entrySet());
        list.sort(Map.Entry.comparingByValue());

        //Put the data from sorted list into hashmap
        HashMap<Player, Integer> sortedEntries = new LinkedHashMap<>();
        for (Map.Entry<Player, Integer> aa : list)
            if (aa.getValue() > 0) sortedEntries.put(aa.getKey(), aa.getValue());

        return sortedEntries;
    }
}
