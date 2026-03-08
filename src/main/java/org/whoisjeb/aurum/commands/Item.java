package org.whoisjeb.aurum.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.whoisjeb.aurum.Aurum;

public class Item extends AuricCommand {
    private final Aurum plugin;

    public Item(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Make sure sender is a player and that there are enough arguments
        if (!isPlayer(sender)) return true;
        if (args.length < 1) {
            sender.sendMessage(message("error.specify").replace("a %thing%", "an item"));
            return true;
        }
        if (args[0].equals("0")) {
            sender.sendMessage(message("error.invalid").replace("%thing%", "item"));
            return true;
        }

        //Determine what item and how much to give
        Material material = Material.matchMaterial(args[0]);
        int quantity;
        if (args.length < 2) quantity = plugin.settings.getInt("general.stack-default-quanity", 1);
        else quantity = Integer.parseInt(args[1]);

        //Target is the sender if no 3rd argument is passed
        Player target = (args.length < 3) ? (Player) sender : getOnlineTarget(args[2]);

        //Make sure the item is valid
        if (material == null) {
            sender.sendMessage(message("error.invalid").replace("%thing%", "item"));
            return true;
        }

        //Give the ItemStack and send appropiate messages
        target.getInventory().addItem(new ItemStack(material, quantity));
        sender.sendMessage(message(command, "sender")
                .replace("%target%", target.getName())
                .replace("%quantity%", String.valueOf(quantity))
                .replace("%item%", material.name().toLowerCase().replaceAll("_", " ")));
        if (sender != target) {
            sender.sendMessage(message(command, "target")
                    .replace("%sender%", (sender instanceof Player) ? sender.getName() : "Console")
                    .replace("%quantity%", String.valueOf(quantity))
                    .replace("%item%", material.name().toLowerCase().replaceAll("_", " ")));
        }
        return true;
    }
}
