package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.logging.Logger;

public abstract class AurumCommandBase implements CommandExecutor {
    private static final Logger log = Bukkit.getServer().getLogger();

    protected AurumCommandBase() {}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        return false;
    }

    public boolean isSenderPlayer(CommandSender sender) {
        if (sender instanceof Player) return true;
        else {
            log.info("That command may only be used by a player!");
            return false;
        }
    }

    public boolean isTargetSender(CommandSender sender, Player target) {
        Player player = (Player) sender;
        return player.getUniqueId() == target.getUniqueId();
    }
}
