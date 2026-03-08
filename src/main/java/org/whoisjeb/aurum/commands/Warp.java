package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;

public class Warp extends AuricCommand {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Warp(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
        this.settings = plugin.settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isPlayer(sender)) return true;
        Player player = (Player) sender;

        String warpName;
        if (args.length < 1) {
            sender.sendMessage(message("general.specify").replace("%thing%", "warp"));
            return true;
        } else {
            warpName = args[0];
        }
        if (!settings.hasProperty("data.warps." + warpName)) {
            sender.sendMessage(message("general.does-not-exist").replace("%thing%", "That warp"));
            return true;
        }
        if (settings.getBoolean("general.per-warp-perms", false)) {
            if (!player.hasPermission("aurum.warp." + warpName)) {
                player.sendMessage(message(command, "no-perm").replace("%warp%", warpName));
                return true;
            }
        }
        player.teleport(settings.getLocation("data.warps." + warpName));
        player.sendMessage(message(command, "run").replace("%warp%", warpName));
        return true;
    }
}
