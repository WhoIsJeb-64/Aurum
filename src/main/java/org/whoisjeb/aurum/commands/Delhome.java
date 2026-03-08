package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumUser;

public class Delhome extends AuricCommand {
    private final Aurum plugin;

    public Delhome(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Command must be run in-game
        if (!isPlayer(sender)) return true;
        Player player = (Player) sender;

        //Get sender's AurumUser instance
        AurumUser user = new AurumUser(player.getUniqueId());
        user.load(player.getUniqueId());

        //Get specified home name
        //If the sender can only set 1 home, it will always be "home"
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("%thing%", "home"));
            return true;
        }
        String homeName = (user.getMaxHomes() > 1) ? args[0] : "home";

        //Check that a home with the given name exists; Return if not
        if (!user.hasProperty("homes." + homeName)) {
            sender.sendMessage(message("error.doesnt-exist")
                    .replace("%thing%", "The home " + homeName));
            return true;
        }

        //Remove the home and inform the player
        user.removeProperty("homes." + homeName);
        sender.sendMessage(message(command).replace("%home%", homeName));
        return true;
    }
}
