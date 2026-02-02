package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import org.whoisjeb.aurum.data.User;
import java.io.File;
import java.util.UUID;

public class Homes extends AurumCommandBase {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Homes(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isSenderPlayer(sender)) return true;
        Player player = (Player) sender;

        UUID uuid = player.getUniqueId();
        User user = new User(plugin, uuid, new File(plugin.getDataFolder(), "userdata/" + uuid + ".yml")).loadIfUnloaded(player);
        if (user.getKeys("homes") == null || user.getKeys("homes").isEmpty()) {
            player.sendMessage("§6You have no homes!");
            return true;
        }

        StringBuilder homesList = new StringBuilder("§6Homes:§e ");
        int i = 1;
        for (String key : user.getKeys("homes")) {
            homesList.append(key);
            if (i < user.getKeys("homes").size()) {
                homesList.append("§6,§e ");
            }
            i++;
        }
        player.sendMessage(homesList.toString());
        return true;
    }
}
