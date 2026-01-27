package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import org.whoisjeb.aurum.data.User;
import java.util.UUID;
import java.util.logging.Logger;

public class Sethome extends AurumCommand {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Sethome(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isSenderPlayer(sender)) return true;
        Player player = (Player) sender;

        String homeName;
        UUID uuid = player.getUniqueId();
        User user = new User(plugin, uuid, plugin.userdataDir(uuid)).loadIfUnloaded(player);
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a name for the new home!");
            return true;
        } else {
            homeName = (user.getMaxHomes(player) > 1) ? args[0] : "home";
        }
        int homesCount = user.getKeys("homes").size();
        int maxHomes = user.getMaxHomes(player);
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
