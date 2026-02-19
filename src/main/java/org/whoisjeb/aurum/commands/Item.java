package org.whoisjeb.aurum.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.whoisjeb.aurum.Aurum;

public class Item extends AurumCommandBase {
    private final Aurum plugin;

    public Item(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Make sure sender is a player and that there are enough arguments
        if (!validatePlayerhood(sender)) return true;
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify an item!");
            return true;
        }
        if (args[0].equals("0")) {
            sender.sendMessage("§c[!] Invalid item!");
            return true;
        }

        //Determine what item and how much, then the target player
        Material material = Material.matchMaterial(args[0]);
        int quantity;
        if (args.length < 2) quantity = plugin.settings.getInt("general./i-default-quanity", 1);
        else quantity = Integer.parseInt(args[1]);
        Player target = (args.length < 3) ? (Player) sender : getOnlineTarget(args[2]);

        //Give the item(s) and send appropiate messages
        target.getInventory().addItem(new ItemStack(material, quantity));
        sender.sendMessage("§9Gave " + target.getName() +  " §b" + quantity + " §9of§b " + material.name() + "§9.");
        if (!isTargetSender(sender, target)) {
            target.sendMessage("§9" + sender.getName() + " gave you§b " + quantity + " §9of§b " + material.name() + "§9.");
        }
        log.info(sender.getName() + " has given " + target.getName() + " " + quantity + " of " + material.getId() + "!");
        return true;
    }
}
