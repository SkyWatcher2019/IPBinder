package ru.skywatcher_2019.IPBinder;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class JoinListener implements Listener {
    private final Main plugin;

    public JoinListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String playerIP = player.getAddress().getAddress().toString().replace("/", "");
        FileConfiguration bindedips = plugin.bindedips;
        List<String> bindedIP = bindedips.getStringList(player.getDisplayName());

        plugin.getLogger().info(playerIP);
        plugin.getLogger().info(bindedIP.toString());

        if (!bindedIP.contains(playerIP) && !bindedIP.isEmpty()) {
            player.kickPlayer(Messages.kick(playerIP));
            plugin.getLogger().info("Kicked " + player.getDisplayName() + " for not whitelisted IP");
        }
    }
}
