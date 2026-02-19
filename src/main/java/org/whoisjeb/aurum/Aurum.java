package org.whoisjeb.aurum;

import com.projectposeidon.johnymuffin.UUIDManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.whoisjeb.aurum.commands.*;
import org.whoisjeb.aurum.data.*;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aurum extends JavaPlugin {
    private JavaPlugin plugin;
    private Logger log;
    private String pluginName;
    private AurumAPI api;
    public PluginDescriptionFile pdf;
    public AurumSettings settings;
    public Punishments punishments;
    public UUIDManager uuidManager;
    private static HashMap<UUID, User> userCache;
    private static ArrayList<TeleportRequest> tpRequests;

    @Override public void onEnable() {
        intializeData();
        registerCommands();
        log.info("[" + pluginName + "] has loaded; Version: " + pdf.getVersion());
    }

    @Override public void onDisable() {
        settings.save();
        punishments.save();
        log.info("[" + pluginName + "] has unloaded; Version: " + pdf.getVersion());
    }

    private void intializeData() {
        //Basic Info
        plugin = this;
        log = this.getServer().getLogger();
        pdf = this.getDescription();
        pluginName = pdf.getName();

        //Data Storage
        uuidManager = UUIDManager.getInstance();
        settings = new AurumSettings(this, new File(getDataFolder(), "config.yml"));
        settings.load();
        punishments = new Punishments(this, new File(getDataFolder(), "config.yml"));
        punishments.load();
        userCache = new HashMap<>();
        tpRequests = new ArrayList<>();

        //Listeners
        Listener listener = new Listener(this, settings);
        getServer().getPluginManager().registerEvents(listener, this);
    }

    private void registerCommands() {
        getCommand("aurum").setExecutor(new AurumCommand(this));
        getCommand("ban").setExecutor(new Ban(this));
        getCommand("banlog").setExecutor(new Banlog(this));
        getCommand("delhome").setExecutor(new Delhome(this));
        getCommand("delwarp").setExecutor(new Delwarp(this));
        getCommand("discord").setExecutor(new Discord(this));
        getCommand("heal").setExecutor(new Heal(this));
        getCommand("help").setExecutor(new Help(this));
        getCommand("home").setExecutor(new Home(this));
        getCommand("homes").setExecutor(new Homes(this));
        getCommand("item").setExecutor(new Item(this));
        getCommand("modview").setExecutor(new Modview(this));
        getCommand("nickname").setExecutor(new Nickname(this));
        getCommand("offlineteleport").setExecutor(new OfflineTeleport(this));
        getCommand("playerlist").setExecutor(new Playerlist(this));
        getCommand("rules").setExecutor(new Rules(this));
        getCommand("sethome").setExecutor(new Sethome(this));
        getCommand("setspawn").setExecutor(new Setspawn(this));
        getCommand("setwarp").setExecutor(new Setwarp(this));
        getCommand("spawn").setExecutor(new Spawn(this));
        getCommand("sudo").setExecutor(new Sudo(this));
        getCommand("teleport").setExecutor(new Teleport(this));
        getCommand("teleportaccept").setExecutor(new Teleportaccept(this));
        getCommand("teleportask").setExecutor(new Teleportask(this));
        getCommand("teleportdeny").setExecutor(new Teleportdeny(this));
        getCommand("tellraw").setExecutor(new Tellraw(this));
        getCommand("time").setExecutor(new Time(this));
        getCommand("unban").setExecutor(new Unban(this));
        getCommand("unwarn").setExecutor(new Unwarn(this));
        getCommand("warn").setExecutor(new Warn(this));
        getCommand("warp").setExecutor(new Warp(this));
        getCommand("warps").setExecutor(new Warps(this));
        getCommand("weather").setExecutor(new Weather(this));
        getCommand("whois").setExecutor(new WhoIs(this));
    }

    public UUID getUUID(OfflinePlayer player) {
        return uuidManager.getUUIDFromUsername(player.getName());
    }

    public UUID getUUID(String name) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        return uuidManager.getUUIDFromUsername(player.getName());
    }

    public String colorize(String input, boolean isAllowed) {
        String regex = "&([0-9a-f])";
        String replacement = isAllowed ? "§$1" : "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.find() ? matcher.replaceAll(replacement) : input;
    }

    public String chatFormat(Player player, String message) {
        String prefix = getPex().getUser(player).getPrefix();
        message = colorize(message, player.hasPermission("aurum.color"));
        String format = settings.getString("general.chat-format")
                .replace("%prefix%", prefix)
                .replace("%name%", player.getDisplayName())
                .replace("%message%", message);
        return colorize(format, true);
    }

    // Getters

    public HashMap<UUID, User> loadedUsers() {
        return userCache;
    }

    public ArrayList<TeleportRequest> tpRequestCache() {
        return tpRequests;
    }

    public PermissionManager getPex() {
        return PermissionsEx.getPermissionManager();
    }
}
