package ru.skywatcher_2019.IPBinder;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class commandReload implements CommandExecutor {
    private final Main plugin;

    public commandReload(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender.hasPermission(plugin.getConfig().getString("permissions.reload")))  {
            plugin.reloadConfig();
            sender.sendMessage("§aПлагин перезагружен!");
        }
        else {
            sender.sendMessage(Messages.noPermission);
        }
        return true;
    }
}
