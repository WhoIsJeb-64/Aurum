package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumSettings;
import org.whoisjeb.aurum.data.User;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class AurumCommand extends AurumCommandBase {
    private final Aurum plugin;
    private final AurumSettings settings;
    private static final Logger log = Bukkit.getServer().getLogger();

    public AurumCommand(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || args[0].equalsIgnoreCase("info")) {
            sender.sendMessage("§6================= §eAurum Info §6=================");
            sender.sendMessage("§6Version:§e " + plugin.getDescription().getVersion());
            sender.sendMessage("§7An essentials alternative for project poseidon,");
            sender.sendMessage("§7by coffeelover42.");
            sender.sendMessage("§eCheck out the github page for documentation!");
            return true;
        }
        else if (args[0].equalsIgnoreCase("reload")) {
            settings.load();
            for (Map.Entry<UUID, User> entry : plugin.loadedUsers().entrySet()) {
                entry.getValue().load();
            }
            sender.sendMessage("§aReloaded Aurum successfully!");
            log.info("[Aurum] Reloaded plugin successfully!");
            return true;
        }
        else {
            sender.sendMessage("§c[!] Invalid argument!");
        }
        return true;
    }
}
