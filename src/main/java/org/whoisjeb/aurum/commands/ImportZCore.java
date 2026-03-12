package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.config.Configuration;
import org.whoisjeb.aurum.Aurum;
import org.whoisjeb.aurum.data.AurumMessages;
import org.whoisjeb.aurum.data.AurumPunishments;
import org.whoisjeb.aurum.data.AurumSettings;
import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class ImportZCore extends AuricCommand {
    private final Aurum plugin;
    private final AurumSettings settings;
    private final AurumMessages language;
    private final AurumPunishments punishments;

    public ImportZCore(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
        this.settings = plugin.settings;
        this.language = plugin.language;
        this.punishments = plugin.punishments;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Check if ZCore is loaded
        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("ZCore")) {
            sender.sendMessage(message(command, "zcore-not-found"));
            return true;
        }

        sender.sendMessage(message(command, "start"));

        if (!importConfiguration()) {
            sender.sendMessage(message(command, "config-not-found"));
            return true;
        }
        else sender.sendMessage(message(command, "config-imported"));

        if (!importUserdata()) {
            sender.sendMessage(message(command, "database-not-found"));
            return true;
        }
        else sender.sendMessage(message(command, "database-imported"));

        sender.sendMessage(message(command, "finish"));
        return true;
    }

    private boolean importConfiguration() {
        //Load ZCore configuration
        File file = new File("plugins/ZCore/config.yml");
        if (!file.exists()) return false;
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(String.valueOf(file));
        Configuration zConfig = (Configuration) yaml.load(inputStream);

        //Import Data
        settings.setProperty("chat.format.normal", zConfig.getString("text.chat-format")
                .replace("{PLAYER}", "{name}"));
        settings.setProperty("chat.nickname-prefix", zConfig.getString("text.nick-prefix"));

        language.setProperty("general.welcome-message", zConfig.getString("text.new-player-announcement", null)
                .replace("{PLAYER}", "{name}"));
        language.setProperty("general.muted", zConfig.getString("command.mute.default-reason", null));

        List<String> zMOTD = zConfig.getStringList("text.message-of-the-day", null);
        zMOTD.replaceAll(line -> line.replace("{NAME}", "{name}"));
        language.setProperty("commands.motd", zMOTD);
        language.setProperty("commands.rules", zConfig.getStringList("command.rules.lines", null));

        return true;
    }

    private boolean importUserdata() {
        return true;
    }
}
