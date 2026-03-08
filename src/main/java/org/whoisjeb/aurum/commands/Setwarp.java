package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class Setwarp extends AuricCommand {
    private final Aurum plugin;

    public Setwarp(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isPlayer(sender)) return true;

        String warpName;
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("%thing%", "name"));
            return true;
        } else {
            warpName = args[0];
        }

        Player player = (Player) sender;
        plugin.settings.setProperty("data.warps." + warpName, player.getLocation());
        player.sendMessage(message(command).replace("%warp%", warpName));
        return true;
    }
}
