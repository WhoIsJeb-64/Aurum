package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import org.whoisjeb.aurum.data.User;
import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

public class Sethome implements CommandExecutor {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Sethome(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            log.info("That command may only be used by a player!");
            return true;
        }
        String homeName = "home";
        if (strings.length < 1) {
            commandSender.sendMessage("§c[!] Please specify a name for the new home!");
            return true;
        } else {
            homeName = strings[0];
        }
        Player player = (Player) commandSender;
        UUID uuid = player.getUniqueId();
        User user = new User(plugin, uuid, new File(plugin.getDataFolder(), "userdata/" + uuid + ".yml")).loadIfUnloaded(player);
        int homesCount = user.getKeys("homes").size();
        int maxHomes = user.getMaxHomes(player, player.getWorld());
        if (homesCount + 1 <= maxHomes) {
            String playerPosition = user.locationToString(player.getLocation());
            user.setProperty("homes." + homeName, playerPosition);
            user.save();
            player.sendMessage("§2Set home§a " + homeName + "§2!");
        } else {
            player.sendMessage("§c[!] You are not authorized to set more than " + maxHomes + " homes!");
        }
        return true;
    }
}
