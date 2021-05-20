package ru.skywatcher_2019.IPBinder;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class commandIPBind implements CommandExecutor {
    private final Main plugin;

    public commandIPBind(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("permissions.ipbind"))) {
            sender.sendMessage(Messages.noPermission);
            return true;
        }

        FileConfiguration bindedips = plugin.bindedips;
        File bindedipsFile = plugin.bindedipsFile;
        String ip;
        String nickname;

        if (sender instanceof Player) {
            if (args.length == 2) {
                if (sender.hasPermission(plugin.getConfig().getString("permissions.ipbindother"))) {
                    ip = args[1];
                    nickname = args[0];

                } else {
                    sender.sendMessage(Messages.noPermission);
                    return true;
                }

            } else {
                Player player = (Player) sender;
                nickname = player.getDisplayName();
                ip = player.getAddress().getAddress().toString().replace("/", "");
            }
        } else {
            if (args.length == 2) {
                ip = args[1];
                nickname = args[0];
            } else return false;
        }

        List<String> bindedIP = bindedips.getStringList(nickname);
        if (bindedIP.contains(ip)) {
            String alrPrtMsg = Messages.alreadyBindedIP(nickname, ip);
            sender.sendMessage(alrPrtMsg);
            return true;
        } else {
            bindedIP.add(ip);
            bindedips.set(nickname, bindedIP);
            String bindedipMsg = Messages.bindedIP(nickname, ip);

            try {
                bindedips.save(bindedipsFile);
                sender.sendMessage(bindedipMsg);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
