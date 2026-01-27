package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import java.util.logging.Logger;

public class Item extends AurumCommand {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Item(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!isSenderPlayer(sender)) return true;
        if (args.length < 1) {
            sender.sendMessage("§c[!] Please specify an item!");
            return true;
        }
        Material material = Material.matchMaterial(args[0]);

        int quanity;
        if (args.length < 2) quanity = settings.getInt("general./i-default-quanity", 1);
        else quanity = Integer.parseInt(args[1]);

        Player target = (Player) sender;

        target.getInventory().addItem(new ItemStack(material, quanity));
        sender.sendMessage("§9Gave " + target.getName() +  " §b" + quanity + " §9of§b " + material.name() + "§9.");
        log.info(sender.getName() + " has given " + target.getName() + " " + quanity + " of " + material.getId() + "!");
        return true;
    }
}
