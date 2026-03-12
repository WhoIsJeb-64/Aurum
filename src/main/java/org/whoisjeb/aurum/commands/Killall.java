package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftAnimals;
import org.bukkit.craftbukkit.entity.CraftCreature;
import org.bukkit.craftbukkit.entity.CraftMonster;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class Killall extends AuricCommand {
    private final Aurum plugin;
    private static int amountKilled;

    public Killall(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
        amountKilled = 0;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Get the desired subset of mobs to butcher
        String arg = (args.length < 1) ? "all" : args[0];

        //Make sure the argument is valid
        if (!(arg.equals("all") || arg.equals("passive") || arg.equals("hostile"))) {
            sender.sendMessage(message("error.command-usage")
                    .replace("{usage}", command.getUsage()));
            return true;
        }

        //If run from console, the world will be the first world on the server
        World world = (sender instanceof Player) ? ((Player) sender).getWorld() : Bukkit.getWorlds().get(0);

        //Iterate through world's living entities, killing them if they're the correct type
        for (LivingEntity entity : world.getLivingEntities()) {
            if (entity instanceof CraftCreature) {
                if (arg.equals("passive") && entity instanceof CraftAnimals) kill(entity);
                if (arg.equals("hostile") && entity instanceof CraftMonster) kill(entity);
                if (arg.equals("all")) kill(entity);
            }
        }

        //Send appropiate message and reset amountKilled
        sender.sendMessage(message(command)
                .replace("{count}", String.valueOf(amountKilled))
                .replace("{set}", (arg.equals("all")) ? "passive and hostile" : arg)
                .replace("{plural}", (amountKilled == 1) ? "" : "s"));
        amountKilled = 0;
        return true;
    }

    private void kill(LivingEntity entity) {
        entity.remove();
        amountKilled++;
    }
}
