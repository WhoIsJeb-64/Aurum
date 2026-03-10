package org.whoisjeb.aurum.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.whoisjeb.aurum.Aurum;

public class TeleportRequest {
    private final Aurum plugin;
    private final Player owner;
    private final Player target;
    private final Long start;

    public TeleportRequest(Aurum plugin, Player owner, Player target) {
        this.plugin = plugin;
        this.owner = owner;
        this.target = target;
        this.start = System.currentTimeMillis();
    }

    public Player getOwner() {
        return owner;
    }

    public Player getTarget() {
        return target;
    }

    public void send(boolean isToTarget) {
        plugin.tpRequestCache().add(this);

        target.sendMessage(message("sent." + ((isToTarget) ? "normal" : "here"))
                .replace("%player%", owner.getName()));
        target.sendMessage(message("prompt-answer"));

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, this::expire, 1200L);
    }

    public void expire() {
        if (!plugin.tpRequestCache().contains(this)) return;

        owner.sendMessage(message("sender.expired").replace("%player%", target.getDisplayName()));
        target.sendMessage(message("target.expired").replace("%player%", owner.getDisplayName()));

        plugin.tpRequestCache().remove(this);
    }

    public void accept() {
        owner.sendMessage(message("sender.accepted").replace("%player%", target.getDisplayName()));
        target.sendMessage(message("target.accepted").replace("%player%", owner.getDisplayName()));

        owner.teleport(target);
        plugin.tpRequestCache().remove(this);
    }

    public void deny() {
        owner.sendMessage(message("sender.denied").replace("%player%", target.getDisplayName()));
        target.sendMessage(message("target.denied").replace("%player%", owner.getDisplayName()));

        plugin.tpRequestCache().remove(this);
    }

    private String message(String path) {
        return plugin.utils.colorize(plugin.language.getString("commands.teleportask." + path), true);
    }
}
