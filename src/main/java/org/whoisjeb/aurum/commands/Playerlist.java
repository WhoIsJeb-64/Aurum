package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Playerlist extends AurumCommand {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Playerlist(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            String prefix = plugin.getPex().getUser(player).getPrefix();
            playerNames.add(prefix.substring(0, 2) + player.getName());
        }
        int playerCount = playerNames.size();
        int maxPlayers = Bukkit.getMaxPlayers();

        StringBuilder playerList =
                new StringBuilder("§9(§b" + playerCount + "§9/§b" + maxPlayers + "§9)" + " Player(s) Online:§f ");
        int i = 1;
        for (String name : playerNames) {
            playerList.append(name);
            if (i < playerCount) {
                playerList.append("§f, ");
            }
            i++;
        }
        sender.sendMessage(plugin.processColor(playerList.toString(), true));
        return true;
    }
}
