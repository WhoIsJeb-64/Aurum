package org.whoisjeb.aurum.commands;

import com.projectposeidon.johnymuffin.UUIDManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import org.whoisjeb.aurum.data.User;
import java.util.UUID;
import java.util.logging.Logger;

public class OfflineTeleport extends AurumCommand {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public OfflineTeleport(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isSenderPlayer(sender)) return true;
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
        UUID uuid = UUIDManager.getInstance().getUUIDFromUsername(args[0]);
        User user = new User(plugin, uuid, plugin.userdataDir(uuid));
        user.load(uuid, args[0]);

        player.teleport(user.getLocation("data.position"));
        return true;
    }
}
