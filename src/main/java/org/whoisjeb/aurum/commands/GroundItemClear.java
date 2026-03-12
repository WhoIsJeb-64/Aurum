package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class GroundItemClear extends AuricCommand {
    private final Aurum plugin;
    private static int amountCleared;

    public GroundItemClear(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
        amountCleared = 0;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //If run from console, the world will be the first world on the server
        World world = (sender instanceof Player) ? ((Player) sender).getWorld() : Bukkit.getWorlds().get(0);

        //Iterate through all entities in the world, clearing any that are items
        for (Entity entity : world.getEntities()) {
            if (entity instanceof CraftItem) clear(entity);
        }

        //Send appropiate message and reset amountCleared
        sender.sendMessage(message(command)
                .replace("{amount}", String.valueOf(amountCleared))
                .replace("{plural}", (amountCleared == 1) ? "" : "s"));
        amountCleared = 0;
        return true;
    }

    private void clear(Entity entity) {
        entity.remove();
        amountCleared++;
    }
}
