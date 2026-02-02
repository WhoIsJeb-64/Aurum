package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;

public class Time extends AurumCommandBase {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Time(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        World world = (sender instanceof Player) ? ((Player) sender).getWorld() : Bukkit.getWorlds().get(0);
        long time = world.getTime() - (world.getTime() % 24000L);
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a time! (day, night, dawn, dusk)");
            return true;
        } else if (args[0].equalsIgnoreCase("dawn")) {
            world.setTime(time + 24000L);
        } else if (args[0].equalsIgnoreCase("day")) {
            world.setTime(time + 30000L);
        } else if (args[0].equalsIgnoreCase("dusk")) {
            world.setTime(time + 36000L);
        } else if (args[0].equalsIgnoreCase("night")) {
            world.setTime(time + 42000L);
        } else {
            sender.sendMessage("§c[!] Invalid time! Valid times: day, night, dawn, dusk");
            return true;
        }
        sender.sendMessage("§3Set time to " + args[0] + "!");
        return true;
    }
}
