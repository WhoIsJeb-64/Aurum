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

public class Delhome extends AurumCommand {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Delhome(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isSenderPlayer(sender)) {
            return true;
        }
        String homeName;
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        User user = new User(plugin, uuid, plugin.userdataDir(uuid)).loadIfUnloaded(player);
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a home to delete!");
            return true;
        } else {
            homeName = (user.getMaxHomes(player) > 1) ? args[0] : "home";
        }
        if (!user.hasProperty("homes." + homeName)) {
            player.sendMessage("§c[!] That home does not exist!");
            return true;
        }
        user.removeProperty("homes." + homeName);
        user.save();
        player.sendMessage("§2Deleted home§a " + homeName + "§2!");
        return true;
    }
}
