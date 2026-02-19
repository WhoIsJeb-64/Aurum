package org.whoisjeb.aurum.data;

import com.projectposeidon.johnymuffin.UUIDManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.whoisjeb.aurum.Aurum;
import java.util.UUID;

public class AurumAPI {
    private static Aurum plugin = (Aurum) Bukkit.getServer().getPluginManager().getPlugin("Aurum");

    /**
     * Retrieves the reason for which a player was banned from Aurum's punishments.yml.
     * @param uuid The UUID of the player.
     */
    public static String getBanReason(UUID uuid) {
        String username = UUIDManager.getInstance().getUsernameFromUUID(uuid);
        if (!Bukkit.getServer().getOfflinePlayer(username).isBanned()) return null;
        return plugin.punishments.getString("bans." + uuid + ".reason");
    }

    /**
     * Retrieves the name of who banned someone from Aurum's punishments.yml.
     * @param uuid The UUID of the banned player.
     */
    public static String getBanIssuer(UUID uuid) {
        String username = UUIDManager.getInstance().getUsernameFromUUID(uuid);
        if (!Bukkit.getServer().getOfflinePlayer(username).isBanned()) return null;
        return plugin.punishments.getString("bans." + uuid + ".issuer");
    }

    /**
     * Allows for the creation/setting of new properties in Aurum userdata files.
     * @param uuid The UUID of the player.
     * @param path The location of the property.
     * @param value The value the property will be set to.
     */
    public static void setUserProperty(UUID uuid, String path, Object value) {
        String name = UUIDManager.getInstance().getUsernameFromUUID(uuid);
        User user = new User(uuid);
        user.load(uuid);

        user.setProperty(path, value);
        user.save();
    }

    /**
     * Allows for the creation/setting of properties that are Locations in Aurum userdata files.
     * @param uuid The UUID of the player.
     * @param path The location of the property.
     * @param location The Location that will be saved at the specified path. (World-Name x y z pitch yaw)
     */
    public static void setUserLocation(UUID uuid, String path, Location location) {
        String name = UUIDManager.getInstance().getUsernameFromUUID(uuid);
        User user = new User(uuid);
        user.load(uuid);

        user.setProperty(path, user.locationToString(location));
        user.save();
    }

    /**
     * Allows for the retrieval of Integers from Aurum userdata.
     * @param uuid The UUID of the player.
     * @param path The location of the property.
     * @return The integer retrieved from the specified path.
     */
    public static int getUserInteger(UUID uuid, String path) {
        String name = UUIDManager.getInstance().getUsernameFromUUID(uuid);
        User user = new User(uuid);
        user.load(uuid);

        return user.getInt(path, 0);
    }

    /**
     * Allows for the retrieval of Strings from Aurum userdata.
     * @param uuid The UUID of the player.
     * @param path The location of the property.
     * @return The string retrieved from the specified path.
     */
    public static String getUserString(UUID uuid, String path) {
        String name = UUIDManager.getInstance().getUsernameFromUUID(uuid);
        User user = new User(uuid);
        user.load(uuid);

        return user.getString(path);
    }

    /**
     * Allows for the retrieval of Locations from Aurum userdata.
     * @param uuid The UUID of the player.
     * @param path The location of the property.
     * @return The Location retrieved from the specified path.
     */
    public static Location getUserLocation(UUID uuid, String path) {
        String name = UUIDManager.getInstance().getUsernameFromUUID(uuid);
        User user = new User(uuid);
        user.load(uuid);

        return user.getLocation(path);
    }
}
