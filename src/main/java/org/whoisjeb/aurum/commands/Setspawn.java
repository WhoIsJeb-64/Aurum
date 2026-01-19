package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import java.util.logging.Logger;

public class Setspawn implements CommandExecutor {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Setspawn(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            log.info("That command may only be used by a player!");
            return true;
        }
        Player player = (Player) commandSender;
        Location location = player.getLocation();
        String newSpawn = settings.locationToString(location);
        settings.setProperty("general.spawn", newSpawn);
        player.sendMessage("§5Set world spawn successfully!");
        return true;
    }
}
