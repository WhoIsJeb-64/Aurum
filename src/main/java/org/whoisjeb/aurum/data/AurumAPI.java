package org.whoisjeb.aurum.data;

import org.bukkit.Bukkit;
import org.whoisjeb.aurum.Aurum;
import java.util.UUID;

public class AurumAPI {

    public AurumAPI() {}

    private static Aurum plugin = (Aurum) Bukkit.getServer().getPluginManager().getPlugin("Aurum");

    public AurumUser user(UUID uuid) {
        AurumUser user = new AurumUser(uuid);
        user.load(uuid);
        return user;
    }

    public AurumSettings config() {
        return plugin.settings;
    }

    public AurumPunishments punishments() {
        return plugin.punishments;
    }
}
