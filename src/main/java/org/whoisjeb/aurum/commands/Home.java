package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumUser;

public class Home extends AuricCommand {
    private final Aurum plugin;

    public Home(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Command must be run in-game
        if (!isPlayer(sender)) return true;
        Player player = (Player) sender;

        //Get target's AurumUser instance
        AurumUser user = new AurumUser(player.getUniqueId());
        user.load(player.getUniqueId());

        //Get home name
        //It will alawys be "home" if the sender can only set 1
        String homeName;
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("%thing%", "home"));
            return true;
        } else {
            homeName = (user.getMaxHomes() > 1) ? args[0] : "home";
        }

        //Check that the home exists
        if (!user.hasProperty("homes." + homeName)) {
            sender.sendMessage(message("error.doesnt-exist").replace("%thing%", "That home"));
            return true;
        }

        //Teleport player and send appropiate message
        player.teleport(user.getLocation("homes." + homeName));
        player.sendMessage(message(command).replace("%home%", homeName));
        return true;
    }
}
