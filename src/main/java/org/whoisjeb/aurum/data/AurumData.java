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

/**
 * Provides methods for inheritors to interface better with data, especially locations and worlds.
 */
public abstract class AurumData extends Configuration {
    private static final Logger log = Bukkit.getServer().getLogger();

    public AurumData(File file) {
        super(file);
    }

    /**
     * Sets a property of an object, then saves it.
     * @param path The property's path, e.g: settings.general.chat-format.
     * @param value The value to which the property will be set.
     */
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

    /**
     * Removes a property of an object, then saves it.
     * @param path The property's path, e.g: settings.general.chat-format.
     */
    @Override
    public void removeProperty(String path) {
        super.removeProperty(path);
        this.save();
    }

    @Override
    public String getString(String path) {
        return (this.hasProperty(path)) ? super.getString(path) : null;
    }

    /**
     * @param path The property's path, e.g: settings.general.chat-format.
     * @return Whether the path is null, which generally corresponds to if it exists.
     */
    public boolean hasProperty(String path) {
        return getProperty(path) != null;
    }

    /**
     * Converts a stored location into a usable Location object.
     * @param input The location in string form, formatted as: "world, x, y, z, pitch, yaw".
     * @return The location as a Location object.
     */
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

    /**
     * Converts a unix-time timestamp into a human-readable date.
     * @param path The path of the long in question.
     * @param format The date format, e.g "MMM. d, yyyy".
     * @return The readable date as a String object.
     */
    public String getFormattedDate(String path, String format) {
        if (!this.hasProperty(path)) return null;

        long unixTime = this.getLong(path);
        Date date = new Date(unixTime);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Extracts the world from a stored location.
     * @param input The location in string form, formatted as: "world, x, y, z, pitch, yaw".
     * @return The world of the stored location as a World object.
     */
    public World getWorld(String input) {
        input = getString(input).replaceAll(" ", "");
        String[] split = input.split(",");
        return Bukkit.getWorld(split[0]);
    }

    /**
     * @param path The path of the UUID in question.
     * @return The UUID at the given path.
     */
    public UUID getUUID(String path) {
        return UUID.fromString(getString(path));
    }

    /**
     * @param path The path of the long in question.
     * @return The long at the given path.
     */
    public long getLong(String path) {
        return Long.parseLong(this.getString(path));
    }

    /**
     * @param path The path of the double in question.
     * @return The double at the given path.
     */
    public double getDouble(String path) {
        return Double.parseDouble(this.getString(path));
    }
}
