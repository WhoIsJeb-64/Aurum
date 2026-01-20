package org.whoisjeb.aurum;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.*;
import org.whoisjeb.aurum.data.*;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import java.io.File;
import java.util.UUID;

public class Listener implements org.bukkit.event.Listener {
    private Aurum plugin;
    private AurumSettings config;

    public Listener(Aurum plugin, AurumSettings config) {
        this.plugin = plugin;
        this.config = config;
    }

    @EventHandler(priority = Event.Priority.Normal)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        User user = new User(plugin, uuid, new File(plugin.getDataFolder(), "userdata/" + uuid + ".yml"));
        user.load(uuid, player.getName());
        for (Object rawLine : config.getList("messages.motd")) {
            String line = plugin.processColor(rawLine.toString(), true);
            player.sendMessage(line);
        }
    }

    @EventHandler(priority = Event.Priority.Normal)
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        plugin.getLoadedUsers().get(uuid).unload();
    }

    @EventHandler(priority = Event.Priority.Normal)
    public void onPlayerKick(PlayerKickEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        plugin.getLoadedUsers().get(uuid).unload();
    }

    @EventHandler(priority = Event.Priority.Normal)
    public void onPlayerChat(PlayerChatEvent event) {
        String prefix = PermissionsEx.getPermissionManager().getUser(event.getPlayer()).getPrefix();
        String message = event.getMessage();
        message = plugin.processColor(message, event.getPlayer().hasPermission("aurum.color"));
        prefix = plugin.processColor(prefix, true);
        event.setMessage(message);
        String format = config.getString("general.chat-format");
        format = format.replace("%prefix%", prefix);
        format = format.replace("%name%", "%1$s");
        format = format.replace("%message%", "%2$s");
        event.setFormat(plugin.processColor(format, true));
    }
}
