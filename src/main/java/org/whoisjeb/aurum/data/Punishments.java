package org.whoisjeb.aurum.data;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.whoisjeb.aurum.Aurum;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class Punishments extends AurumData {
    private final Aurum plugin;
    private final File logFile;
    private static final Logger log = Bukkit.getServer().getLogger();

    public Punishments(Aurum plugin, File logFile) {
        super(new File(plugin.getDataFolder(), "punishments.yml"));
        this.plugin = plugin;
        this.logFile = new File(plugin.getDataFolder(), "punishments.yml");
    }

    public void load() {
        try {
            Files.createDirectories(logFile.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!logFile.exists()) {
            initializeNewLogFile();
        }
        super.load();
    }

    private void initializeNewLogFile() {
        String resourcePath = "/punishments.yml";
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                log.severe("[Aurum] Failed to find punishments.yml!");
                return;
            }
            //Does the actual copying of the resource to the real log file
            Files.copy(inputStream, logFile.toPath());
        } catch (IOException e) {
            log.severe("[Aurum] Failed to initialize new punishments.yml!");
            throw new RuntimeException(e);
        }
    }

    public boolean isBanned(UUID uuid) {
        String path = "bans." + uuid;
        if (this.hasProperty(path))
            return this.getBoolean(path + ".active", false);
        else
            //Will add code to check if the ban has expired
            return false;
    }

    public void ban(OfflinePlayer player, String reason, String issuer) {
        UUID uuid = plugin.uuidManager.getUUIDFromUsername(player.getName());
        String path = "bans." + uuid + ".";
        this.setProperty(path + "expiration", 0);
        this.setProperty(path + "active", true);
        this.setProperty(path + "issuer", issuer);
        this.setProperty(path + "reason", reason);

        //Make sure that the lastOnline date is set before kicking
        User user = new User(plugin.uuidManager.getUUIDFromUsername(player.getName()));
        user.setProperty("data.lastOnline", System.currentTimeMillis());
        user.save();
        Bukkit.getPlayer(player.getName()).kickPlayer(reason);
    }

    public void unban(OfflinePlayer player) {
        UUID uuid = plugin.uuidManager.getUUIDFromUsername(player.getName());
        String path = "bans." + uuid + ".";
        this.setProperty(path + "active", false);
    }

    public void warn(OfflinePlayer player, String reason) {
        UUID uuid = plugin.uuidManager.getUUIDFromUsername(player.getName());
        String path = "warnings." + uuid;
        ArrayList<String> warnings = new ArrayList<>(this.getStringList(path, null));

        if (!warnings.contains(reason)) warnings.add(reason);
        this.setProperty(path, warnings);
    }

    public void unwarn(OfflinePlayer player, String reason) {
        UUID uuid = plugin.uuidManager.getUUIDFromUsername(player.getName());
        String path = "warnings." + uuid;
        ArrayList<String> warnings = new ArrayList<>(this.getStringList(path, null));

        for (String warning : warnings) {
            if (warning.equalsIgnoreCase(reason)) {
                warnings.remove(warning);
                this.setProperty(path, warnings);
                return;
            }
        }
    }
}
