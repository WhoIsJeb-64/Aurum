package org.whoisjeb.aurum.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.config.Configuration;
import org.whoisjeb.aurum.Aurum;
import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

public class AurumData extends Configuration {
    private Aurum plugin;
    private File settingsFile;
    private static final Logger log = Bukkit.getServer().getLogger();

    public AurumData(File file) {
        super(file);
    }

    public boolean hasProperty(String path) {
        return getProperty(path) != null;
    }

    public Location getLocation(String input) {
        input = getString(input).replaceAll(" ", "");
        final String[] split = input.split(",");
        try {
            return new Location(
                    Bukkit.getWorld(split[0]),    // World Name
                    Double.parseDouble(split[1]), // x
                    Double.parseDouble(split[2]), // y
                    Double.parseDouble(split[3]), // z
                    Float.parseFloat(split[4]),   // Pitch
                    Float.parseFloat(split[5])    // Yaw
            );
        } catch (RuntimeException e) {
            log.severe("[Aurum] Failed to get location from config!");
            throw new RuntimeException(e);
        }
    }

    public String getString(String path) {
        if (this.getProperty(path).toString() == null) {
            return null;
        }
        return this.getProperty(path).toString();
    }

    public UUID getUUID(String path) {
        return UUID.fromString(getString(path));
    }

    public String locationToString(Location input) {
        String world = input.getWorld().getName();
        String x = String.valueOf(input.getX());
        String y = String.valueOf(input.getY());
        String z = String.valueOf(input.getZ());
        String pitch = String.valueOf(input.getPitch());
        String yaw = String.valueOf(input.getYaw());
        return world + ", " + x + ", " + y + ", " + z + ", " + pitch + ", " + yaw;
    }
}
