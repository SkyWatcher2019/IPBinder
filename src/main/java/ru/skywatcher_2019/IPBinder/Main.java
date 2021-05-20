package ru.skywatcher_2019.IPBinder;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    public File bindedipsFile = new File(getDataFolder() + File.separator + "bindedips.yml");
    public FileConfiguration bindedips = YamlConfiguration.loadConfiguration(bindedipsFile);

    @Override
    public void onEnable() {
        File config = new File(getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
            getLogger().info("config.yml created");
        }

        if (!bindedipsFile.exists()) {
            try {
                if (bindedipsFile.createNewFile()) getLogger().info("bindedips.yml created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        getCommand("ipbind").setExecutor(new commandIPBind(this));
        getCommand("ipunbind").setExecutor(new commandIPUnbind(this));
        getCommand("bindedips").setExecutor(new commandBindedIPs(this));
        getCommand("ipbindreload").setExecutor(new commandReload(this));

        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);

        getLogger().info("IPBinder enabled");
    }
}
