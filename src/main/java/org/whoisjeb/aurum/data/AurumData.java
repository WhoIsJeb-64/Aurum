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

    //Allows for the seamless saving of Location objects
    public void setProperty(String path, Location location) {
        String world = location.getWorld().getName();
        String x = String.valueOf(location.getX());
        String y = String.valueOf(location.getY());
        String z = String.valueOf(location.getZ());
        String pitch = String.valueOf(location.getYaw());
        String yaw = String.valueOf(location.getPitch());

        this.setProperty(path, world + ", " + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch);
    }

    //Allows for saving null
    public void setProperty(String path) {
        super.setProperty(path, null);
    }

    @Override
    public void removeProperty(String path) {
        super.removeProperty(path);
        this.save();
    }

    @Override
    public String getString(String path) {
        return (this.hasProperty(path)) ? super.getString(path) : null;
    }

    public boolean hasProperty(String path) {
        return getProperty(path) != null;
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
                    Float.parseFloat(split[5]),   // Yaw
                    Float.parseFloat(split[4])    // Pitch
            );
        } catch (RuntimeException e) {
            log.severe("[Aurum] Failed to get location from config!");
            throw new RuntimeException(e);
        }
    }

    public String getFormattedDate(String path, String format) {
        if (!this.hasProperty(path)) return null;

        long unixTime = this.getLong(path);
        Date date = new Date(unixTime);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public World getWorld(String input) {
        input = getString(input).replaceAll(" ", "");
        String[] split = input.split(",");
        return Bukkit.getWorld(split[0]);
    }

    public UUID getUUID(String path) {
        return UUID.fromString(getString(path));
    }

    public long getLong(String path) {
        return Long.parseLong(this.getString(path));
    }

    public double getDouble(String path) {
        return Double.parseDouble(this.getString(path));
    }
}
