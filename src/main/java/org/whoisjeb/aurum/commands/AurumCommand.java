package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumUser;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class AurumCommand extends AuricCommand {
    private final Aurum plugin;

    public AurumCommand(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Messages are sent after all logic is run
        ArrayList<String> menu = new ArrayList<>();

        //Logic for info subcommand; Message is hard-coded
        if (args.length < 1 || args[0].equalsIgnoreCase("info")) {
            menu.add("§e[§6================= §eAurum Info §6=================§e]");
            menu.add("§6Version:§e " + plugin.pdf.getVersion());
            menu.add("§7An essentials alternative for project poseidon,");
            menu.add("§7by coffeelover42.");
            menu.add("§dCheck out the github page for documentation!");
        }
        //Logic for reload subcommand
        else if (args[0].equalsIgnoreCase("reload")) {
            plugin.settings.load();
            plugin.punishments.load();
            for (Map.Entry<UUID, AurumUser> entry : plugin.loadedUsers().entrySet()) {
                entry.getValue().load(entry.getValue().getUUID("info.uuid"));
            }
            menu.add(message(command));
        }
        //Logic for invalid subcommand
        else {
            menu.add(message("general.command-usage").replace("{usage}", command.getUsage()));
        }

        //Send appropiate message(s) based on the subcommand
        sendMessages(sender, menu);
        return true;
    }
}
