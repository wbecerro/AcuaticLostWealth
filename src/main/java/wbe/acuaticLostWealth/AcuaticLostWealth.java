package wbe.acuaticLostWealth;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import wbe.acuaticLostWealth.commands.CommandListener;
import wbe.acuaticLostWealth.config.Config;
import wbe.acuaticLostWealth.config.Messages;
import wbe.acuaticLostWealth.listeners.EventListeners;

import java.io.File;

public final class AcuaticLostWealth extends JavaPlugin {

    private FileConfiguration configuration;

    private final CommandListener commandListener = new CommandListener(this);

    private final EventListeners eventListeners = new EventListeners(this);

    public static Config config;

    public static Messages messages;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("AcuaticLostWealth enabled correctly");
        reloadConfiguration();
        config = new Config(configuration);

        getCommand("acuaticlostwealth").setExecutor(this.commandListener);
        this.eventListeners.initializeListeners();
    }

    @Override
    public void onDisable() {
        reloadConfig();
        getLogger().info("AcuaticLostWealth disabled correctly");
    }

    public void reloadConfiguration() {
        if(!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }
        reloadConfig();
        configuration = getConfig();
        config = new Config(configuration);
        messages = new Messages(configuration);
    }
}
