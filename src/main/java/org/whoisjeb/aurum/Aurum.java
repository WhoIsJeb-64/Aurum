package org.whoisjeb.aurum;

import org.bukkit.Bukkit;
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
    private PluginDescriptionFile pdf;
    private AurumAPI api;
    private AurumSettings settings;
    private static HashMap<UUID, User> userCache;
    private static ArrayList<TeleportRequest> tpRequests;

    @Override public void onEnable() {
        plugin = this;
        log = this.getServer().getLogger();
        pdf = this.getDescription();
        pluginName = pdf.getName();
        log.info("[" + pluginName + "] Is Loading, Version: " + pdf.getVersion());

        settings = new AurumSettings(this, new File(getDataFolder(), "config.yml"));
        settings.load();
        userCache = new HashMap<>();
        tpRequests = new ArrayList<>();

        registerCommands();
        Listener listener = new Listener(this, settings);

        getServer().getPluginManager().registerEvents(listener, this);
        log.info("[" + pluginName + "] Is Loaded, Version: " + pdf.getVersion());
    }

    @Override public void onDisable() {
        settings.save();
        log.info("[" + pluginName + "] Is Unloaded, Version: " + pdf.getVersion());
    }

    private void registerCommands() {
        getCommand("aurum").setExecutor(new AurumCommand(this, settings));
        getCommand("delhome").setExecutor(new Delhome(this, settings));
        getCommand("delwarp").setExecutor(new Delwarp(this, settings));
        getCommand("discord").setExecutor(new Discord(this, settings));
        getCommand("home").setExecutor(new Home(this, settings));
        getCommand("homes").setExecutor(new Homes(this, settings));
        getCommand("item").setExecutor(new Item(this, settings));
        getCommand("nickname").setExecutor(new Nickname(this, settings));
        getCommand("offlineteleport").setExecutor(new OfflineTeleport(this, settings));
        getCommand("playerlist").setExecutor(new Playerlist(this, settings));
        getCommand("rules").setExecutor(new Rules(this, settings));
        getCommand("sethome").setExecutor(new Sethome(this, settings));
        getCommand("setspawn").setExecutor(new Setspawn(this, settings));
        getCommand("setwarp").setExecutor(new Setwarp(this, settings));
        getCommand("spawn").setExecutor(new Spawn(this, settings));
        getCommand("sudo").setExecutor(new Sudo(this, settings));
        getCommand("teleport").setExecutor(new Teleport(this, settings));
        getCommand("teleportaccept").setExecutor(new Teleportaccept(this, settings));
        getCommand("teleportask").setExecutor(new Teleportask(this, settings));
        getCommand("teleportdeny").setExecutor(new Teleportdeny(this, settings));
        getCommand("tellraw").setExecutor(new Tellraw(this, settings));
        getCommand("time").setExecutor(new Time(this, settings));
        getCommand("warp").setExecutor(new Warp(this, settings));
        getCommand("warps").setExecutor(new Warps(this, settings));
        getCommand("weather").setExecutor(new Weather(this, settings));
        getCommand("whois").setExecutor(new WhoIs(this, settings));
    }

    public static AurumAPI api() {
        Aurum plugin = (Aurum) Bukkit.getServer().getPluginManager().getPlugin("Aurum");
        return plugin.api;
    }

    public HashMap<UUID, User> loadedUsers() {
        return userCache;
    }

    public ArrayList<TeleportRequest> tpRequestCache() {
        return tpRequests;
    }

    public File userdataDir(UUID uuid) {
        return new File(plugin.getDataFolder(), "userdata/" + uuid + ".yml");
    }

    public PermissionManager getPex() {
        return PermissionsEx.getPermissionManager();
    }

    public String colorize(String input, boolean isAllowed) {
        String regex = "&([0-9a-f])";
        String replacement = isAllowed ? "§$1" : "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.find() ? matcher.replaceAll(replacement) : input;
    }

    public String formatChatMessage(Player player, String message) {
        String prefix = getPex().getUser(player).getPrefix();
        message = colorize(message, player.hasPermission("aurum.color"));
        String format = settings.getString("general.chat-format")
                .replace("%prefix%", prefix)
                .replace("%name%", player.getDisplayName())
                .replace("%message%", message);
        return colorize(format, true);
    }
}
