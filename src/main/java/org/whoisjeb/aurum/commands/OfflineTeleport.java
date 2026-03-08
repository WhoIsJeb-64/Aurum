package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumUser;
import java.util.UUID;

public class OfflineTeleport extends AuricCommand {
    private final Aurum plugin;

    public OfflineTeleport(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isPlayer(sender)) return true;
        Player player = (Player) sender;

        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("%thing%", "player"));
            return true;
        }
        if (getOnlineTarget(args[0]) != null) {
            sender.sendMessage(message(command, "target-online")
                    .replace("%target%", getOnlineTarget(args[0]).getName()));
            return true;
        }
        if (plugin.uuidManager.getUUIDFromUsername(args[0]) == null) {
            sender.sendMessage(message("error.doesnt-exist").replace("%thing%", "That player"));
            return true;
        }

        UUID uuid = plugin.uuidManager.getUUIDFromUsername(args[0]);
        AurumUser user = new AurumUser(uuid);
        user.load(uuid, false);

        player.teleport(user.getLocation("data.position"));
        sender.sendMessage(message(command, "run")
                .replace("%target%", user.getString("info.name")));
        return true;
    }
}
