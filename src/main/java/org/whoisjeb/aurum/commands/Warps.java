package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;

public class Warps extends AurumCommandBase {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Warps(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isSenderPlayer(sender)) return true;

        if (settings.getKeys("general.warps").isEmpty()) {
            sender.sendMessage("§5There are no warps!");
            return true;
        }
        StringBuilder warpList = new StringBuilder("§5Warps:§d ");
        int i = 1;
        for (String key : settings.getKeys("general.warps")) {
            warpList.append(key);
            if (i < settings.getKeys("general.warps").size()) {
                warpList.append("§5,§d ");
            }
            i++;
        }
        sender.sendMessage(warpList.toString());
        return true;
    }
}
