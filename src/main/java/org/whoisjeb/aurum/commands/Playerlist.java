package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import java.util.ArrayList;

public class Playerlist extends AurumCommandBase {
    private final Aurum plugin;

    public Playerlist(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Populate list of online players names, with the first 2 chars of their prefixes for color
        ArrayList<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            String prefix = plugin.getPex().getUser(player).getPrefix();
            playerNames.add(prefix.substring(0, 2) + player.getName());
        }

        int playerCount = playerNames.size();
        int maxPlayers = Bukkit.getMaxPlayers();

        //Construct menu
        StringBuilder playerList =
                new StringBuilder("§9§b" + playerCount + "/" + maxPlayers + " §9Player(s) Online:§f ");
        int i = 1;
        for (String name : playerNames) {
            playerList.append(name);
            if (i < playerCount) {
                playerList.append("§f, ");
            }
            i++;
        }

        //Process the color of, then send, the final menu
        sender.sendMessage(plugin.colorize(playerList.toString(), true));
        return true;
    }
}
