package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumUser;

public class Sethome extends AuricCommand {
    private final Aurum plugin;

    public Sethome(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Make sure the command is being run in-game; Load AurumUser object
        if (!isPlayer(sender)) return true;
        Player player = (Player) sender;
        AurumUser user = new AurumUser(player.getUniqueId());
        user.load(player.getUniqueId());

        //Get given home name, unless they can only set 1, in which case it is set to "home"
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("%thing%", "home"));
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
            user.setProperty("homes." + homeName, player.getLocation());
            player.sendMessage(message(command, "run").replace("%home%", homeName));
        } else {
            player.sendMessage(message(command, "cannot-exceed-max")
                    .replace("%maxHomes%", String.valueOf(maxHomes))
                    .replace("%plural%", (maxHomes == 1) ? "s" : ""));
        }
        return true;
    }
}
