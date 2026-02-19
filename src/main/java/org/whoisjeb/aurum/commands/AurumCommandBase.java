package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class AurumCommandBase implements CommandExecutor {
    private final Aurum plugin;
    public final Logger log = Bukkit.getServer().getLogger();

    protected AurumCommandBase(Aurum plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        return false;
    }

    /**
     * Determines whether a given sender of a command is console or a player. Returns a message as well if the former.
     * @param sender The CommandSender object.
     * @return True if the sender is a player.
     */
    public boolean validatePlayerhood(CommandSender sender) {
        if (sender instanceof Player) return true;
        else {
            sender.sendMessage("[!] This command may only be executed by a player!");
            return false;
        }
    }

    /**
     * Retrieves a Player or OfflinePlayer given a name. For online players, their full name may be predicted from a fragment.
     * @param input The given string used to predict the intended target's name. Note prediction only works for online players.
     * @return The intended target, either a Player or OfflinePlayer, as an Object that must be properly cast.
     */
    public Object getTarget(String input) {
        if (getOnlineTarget(input) != null) return getOnlineTarget(input);

        return (Bukkit.getOfflinePlayer(input) == null) ? null : Bukkit.getOfflinePlayer(input);
    }

    /**
     * Given a string, either retrives the online player whose name is the string, or uses the string to predict the intended target.
     * Not that name predcition will return the first player whose name starts with the fragment, so use the feature accordingly.
     * @param input The given string used to predict the intended target's name.
     * @return The Player object if a prediction is made, otherwise null.
     */
    public Player getOnlineTarget(String input) {
        if (Bukkit.getPlayer(input) != null) return Bukkit.getPlayer(input);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().startsWith(input)) return player;
        }
        return null;
    }

    /**
     * Determines whether the sender of a command and the intended target are the same player.
     * @param sender The CommandSender object.
     * @param target The other player.
     * @return Whether the sender and the target are the same player.
     */
    public boolean isTargetSender(CommandSender sender, OfflinePlayer target) {
        Player player = (Player) sender;
        return player.getUniqueId() == plugin.uuidManager.getUUIDFromUsername(target.getName());
    }

    public void sendMessages(CommandSender player, ArrayList<String> messages) {
        for (String line : messages) {
            player.sendMessage(line);
        }
    }
}
