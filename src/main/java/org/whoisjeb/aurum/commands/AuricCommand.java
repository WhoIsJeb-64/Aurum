package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Provides some utility methods for inheritors to have more consice, readable code.
 */
public abstract class AuricCommand implements CommandExecutor {
    private final Aurum plugin;
    public final Logger log = Bukkit.getServer().getLogger();

    protected AuricCommand(Aurum plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        return false;
    }

    /**
     * @param path The path of the desired message.
     * @return The message at the specified path in Aurum's messages.yml.
     */
    public String message(String path) {
        return plugin.utils.colorize(plugin.language.getString(path), true);
    }

    /**
     * @param command The command the message belongs to.
     * @return The message at commands.{command.getName()}
     */
    public String message(Command command) {
        String message = plugin.language.getString("commands." + command.getName());
        return plugin.utils.colorize(message, true);
    }

    /**
     * @param command The command the message belongs to.
     * @param path The rest of the path after commands.{command.getName()}
     * @return The message at commands.{command.getName()}.{path}
     */
    public String message(Command command, String path) {
        String message = plugin.language.getString("commands." + command.getName() + "." + path);
        return plugin.utils.colorize(message, true);
    }

    /**
     * Determines whether a given sender of a command is console or a player. Returns a message as well if the former.
     * @param sender The CommandSender object.
     * @return True if the sender is a player.
     */
    public boolean isPlayer(CommandSender sender) {
        if (sender instanceof Player) return true;
        else {
            sender.sendMessage(message("error.must-be-player"));
            return false;
        }
    }

    /**
     * Retrieves a Player or OfflinePlayer given a name. For online players, their full name may be predicted from a fragment.
     * @param name The given string used to predict the intended target's name. Note prediction only works for online players.
     * @return The intended target, either a Player or OfflinePlayer, as an Object that must be properly cast.
     */
    public Object getTarget(String name) {
        if (getOnlineTarget(name) != null) return getOnlineTarget(name);

        return (Bukkit.getOfflinePlayer(name) == null) ? null : Bukkit.getOfflinePlayer(name);
    }

    /**
     * Given a string, either retrives the online player whose name is the string, or uses the string to predict the intended target.
     * Not that name predcition will return the first player whose name starts with the fragment, so use the feature accordingly.
     * @param name The given string used to predict the intended target's name.
     * @return The Player object if a prediction is made, otherwise null.
     */
    public Player getOnlineTarget(String name) {
        if (Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name);

        //Basic implementation of name prediction
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().startsWith(name)) return player;
        }
        return null;
    }

    /**
     * Sends an ArrayList of strings in 1 line of code.
     * @param sender The instance of CommandSender to whom the message will be sent.
     * @param messages The ArrayList to be sent.
     */
    public void sendMessages(CommandSender sender, ArrayList<String> messages) {
        for (String line : messages)
            sender.sendMessage(line);
    }

    /**
     * Sends a List of strings in 1 line of code.
     * @param sender The instance of CommandSender to whom the message will be sent.
     * @param messages The List to be sent.
     */
    public void sendMessages(CommandSender sender, List<String> messages) {
        for (String line : messages)
            sender.sendMessage(line);
    }
}
