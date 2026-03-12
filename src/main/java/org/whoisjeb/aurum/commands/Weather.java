package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import java.util.ArrayList;

public class Weather extends AuricCommand {
    private final Aurum plugin;

    public Weather(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Messages are sent after all logic is run
        ArrayList<String> menu = new ArrayList<>();

        //If run from console, the world will be the first world on the server
        World world = (sender instanceof Player) ? ((Player) sender).getWorld() : Bukkit.getWorlds().get(0);

        //If no argument is passed, it will be the opposite of the current weather
        if (args.length < 1) {
            args = new String[]{world.hasStorm() ? "clear" : "rain"};
        }

        //Set the world's weather based on the argument and send appropiate message
        if (args[0].equalsIgnoreCase("clear")) {
            world.setStorm(false);
            menu.add(message(command).replace("{weather}", args[0]));
        } else if (args[0].equalsIgnoreCase("rain")) {
            world.setStorm(true);
            menu.add(message(command).replace("{weather}", args[0]));
        } else {
            menu.add(message("error.invalid").replace("{thing}", "argument"));
        }

        sendMessages(sender, menu);
        return true;
    }
}
