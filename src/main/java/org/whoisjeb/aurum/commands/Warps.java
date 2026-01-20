package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import java.util.logging.Logger;

public class Warps implements CommandExecutor {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Warps(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            log.info("That command may only be used by a player!");
            return true;
        }
        StringBuilder warpList = new StringBuilder("§5Warps:§d ");
        int i = 1;
        for (String key : settings.getKeys("general.warps")) {
            if (i == settings.getKeys("general.warps").size()) {
                warpList.append(key);
            } else {
                warpList.append(key).append("§5,§d ");
            }
            i++;
        }
        commandSender.sendMessage(warpList.toString());
        return true;
    }
}
