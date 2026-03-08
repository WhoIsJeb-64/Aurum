package org.whoisjeb.aurum.data;

import org.bukkit.Bukkit;
import org.whoisjeb.aurum.Aurum;
import java.util.UUID;

public class AurumAPI {
    private static Aurum plugin = (Aurum) Bukkit.getServer().getPluginManager().getPlugin("Aurum");

    /**
     * Retrieves an instance of a User object, to have full access to manipulating/accessing their Aurum userdata.
     * @param uuid The uuid of the player whose User object will be returned.
     * @return The User object associated with the player.
     */
    public AurumUser user(UUID uuid) {
        AurumUser user = new AurumUser(uuid);
        user.load(uuid);
        return user;
    }

    /**
     * @return Aurum's configuration, as in what's at plugins/Aurum/config.yml.
     */
    public AurumSettings config() {
        return plugin.settings;
    }

    /**
     * Retrieves the object stored as plugins/Aurum/punishments.yml, allowing for accessing and manipulating its data.
     * @return The data structure containing the server's bans and warnings.
     */
    public AurumPunishments punishments() {
        return plugin.punishments;
    }
}
