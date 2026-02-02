package org.whoisjeb.aurum;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.*;
import org.whoisjeb.aurum.data.*;
import java.util.UUID;

public class Listener implements org.bukkit.event.Listener {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Listener(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @EventHandler(priority = Event.Priority.Normal)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        User user = new User(plugin, uuid, plugin.userdataDir(uuid));
        user.load(uuid, player.getName());

        String nickname = user.getString("info.nickname");
        if (nickname != null) player.setDisplayName(settings.getString("general.nickname-prefix") + nickname);

        for (Object rawLine : settings.getList("messages.motd")) {
            String line = plugin.colorize(rawLine.toString(), true);
            player.sendMessage(line);
        }
    }

    @EventHandler(priority = Event.Priority.Normal)
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        plugin.loadedUsers().get(uuid).unload();
    }

    @EventHandler(priority = Event.Priority.Normal)
    public void onPlayerKick(PlayerKickEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        plugin.loadedUsers().get(uuid).unload();
    }

    @EventHandler(priority = Event.Priority.Normal)
    public void onPlayerChat(PlayerChatEvent event) {
        String prefix = plugin.getPex().getUser(event.getPlayer()).getPrefix();
        String finalMessage = plugin.formatChatMessage(event.getPlayer(), event.getMessage());
        event.setFormat(plugin.colorize(finalMessage, true));
    }
}
