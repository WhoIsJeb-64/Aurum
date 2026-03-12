package org.whoisjeb.aurum;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.*;
import org.whoisjeb.aurum.data.*;

import java.util.List;
import java.util.UUID;

public class Listener implements org.bukkit.event.Listener {
    private final Aurum plugin;
    private final AurumSettings settings;

    public Listener(Aurum plugin, AurumSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().substring(1);
        Command command = Bukkit.getPluginCommand((message.split(" ")[0]));

        //Commands that aren't formally registered in a plugin.yml cannot give the custom deny message
        if (command.getPermission() == null) return;

        //Override "I'm sorry, Dave" message with custom denial message
        if (!player.hasPermission(command.getPermission()) || !player.isOp()) {
            player.sendMessage(plugin.language.getString("error.no-perm")
                    .replace("{command}", command.getName()));
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        //Kick the player if they're banned
        if (plugin.punishments.isBanned(uuid)) {
            long expiration = plugin.punishments.getLong("bans." + uuid + ".expiration");
            String message = (expiration == 0L)
                    ? plugin.language.getString("general.ban." + "permanent")
                    : plugin.language.getString("general.ban." + "temporary");

            message = message
                    .replace("{reason}", plugin.punishments.getString("bans." + uuid + ".reason"))
                    .replace("{time}", String.valueOf(expiration - System.currentTimeMillis()));

            Bukkit.getPlayer(player.getName()).kickPlayer(message);
        }

        AurumUser user = new AurumUser(uuid);
        user.load(uuid);
        updateUserData(player, user);

        //Broadcast welcome message if the player is new
        if (user.getBoolean("states.new", false)) {
            plugin.getServer().broadcastMessage(plugin.language.getString("general.welcome-message")
                    .replace("{name}", player.getName()));

            //Make sure the player spawns at the Aurum spawn
            if (plugin.settings.getLocation("data.spawn") == null) {
                //
            } else {
                player.teleport(plugin.settings.getLocation("data.spawn"));
            }
        }

        //Send MOTD
        List<String> motd = plugin.language.getStringList("commands.motd", null);
        motd.replaceAll(str -> str.replace("{name}", player.getName()));
        motd.replaceAll(str -> str.replace("{nickname}", player.getDisplayName()));
        motd.replaceAll(line -> line.replace("{prefix}", plugin.utils.getPrefix(player.getName())));
        motd.replaceAll(line -> line.replace("{suffix}", plugin.utils.getSuffix(player.getName())));
        for (String line : motd) {
            player.sendMessage(plugin.utils.colorize(line, true));
        }

        user.setProperty("states.new", false);
    }

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (plugin.punishments.isBanned(uuid)) return;
        AurumUser user = plugin.loadedUsers().get(uuid);

        user.setProperty("data.lastOnline", System.currentTimeMillis());
        user.save();

        user.unload();
    }

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerKick(PlayerKickEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (plugin.punishments.isBanned(uuid)) return;
        AurumUser user = plugin.loadedUsers().get(uuid);

        user.setProperty("data.lastOnline", System.currentTimeMillis());
        user.save();

        user.unload();
    }

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        //Cancel event if chatter is muted
        UUID uuid = player.getUniqueId();
        if (plugin.punishments.isMuted(uuid)) {
            player.sendMessage(plugin.language.getString("general.muted"));
            event.setCancelled(true);
        }

        //Use plugin's chat formatting
        String prefix = plugin.utils.getPrefix(player.getName());
        String finalMessage = plugin.utils.formatChat(player, event.getMessage(), false);
        event.setFormat(plugin.utils.colorize(finalMessage, true));
    }

    private void updateUserData(Player player, AurumUser user) {
        //Check if their username has changed since last join
        if (!user.getString("info.name").equals(player.getName())) {
            user.setProperty("info.name", player.getName());
        }

        //Check if they're joining from a different IP
        String address = user.getIP();
        if (user.hasProperty("info.address") &&
                !address.equals(player.getAddress().toString().substring(0, player.getAddress().toString().length() - 6))) {
            //
        }
        user.setProperty("info.address", player.getAddress().toString());

        //Check if their nickname has been updated since last join
        String nickname = user.getString("info.nickname");
        if (nickname != null) player.setDisplayName(settings.getString("general.nickname-prefix") + nickname);
    }
}
