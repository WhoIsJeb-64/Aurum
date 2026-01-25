package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import org.whoisjeb.aurum.data.TeleportRequest;
import java.util.logging.Logger;

public class Teleportdeny extends AurumCommand {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Teleportdeny(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isSenderPlayer(sender)) return true;
        Player player = (Player) sender;
        TeleportRequest request = null;

        if (args.length < 1) {
            for (TeleportRequest cacheEntry : plugin.tpRequestCache()) {
                if (cacheEntry.getTarget() == player) {
                    request = cacheEntry;
                    break;
                }
            }
        }
        else if (Bukkit.getPlayer(args[0]) != null) {
            for (TeleportRequest cacheEntry : plugin.tpRequestCache()) {
                if (cacheEntry.getTarget() == player && cacheEntry.getOwner() == Bukkit.getPlayer(args[0])) {
                    request = cacheEntry;
                    break;
                }
            }
        } else {
            player.sendMessage("§c[!] Invalid player!");
            return true;
        }

        if (request != null) request.deny(); else player.sendMessage("§c[!] Request not found!");
        return true;
    }
}
