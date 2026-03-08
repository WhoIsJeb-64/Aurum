package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;

public class Warps extends AuricCommand {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Warps(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
        this.settings = plugin.settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //If the server has no warps, send a different message and return
        if (settings.getProperty("data.warps") == null || settings.getKeys("data.warps").isEmpty()) {
            sender.sendMessage(message(command, "no-warps"));
            return true;
        }

        //Construct menu
        StringBuilder menu = new StringBuilder(message(command, "head"));
        int i = 1;
        for (String key : settings.getKeys("data.warps")) {
            menu.append((i < settings.getKeys("data.warps").size())
                    ? message(command, "body").replace("%warp%", key)
                    : message(command, "tail").replace("%warp%", key));
            i++;
        }

        //Print menu
        sender.sendMessage(menu.toString());
        return true;
    }
}
