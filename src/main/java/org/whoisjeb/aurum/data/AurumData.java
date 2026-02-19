package org.whoisjeb.aurum.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.config.Configuration;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

public class AurumData extends Configuration {
    private static final Logger log = Bukkit.getServer().getLogger();

    public AurumData(File file) {
        super(file);
    }

    @Override
    public void setProperty(String path, Object value) {
        super.setProperty(path, value);
        this.save();
    }

    @Override
    public void removeProperty(String path) {
        super.removeProperty(path);
        this.save();
    }

    public boolean hasProperty(String path) {
        return getProperty(path) != null;
    }

    public long getLong(String path) {
        return Long.parseLong(this.getString(path));
    }

    public Location getLocation(String input) {
        input = getString(input).replaceAll(" ", "");
        String[] split = input.split(",");
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

    public World getWorld(String input) {
        input = getString(input).replaceAll(" ", "");
        String[] split = input.split(",");
        return Bukkit.getWorld(split[0]);
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

    public String getDate(String path) {
        if (!this.hasProperty(path)) {
            return null;
        }
        Long unixTime = (Long) this.getProperty(path);
        Date date = new Date(unixTime);
        SimpleDateFormat format = new SimpleDateFormat("MMM. d, yyyy");
        return format.format(date);
    }
}
