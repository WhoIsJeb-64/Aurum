package org.whoisjeb.aurum.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import java.util.Arrays;

public class Ban extends AurumCommandBase {
    private final Aurum plugin;

    public Ban(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Determine who will be banned
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify a player!");
            return true;
        }
        OfflinePlayer target = (OfflinePlayer) getTarget(args[0]);
        if (target == null) {
            sender.sendMessage("§c[!] No match was found!");
            return true;
        }

        //Determine the reason and sender's playerhood
        String reason = (args.length < 2) ? "Rule breaking" : String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        String issuer = (sender instanceof Player) ? sender.getName() : "Console";

        //Issue ban and send appropiate messages
        plugin.punishments.ban(target, reason, issuer);
        String message = target.isBanned()
                ? "§4Banned§c " + target.getName() + "§2!" : "§4[!] Failed to ban§c " + target.getName() + "§4!";
        sender.sendMessage(message);
        return true;
    }
}
