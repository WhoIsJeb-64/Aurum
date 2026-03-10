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

public class AurumUser extends AurumData {
    private static Aurum plugin = (Aurum) Bukkit.getServer().getPluginManager().getPlugin("Aurum");
    private final File dataFile;
    private static final Logger log = Bukkit.getServer().getLogger();

    public AurumUser(UUID uuid) {
        super(new File(plugin.getDataFolder(), "userdata/" + uuid + ".yml"));
        this.dataFile = new File(plugin.getDataFolder(), "userdata/" + uuid + ".yml");
    }

    public void load(UUID uuid) {
        String name = plugin.utils.getUsername(uuid);
        try {
            Files.createDirectories(dataFile.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!dataFile.exists()) {
            initializeNewUser(uuid);
        }
        super.load();
        plugin.loadedUsers().putIfAbsent(uuid, this);
    }

    public void load(UUID uuid, boolean keep) {
        String name = plugin.utils.getUsername(uuid);
        try {
            Files.createDirectories(dataFile.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!dataFile.exists()) {
            if (!initializeNewUser(uuid)) return;
        }
        else super.load();
        if (keep) plugin.loadedUsers().putIfAbsent(uuid, this);
    }

    private boolean initializeNewUser(UUID uuid) {
        if (plugin.utils.getUsername(uuid) == null) {
            return false;
        }
        String name = plugin.utils.getUsername(uuid);
        String resourcePath = "/user.yml";
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                log.severe("[Aurum] Failed to find user.yml!");
                return false;
            }
            //Copy contents of resource to new dataFile, then add their data
            {
                Files.copy(inputStream, dataFile.toPath());
                super.setProperty("info.uuid", uuid.toString());
                this.setProperty("info.name", name);
                this.setProperty("data.firstJoin", System.currentTimeMillis());
                this.setProperty("data.lastOnline");
                this.setProperty("homes");
                this.setProperty("economy.balance", 0.00);
                this.setProperty("states.new", true);
                plugin.loadedUsers().put(uuid, this);
            }
        } catch (IOException e) {
            log.severe("[Aurum] Failed to initialize new user!");
            throw new RuntimeException(e);
        }
        return true;
    }

    public void unload() {
        Player player = Bukkit.getPlayer(this.getString("info.name"));
        Location location = player.getLocation();
        this.setProperty("data.position", location);
        this.save();
        plugin.loadedUsers().remove(this.getUUID("info.uuid"));
        log.info("[Aurum] Unloaded data for " + this.getUUID("info.uuid") + " successfully!");
    }

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

    public boolean addBalance(double amount) {
        double balance = this.getDouble("economy.balance", 0.00);

        this.setProperty("economy.balance", balance + amount);
        return true;
    }

    public boolean subtractBalance(double amount, boolean allowNegative) {
        double balance = this.getDouble("economy.balance", 0.00);
        if (amount > balance && !allowNegative) return false;

        this.setProperty("economy.balance", balance - amount);
        return true;
    }
}