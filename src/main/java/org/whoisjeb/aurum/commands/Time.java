package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
public class Time extends AuricCommand {
    private final Aurum plugin;

    public Time(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //If run from console, the world will be the first world on the server
        World world = (sender instanceof Player) ? ((Player) sender).getWorld() : Bukkit.getWorlds().get(0);

        long time = world.getTime() - (world.getTime() % 24000L);
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("{thing}", "time"));
            return true;
        }

        long offset;
        switch(args[0]) {
            case "da": args[0] = "dawn";
            case "dawn":
                offset = 24000L;
                break;
            case "d": args[0] = "day";
            case "day":
                offset = 30000L;
                break;
            case "du": args[0] = "dusk";
            case "dusk":
                offset = 36000L;
                break;
            case "n": args[0] = "night";
            case "night":
                offset = 42000L;
                break;
            default:
                sender.sendMessage(message("error.invalid").replace("{thing}", "time"));
                return true;
        }

        world.setTime(time + offset);
        sender.sendMessage(message(command).replace("{time}", args[0]));
        return true;
    }
}
