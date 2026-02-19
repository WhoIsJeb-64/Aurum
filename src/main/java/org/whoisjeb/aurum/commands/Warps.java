package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;

public class Warps extends AurumCommandBase {
    private final Aurum plugin;

    public Warps(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!validatePlayerhood(sender)) return true;

        if (plugin.settings.getProperty("general.warps") == null ||plugin.settings.getKeys("general.warps").isEmpty()) {
            sender.sendMessage("§5There are no warps!");
            return true;
        }
        StringBuilder warpList = new StringBuilder("§5Warps:§d ");
        int i = 1;
        for (String key : plugin.settings.getKeys("general.warps")) {
            warpList.append(key);
            if (i < plugin.settings.getKeys("general.warps").size()) {
                warpList.append("§5,§d ");
            }
            i++;
        }
        sender.sendMessage(warpList.toString());
        return true;
    }
}
