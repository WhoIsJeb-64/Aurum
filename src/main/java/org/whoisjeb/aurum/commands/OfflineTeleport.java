package org.whoisjeb.aurum.commands;

import com.projectposeidon.johnymuffin.UUIDManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.User;
import java.util.UUID;

public class OfflineTeleport extends AurumCommandBase {
    private final Aurum plugin;

    public OfflineTeleport(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!validatePlayerhood(sender)) return true;
        Player player = (Player) sender;

        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a player!");
            return true;
        }
        if (Bukkit.getPlayer(args[0]) != null) {
            sender.sendMessage("§c[!] Player is online!");
            return true;
        }
        if (UUIDManager.getInstance().getUUIDFromUsername(args[0]) == null) {
            sender.sendMessage("§c[!] Player does not exist!");
            return true;
        }

        UUID uuid = plugin.getUUID(args[0]);
        User user = new User(uuid);
        user.load(uuid);

        player.teleport(user.getLocation("data.position"));
        return true;
    }
}
