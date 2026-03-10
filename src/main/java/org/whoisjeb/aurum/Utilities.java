package org.whoisjeb.aurum;

import com.projectposeidon.johnymuffin.UUIDManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
    private final Aurum plugin;

    public Utilities(Aurum plugin) {
        this.plugin = plugin;
    }

    public String colorize(String input, boolean isAllowed) {
        String regex = "&([0-9a-f])";
        String replacement = isAllowed ? "§$1" : "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.find() ? matcher.replaceAll(replacement) : input;
    }

    public String formatChat(Player player, String message, boolean hasTarget) {
        //Define necessary variables
        String prefix = getPrefix(player.getName());
        String color = getColor(player.getName());
        String suffix = getSuffix(player.getName());
        String target = hasTarget ? player.getDisplayName() : "";

        String displayName = plugin.settings.getString("chat.display-name")
                .replace("%prefix%", prefix)
                .replace("%color%", color)
                .replace("%name%", player.getDisplayName())
                .replace("%suffix%", suffix);

        message = colorize(message, player.hasPermission("aurum.color"));

        //Replace placeholders with content
        String format = plugin.settings.getString("chat.format.normal")
                .replace("%prefix%", prefix)
                .replace("%color%", color)
                .replace("%display-name%", displayName)
                .replace("%name%", player.getName())
                .replace("%message%", message);

        return colorize(format, true);
    }

    public UUID getUUID(OfflinePlayer player) {
        return UUIDManager.getInstance().getUUIDFromUsername(player.getName());
    }

    public UUID getUUID(String name) {
        return UUIDManager.getInstance().getUUIDFromUsername(name);
    }

    public String getUsername(UUID uuid) {
        return UUIDManager.getInstance().getUsernameFromUUID(uuid);
    }

    public String getPrefix(String name) {
        return (PermissionsEx.getPermissionManager().getUser(name).getPrefix() != null)
                ? PermissionsEx.getPermissionManager().getUser(name).getPrefix() : "";
    }

    public String getPrefix(UUID uuid) {
        return (PermissionsEx.getPermissionManager().getUser(getUsername(uuid)).getPrefix() != null)
                ? PermissionsEx.getPermissionManager().getUser(getUsername(uuid)).getPrefix() : "";
    }

    public String getSuffix(String name) {
        return (PermissionsEx.getPermissionManager().getUser(name).getSuffix() != null)
                ? PermissionsEx.getPermissionManager().getUser(name).getSuffix() : "";
    }

    public String getSuffix(UUID uuid) {
        return (PermissionsEx.getPermissionManager().getUser(getUsername(uuid)).getSuffix() != null)
                ? PermissionsEx.getPermissionManager().getUser(getUsername(uuid)).getSuffix() : "";
    }

    public String getColor(String name) {
        return (PermissionsEx.getPermissionManager().getUser(name).getOption("color") != null)
                ? PermissionsEx.getPermissionManager().getUser(name).getOption("color") : "";
    }

    public String getColor(UUID uuid) {
        return (PermissionsEx.getPermissionManager().getUser(getUsername(uuid)).getOption("color") != null)
                ? PermissionsEx.getPermissionManager().getUser(getUsername(uuid)).getOption("color") : "";
    }
}
