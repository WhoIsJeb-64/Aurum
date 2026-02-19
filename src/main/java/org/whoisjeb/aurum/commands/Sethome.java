package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.User;

public class Sethome extends AurumCommandBase {
    private final Aurum plugin;

    public Sethome(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Make sure the command is being run in-game; Load User object
        if (!validatePlayerhood(sender)) return true;
        Player player = (Player) sender;
        User user = new User(player.getUniqueId()).loadIfUnloaded(player);

        //Get given home name, unless they can only set 1, in which case it is set to "home"
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a name for the new home!");
            return true;
        }
        String homeName = (user.getMaxHomes() > 1) ? args[0] : "home";

        //Determine current amount of homes the player has
        int homesCount;
        if (user.getKeys("homes") == null) {
            homesCount = 0;
        } else {
            homesCount = user.getKeys("homes").size();
        }
        int maxHomes = user.getMaxHomes();

        //Check if player is allowed to set a new home, then send appropiate message
        if (homesCount + 1 <= maxHomes) {
            String playerPosition = user.locationToString(player.getLocation());
            user.setProperty("homes." + homeName, playerPosition);
            player.sendMessage("§2Set home§a " + homeName + "§2!");
        } else {
            player.sendMessage("§c[!] You are not authorized to set more than " + maxHomes + " homes!");
        }
        return true;
    }
}
