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

    public void initialize() {
        plugin.tpRequestCache().add(this);
        target.sendMessage("§d" + owner.getDisplayName() + " §5sent a teleport request!");
        target.sendMessage("§7Run §5/tpaccept §7to accept, or §5/tpdeny §7to deny.");
        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, this::expire, 1200L);
    }

    public void expire() {
        if (!plugin.tpRequestCache().contains(this)) {
            return;
        }
        owner.sendMessage("§7Your teleport request to§5 " + target.getDisplayName() + " §7has expired.");
        target.sendMessage("§5" + owner.getDisplayName() + "'s §7teleport request has expired.");
        plugin.tpRequestCache().remove(this);
    }

    public void accept() {
        owner.sendMessage("§a" + target.getDisplayName() + " §2accepted your teleport request!");
        target.sendMessage("§5Accepted §d" + owner.getDisplayName() + "'s §5 teleport request!");
        owner.teleport(target);
        plugin.tpRequestCache().remove(this);
    }

    public void deny() {
        owner.sendMessage("§c" + target.getDisplayName() + " §4denied your teleport request!");
        target.sendMessage("§5Denied §d" + owner.getDisplayName() + "'s §5 teleport request!");
        plugin.tpRequestCache().remove(this);
    }
}
