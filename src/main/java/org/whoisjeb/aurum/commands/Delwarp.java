package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;

public class Delwarp extends AuricCommand {
    private final Aurum plugin;

    public Delwarp(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Warp name must be specified
        String warpName;
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("%thing%", "warp"));
            return true;
        } else {
            warpName = args[0];
        }

        //Check that a warp with the given name exists; Return if not
        if (!plugin.settings.hasProperty("general.warps." + warpName)) {
            sender.sendMessage(message("error.doesnt-exist")
                    .replace("%thing%", "The warp " + warpName));
            return true;
        }

        //Remove the warp and inform the player
        plugin.settings.removeProperty("general.warps." + warpName);
        sender.sendMessage(message(command).replace("%home%", warpName));
        return true;
    }
}
