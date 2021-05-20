package ru.skywatcher_2019.IPBinder;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class commandIPUnbind implements CommandExecutor {
    private final Main plugin;

    public commandIPUnbind(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("permissions.ipunbind"))) {
            sender.sendMessage(Messages.noPermission);
            return true;
        }

        FileConfiguration bindedips = plugin.bindedips;
        File bindedipsFile = plugin.bindedipsFile;
        int ip;
        String preIP;
        String nickname;
        char[] ipChars;
        String allowedChars = "1234567890.";

        if (sender instanceof Player) {
            if (args.length == 2) {
                if (sender.hasPermission(plugin.getConfig().getString("permissions.ipunbindother"))) {
                    preIP = args[1];
                    ipChars = preIP.toCharArray();
                    for (char c : ipChars) {
                        if (!allowedChars.contains(String.valueOf(c))) {
                            return false;
                        }
                    }
                    ip = Integer.parseInt(preIP);
                    nickname = args[0];
                } else {
                    sender.sendMessage(Messages.noPermission);
                    return true;
                }
            } else if (args.length == 1) {
                Player player = (Player) sender;
                nickname = player.getDisplayName();
                preIP = args[0];
                ipChars = preIP.toCharArray();
                for (char c : ipChars) {
                    if (!allowedChars.contains(String.valueOf(c))) {
                        return false;
                    }
                }
                ip = Integer.parseInt(preIP);
            } else return false;
        } else {
            if (args.length == 2) {
                preIP = args[1];
                ipChars = preIP.toCharArray();
                for (char c : ipChars) {
                    if (!allowedChars.contains(String.valueOf(c))) {
                        return false;
                    }
                }
                ip = Integer.parseInt(preIP);
                nickname = args[0];
            } else return false;
        }

        List<String> bindedIP = bindedips.getStringList(nickname);
        if (!bindedIP.isEmpty()) {
            if (ip < bindedIP.size() && ip > -1) {
                bindedIP.remove(ip);
                bindedips.set(nickname, bindedIP);
                try {
                    bindedips.save(bindedipsFile);
                    sender.sendMessage(Messages.unbindedIP(nickname, bindedIP.get(ip)));
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String noBindedIPMsg = Messages.noBindedIP(nickname);
                sender.sendMessage(noBindedIPMsg);
                return true;
            }
        } else {
            String noBindedIPMsg = Messages.emptyBindedIPs(nickname);
            sender.sendMessage(noBindedIPMsg);
            return true;
        }
        return false;
    }
}

