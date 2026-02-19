package org.whoisjeb.aurum;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        //Kick the player if they're banned
        if (plugin.punishments.isBanned(uuid)) {
            String banMessage = (plugin.punishments.getLong("bans." + uuid + ".expiration") == 0)
                    ? ChatColor.DARK_RED + "You are permanently banned for " + plugin.punishments.getString("bans." + uuid + ".reason") + "!"
                    : ChatColor.RED + "You are banned for " + plugin.punishments.getString("bans." + uuid + ".reason") + "!";
            Bukkit.getPlayer(player.getName()).kickPlayer(banMessage);
        }

        User user = new User(uuid);
        user.load(uuid);

        updateUserData(player, user);

        for (Object rawLine : settings.getList("messages.motd")) {
            String line = plugin.colorize(rawLine.toString(), true);
            player.sendMessage(line);
        }
    }

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        User user = plugin.loadedUsers().get(uuid);

        user.setProperty("data.lastOnline", System.currentTimeMillis());
        user.save();

        user.unload();
    }

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerKick(PlayerKickEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        User user = plugin.loadedUsers().get(uuid);

        user.setProperty("data.lastOnline", System.currentTimeMillis());
        user.save();

        user.unload();
    }

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerChat(PlayerChatEvent event) {
        String prefix = plugin.getPex().getUser(event.getPlayer()).getPrefix();
        String finalMessage = plugin.chatFormat(event.getPlayer(), event.getMessage());
        event.setFormat(plugin.colorize(finalMessage, true));
    }

    private void updateUserData(Player player, User user) {
        if (!user.getString("info.name").equals(player.getName())) {
            user.setProperty("info.name", player.getName());
            user.save();
        }

        String address = user.getIP();
        if (user.hasProperty("info.address") &&
                !address.equals(player.getAddress().toString().substring(0, player.getAddress().toString().length() - 6))) {
            //
        }
        user.setProperty("info.address", player.getAddress().toString());

        String nickname = user.getString("info.nickname");
        if (nickname != null) player.setDisplayName(settings.getString("general.nickname-prefix") + nickname);

        user.save();
    }
}
