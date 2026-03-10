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

    public String message(String path) {
        return plugin.utils.colorize(plugin.language.getString(path), true);
    }

    public String message(Command command) {
        String message = plugin.language.getString("commands." + command.getName());
        return plugin.utils.colorize(message, true);
    }

    public String message(Command command, String path) {
        String message = plugin.language.getString("commands." + command.getName() + "." + path);
        return plugin.utils.colorize(message, true);
    }

    public boolean isPlayer(CommandSender sender) {
        if (sender instanceof Player) return true;
        else {
            sender.sendMessage(message("error.must-be-player"));
            return false;
        }
    }

    public Object getTarget(String name) {
        if (getOnlineTarget(name) != null) return getOnlineTarget(name);

        return (Bukkit.getOfflinePlayer(name) == null) ? null : Bukkit.getOfflinePlayer(name);
    }

    public Player getOnlineTarget(String name) {
        if (Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name);

        //Basic implementation of name prediction
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().startsWith(name)) return player;
        }
        return null;
    }

    public void sendMessages(CommandSender sender, ArrayList<String> messages) {
        for (String line : messages)
            sender.sendMessage(line);
    }

    public void sendMessages(CommandSender sender, List<String> messages) {
        for (String line : messages)
            sender.sendMessage(line);
    }
}
