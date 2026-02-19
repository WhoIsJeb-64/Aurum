package org.whoisjeb.aurum.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * The data structure used by Aurum to store, manipulate, and retireve data.
 */
public class User extends AurumData {
    private static Aurum plugin = (Aurum) Bukkit.getServer().getPluginManager().getPlugin("Aurum");
    private final File dataFile;
    private static final Logger log = Bukkit.getServer().getLogger();

    public User(UUID uuid) {
        super(new File(plugin.getDataFolder(), "userdata/" + uuid + ".yml"));
        this.dataFile = new File(plugin.getDataFolder(), "userdata/" + uuid + ".yml");
    }

    public void load(UUID uuid) {
        String name = plugin.uuidManager.getUsernameFromUUID(uuid);
        try {
            Files.createDirectories(dataFile.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!dataFile.exists()) {
            initializeNewUser(uuid);
        }
        super.load();
        if (!plugin.loadedUsers().containsKey(uuid)) {
            plugin.loadedUsers().put(uuid, this);
            log.info("[Aurum] Loaded data for " + uuid.toString() + " successfully!");
        }
    }

    public User loadIfUnloaded(OfflinePlayer player) {
        UUID uuid = plugin.uuidManager.getUUIDFromUsername(player.getName());
        if (plugin.loadedUsers().containsKey(uuid)) {
            return plugin.loadedUsers().get(uuid);
        }
        this.load(uuid);
        return this;
    }

    private void initializeNewUser(UUID uuid) {
        String name = plugin.uuidManager.getUsernameFromUUID(uuid);
        String resourcePath = "/user.yml";
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                log.severe("[Aurum] Failed to find user.yml!");
                return;
            }
            //Copy contents of resource to new dataFile, then add their data
            {
                Files.copy(inputStream, dataFile.toPath());
                this.setProperty("info.uuid", uuid.toString());
                this.setProperty("info.name", name);
                this.setProperty("data.firstJoin", System.currentTimeMillis());
                this.setProperty("data.lastOnline", null);
                this.setProperty("homes", null);
                this.save();
                plugin.loadedUsers().put(uuid, this);
            }
        } catch (IOException e) {
            log.severe("[Aurum] Failed to initialize new user!");
            throw new RuntimeException(e);
        }
    }

    public void unload() {
        Player player = Bukkit.getPlayer(this.getString("info.name"));
        Location location = player.getLocation();
        String position = this.locationToString(location);
        this.setProperty("data.position", position);
        this.save();
        plugin.loadedUsers().remove(this.getUUID("info.uuid"));
        log.info("[Aurum] Unloaded data for " + this.getUUID("info.uuid") + " successfully!");
    }

    //Getters

    public int getMaxHomes() {
        World world = this.getWorld("data.position");
        String[] userPerms = PermissionsEx.getPermissionManager().getUser(this.getString("info.name")).getPermissions(world.getName());
        for (String perm : userPerms) {
            if (perm.contains("aurum.maxhomes.")) {
                String clean = perm.replaceAll("\\D+","");
                return Math.max(Integer.parseInt(clean), 1);
            }
        }
        return 1;
    }

    public String getIP() {
        if (!this.hasProperty("info.address")) return null;
        String address = this.getString("info.address");
        return address.substring(0, address.length() - 6);
    }
}