package wbe.acuaticLostWealth;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import wbe.acuaticLostWealth.commands.CommandListener;
import wbe.acuaticLostWealth.commands.TabListener;
import wbe.acuaticLostWealth.config.Config;
import wbe.acuaticLostWealth.config.Messages;
import wbe.acuaticLostWealth.listeners.EventListeners;
import wbe.acuaticLostWealth.papi.PapiExtension;

import java.io.File;

public final class AcuaticLostWealth extends JavaPlugin {

    private FileConfiguration configuration;

    private PapiExtension papiExtension;

    private final CommandListener commandListener = new CommandListener(this);

    private final TabListener tabListener = new TabListener();

    private final EventListeners eventListeners = new EventListeners(this);

    public static Config config;

    public static Messages messages;

    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papiExtension = new PapiExtension(this);
            papiExtension.register();
        }
        saveDefaultConfig();
        getLogger().info("AcuaticLostWealth enabled correctly");
        reloadConfiguration();
        config = new Config(configuration);

        getCommand("acuaticlostwealth").setExecutor(this.commandListener);
        getCommand("acuaticlostwealth").setTabCompleter(this.tabListener);
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
