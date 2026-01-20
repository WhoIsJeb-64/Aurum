package org.whoisjeb.aurum.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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

public class User extends AurumData {
    private final Aurum plugin;
    private final File dataFile;
    private static final Logger log = Bukkit.getServer().getLogger();

    public User(Aurum plugin, UUID uuid, File dataFile) {
        super(new File(plugin.getDataFolder(), "userdata/" + uuid + ".yml"));
        this.plugin = plugin;
        this.dataFile = plugin.userdataDir(uuid);
    }

    public void load(UUID uuid, String name) {
        try {
            Files.createDirectories(dataFile.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!dataFile.exists()) {
            initializeNewUser(uuid, name);
        }
        super.load();
        plugin.loadedUsers().put(uuid, this);
        log.info("[Aurum] Loaded data for " + uuid.toString() + " successfully!");
    }

    public User loadIfUnloaded(Player player) {
        UUID uuid = player.getUniqueId();
        if (plugin.loadedUsers().containsKey(uuid)) {
            return plugin.loadedUsers().get(uuid);
        }
        this.load(uuid, player.getName());
        return this;
    }

    private void initializeNewUser(UUID uuid, String name) {
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

    public int getMaxHomes(Player player) {
        World world = player.getWorld();
        String[] userPerms = PermissionsEx.getPermissionManager().getUser(player).getPermissions(world.getName());
        for (String perm : userPerms) {
            if (perm.contains("aurum.maxhomes.")) {
                String clean = perm.replaceAll("\\D+","");
                return Math.max(Integer.parseInt(clean), 1);
            }
        }
        return 1;
    }
}
