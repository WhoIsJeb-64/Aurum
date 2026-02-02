package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import org.whoisjeb.aurum.data.User;
import java.util.UUID;

public class Home extends AurumCommandBase {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Home(Aurum plugin, AurumSettings settings) {
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
            sender.sendMessage("§c[!] Please specify a home!");
            return true;
        } else {
            homeName = (user.getMaxHomes(player) > 1) ? args[0] : "home";
        }
        if (!user.hasProperty("homes." + homeName)) {
            player.sendMessage("§c[!] That home does not exist!");
            return true;
        }
        player.teleport(user.getLocation("homes." + homeName));
        player.sendMessage("§5Teleported to home§d " + homeName + "§5!");
        return true;
    }
}
