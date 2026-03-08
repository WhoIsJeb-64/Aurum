package org.whoisjeb.aurum.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumUser;

public class Nickname extends AuricCommand {
    private final Aurum plugin;

    public Nickname(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Make sure that an argument is passed
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("%thing%", "nickname"));
            return true;
        }

        //Set nickname to the given argument; Colorize only if sender is permitted
        String nickname = plugin.colorize(args[0], sender.hasPermission("aurum.color"));

        //Check if a target is specified as the 2nd argument
        OfflinePlayer target = (OfflinePlayer) (args.length < 2 ? sender : getTarget(args[1]));

        //Make sure that a target was resolved
        if (target == null) {
            sender.sendMessage(message("error-doesnt-exist")
                    .replace("%thing%", "That player"));
            return true;
        }

        //Load target's AurumUser instance
        AurumUser user = new AurumUser(plugin.getUUID(target));
        user.load(plugin.getUUID(target));

        //Clear the target's nickname if the argument "clear" was given
        if (nickname.equalsIgnoreCase("clear")) {
            user.removeProperty("info.nickname");
            if (getOnlineTarget(target.getName()) != null) {
                getOnlineTarget(target.getName()).setDisplayName(target.getName());
            }
            sender.sendMessage(message(command, "clear").replace("%target%", target.getName()));
            return true;
        }

        //Get configured nickname prefix
        String nicknamePrefix = plugin.settings.getString("chat.nickname-prefix");

        //Set target's nickname through Aurum, and Minecraft if they're online
        user.setProperty("info.nickname", nickname);
        if (getOnlineTarget(target.getName()) != null) {
            getOnlineTarget(target.getName()).setDisplayName(nicknamePrefix + nickname);
        }
        sender.sendMessage(message(command, "set." + ((sender == target) ? "sender" : "target"))
                .replace("%nickname%", nicknamePrefix + nickname)
                .replace("%target%", target.getName()));

        return true;
    }
}
