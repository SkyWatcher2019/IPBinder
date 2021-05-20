package ru.skywatcher_2019.IPBinder;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class commandBindedIPs implements CommandExecutor {
    private final Main plugin;

    public commandBindedIPs(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("permissions.bindedips"))) {
            sender.sendMessage(Messages.noPermission);
            return true;
        }

        FileConfiguration bindedIPs = plugin.bindedips;
        List<String> bindedIP;
        String nickname;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                if (sender.hasPermission(plugin.getConfig().getString("permissions.bindedipsother"))) {
                    nickname = args[0];
                } else {
                    sender.sendMessage(Messages.noPermission);
                    return true;
                }
            } else if (args.length == 0) {
                nickname = player.getDisplayName();
            } else return false;

            bindedIP = bindedIPs.getStringList(nickname);
            if (bindedIP.isEmpty()) {
                sender.sendMessage(Messages.emptyBindedIPs(nickname));
                return true;
            }

            sender.sendMessage("§eПривязанные IP:");
            int index = 0;
            String ip = player.getAddress().getAddress().toString().replace("/", "");
            for (String s : bindedIP) {
                if (s.contains(ip)) s = s + "§e ◄";
                player.sendMessage("§e" + index + " | §b" + s);
                index++;
            }
            return true;
        } else {
            if (args.length != 1) return false;
            else bindedIP = bindedIPs.getStringList(args[0]);

            if (bindedIP.isEmpty()) {
                sender.sendMessage(Messages.emptyBindedIPs(args[0]));
                return true;
            }

            sender.sendMessage("§eПривязанные IP:");
            int index = 0;
            for (String s : bindedIP) {
                sender.sendMessage("§e" + index + " | §b" + s);
                index++;
            }
        }
        return true;
    }
}