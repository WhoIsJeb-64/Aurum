package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.User;

public class Delhome extends AurumCommandBase {
    private final Aurum plugin;

    public Delhome(Aurum plugin) {
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
            sender.sendMessage("§c[!] Please specify a home to delete!");
            return true;
        }
        String homeName = (user.getMaxHomes() > 1) ? args[0] : "home";

        //Check that a home with the given name exists
        if (!user.hasProperty("homes." + homeName)) {
            player.sendMessage("§c[!] That home does not exist!");
            return true;
        }

        //Remove the home and inform the player
        user.removeProperty("homes." + homeName);
        player.sendMessage("§2Deleted home§a " + homeName + "§2!");
        return true;
    }
}
