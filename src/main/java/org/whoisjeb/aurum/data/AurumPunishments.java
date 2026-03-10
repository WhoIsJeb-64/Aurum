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

/**
 * The data structure used by Aurum to manage bans and warnings.
 */
public class AurumPunishments extends AurumData {
    private final Aurum plugin;
    private final File logFile;
    private static final Logger log = Bukkit.getServer().getLogger();

    public AurumPunishments(Aurum plugin, File logFile) {
        super(logFile);
        this.plugin = plugin;
        this.logFile = logFile;
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

    /**
     * Determines whether a player is banned.
     * @param uuid The player's UUID.
     * @return Whether the player is banned, after having checked for expiration.
     */
    public boolean isBanned(UUID uuid) {
        String path = "bans." + uuid;

        //Check if the player has ever been banned
        if (this.hasProperty(path)) {
            //Check if the ban is already marked as inactive
            if (this.getBoolean(path + ".active", false)) {
                return true;
            }
            //Check if the ban has expired, and mark as inactive if so
            if (System.currentTimeMillis() >= this.getLong(path + ".expiration")) {
                this.setProperty(path + ".active", false);
                return false;
            }
        }
        return false;
    }

    /**
     * Determines whether a player is muted.
     * @param uuid The player's UUID.
     * @return Whether the player is muted, after having checked for expiration.
     */
    public boolean isMuted(UUID uuid) {
        String path = "mutes." + uuid;

        //Check if the player has ever been muted
        if (this.hasProperty(path)) {
            //Check if the mute is already marked as inactive
            if (this.getBoolean(path + ".active", false)) {
                return true;
            }
            //Check if the mute has expired, and mark as inactive if so
            if (System.currentTimeMillis() >= this.getLong(path + ".expiration")) {
                this.setProperty(path + ".active", false);
                return false;
            }
        }
        return false;
    }

    /**
     * Bans a player with the Aurum ban system, kicking the player if they're online.
     * @param player The player who will be banned.
     * @param duration How long the ban will last. If 0, the ban will be permanent.
     * @param reason The reason for which the ban is being isssued.
     * @param issuer The name of who is issuing the ban.
     */
    public void ban(OfflinePlayer player, long duration, String reason, String issuer) {
        UUID uuid = plugin.utils.getUUID(player.getName());
        String path = "bans." + uuid + ".";
        this.setProperty(path + "expiration", duration);
        this.setProperty(path + "active", true);
        this.setProperty(path + "issuer", issuer);
        this.setProperty(path + "reason", reason);

        //Make sure that the lastOnline date is set before kicking
        AurumUser user = new AurumUser(plugin.utils.getUUID(player.getName()));
        user.load(plugin.utils.getUUID(player.getName()));
        user.setProperty("data.lastOnline", System.currentTimeMillis());
        user.save();
        if (player.isOnline()) Bukkit.getPlayer(player.getName()).kickPlayer(reason);
    }

    /**
     * Lifts a ban with the Aurum ban system.
     * @param player The player who will be unbanned.
     */
    public void unban(OfflinePlayer player) {
        UUID uuid = plugin.utils.getUUID(player.getName());
        String path = "bans." + uuid + ".";
        this.setProperty(path + "active", false);
    }

    /**
     * Mutes a player.
     * @param player The player who will be muted.
     * @param duration How long the mute will last. If 0, the mute will be permanent.
     * @param reason The reason for which the mute is being isssued.
     * @param issuer The name of who is issuing the mute.
     */
    public void mute(OfflinePlayer player, long duration, String reason, String issuer) {
        UUID uuid = plugin.utils.getUUID(player.getName());
        String path = "mutes." + uuid + ".";
        this.setProperty(path + "expiration", duration);
        this.setProperty(path + "active", true);
        this.setProperty(path + "issuer", issuer);
        this.setProperty(path + "reason", reason);
    }

    /**
     * Temporarily mutes a player.
     * @param player The player who will be banned.
     * @param days The amount of days for which the ban will last.
     * @param reason The reason for which the ban is being isssued.
     * @param issuer The name of who is issuing the ban.
     */
    public void tempMute(OfflinePlayer player, int days, String reason, String issuer) {
        UUID uuid = plugin.utils.getUUID(player.getName());
        String path = "mutes." + uuid + ".";
        this.setProperty(path + "expiration", System.currentTimeMillis() + (days * 86400000L));
        this.setProperty(path + "active", true);
        this.setProperty(path + "issuer", issuer);
        this.setProperty(path + "reason", reason);
    }

    /**
     * Lifts a mute.
     * @param player The player who will be unmuted.
     */
    public void unmute(OfflinePlayer player) {
        UUID uuid = plugin.utils.getUUID(player.getName());
        String path = "mutes." + uuid + ".";
        this.setProperty(path + "active", false);
    }

    /**
     * Warns a player using Aurum's warn system.
     * @param player The player who will be warned.
     * @param reason The content of/reason for the warning.
     */
    public void warn(OfflinePlayer player, String reason) {
        UUID uuid = plugin.utils.getUUID(player.getName());
        String path = "warnings." + uuid;
        ArrayList<String> warnings = new ArrayList<>(this.getStringList(path, null));

        if (!warnings.contains(reason)) warnings.add(reason);
        this.setProperty(path, warnings);
    }

    /**
     * Lifts a warning from a player, any whose reason matches the given reason.
     * @param player The player from whom a warn will be lifted.
     * @param reason The content of the warning to be lifted.
     */
    public void unwarn(OfflinePlayer player, String reason) {
        UUID uuid = plugin.utils.getUUID(player.getName());
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
