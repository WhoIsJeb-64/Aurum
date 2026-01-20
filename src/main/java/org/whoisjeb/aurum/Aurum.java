package org.whoisjeb.aurum;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.whoisjeb.aurum.commands.*;
import org.whoisjeb.aurum.data.AurumSettings;
import org.whoisjeb.aurum.data.User;
import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aurum extends JavaPlugin {
    private JavaPlugin plugin;
    private Logger log;
    private String pluginName;
    private PluginDescriptionFile pdf;
    private AurumSettings settings;
    private static HashMap<UUID, User> loadedUsers;

    @Override public void onEnable() {
        plugin = this;
        log = this.getServer().getLogger();
        pdf = this.getDescription();
        pluginName = pdf.getName();
        log.info("[" + pluginName + "] Is Loading, Version: " + pdf.getVersion());
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
            log.info("[" + pluginName + "] Loaded PermissionsEx successfully!");
        } else {
            log.severe("[" + pluginName + "] Failed to load permissions plugin!");
        }
        settings = new AurumSettings(this, new File(getDataFolder(), "config.yml"));
        settings.load();
        loadedUsers = new HashMap<>();

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
        getCommand("spawn").setExecutor(new Spawn(this, settings));
        getCommand("setspawn").setExecutor(new Setspawn(this, settings));
        getCommand("rules").setExecutor(new Rules(this, settings));
        getCommand("discord").setExecutor(new Discord(this, settings));
        getCommand("sethome").setExecutor(new Sethome(this, settings));
        getCommand("home").setExecutor(new Home(this, settings));
        getCommand("delhome").setExecutor(new Delhome(this, settings));
        getCommand("homes").setExecutor(new Homes(this, settings));
        getCommand("setwarp").setExecutor(new Setwarp(this, settings));
        getCommand("warp").setExecutor(new Warp(this, settings));
        getCommand("delwarp").setExecutor(new Delwarp(this, settings));
        getCommand("warps").setExecutor(new Warps(this, settings));
    }

    public void logger(Level level, String message) {
        Bukkit.getLogger().log(level, "[" + plugin.getDescription().getName() + "] " + message);
    }

    public AurumSettings getSettings() {
        return settings;
    }

    public HashMap<UUID, User> getLoadedUsers() {
        return loadedUsers;
    }

    public String processColor(String input, boolean isAllowed) {
        String regex = "&([0-9a-f])";
        String replacement;
        if (isAllowed) {
            replacement = "§$1";
        } else {
            replacement = "";
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.replaceAll(replacement);
        } else {
            return input;
        }
    }
}
