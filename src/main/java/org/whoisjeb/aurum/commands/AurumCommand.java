package org.whoisjeb.aurum.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.User;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class AurumCommand extends AurumCommandBase {
    private final Aurum plugin;

    public AurumCommand(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> menu = new ArrayList<>();

        if (args.length < 1 || args[0].equalsIgnoreCase("info")) {
            menu.add("§e[§6================= §eAurum Info §6=================§e]");
            menu.add("§6Version:§e " + plugin.pdf.getVersion());
            menu.add("§7An essentials alternative for project poseidon,");
            menu.add("§7by coffeelover42.");
            menu.add("§eCheck out the github page for documentation!");
        }
        else if (args[0].equalsIgnoreCase("reload")) {
            plugin.settings.load();
            plugin.punishments.load();
            for (Map.Entry<UUID, User> entry : plugin.loadedUsers().entrySet()) {
                entry.getValue().load();
            }
            menu.add("§aReloaded Aurum successfully!");
            log.info("[Aurum] Reloaded plugin successfully!");
        }
        else {
            menu.add("§c[!] Invalid argument!");
        }

        sendMessages(sender, menu);
        return true;
    }
}
