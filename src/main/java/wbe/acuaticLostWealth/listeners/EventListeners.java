package wbe.acuaticLostWealth.listeners;

import org.bukkit.plugin.PluginManager;
import wbe.acuaticLostWealth.AcuaticLostWealth;

public class EventListeners {

    private AcuaticLostWealth plugin;

    public EventListeners(AcuaticLostWealth plugin) {
        this.plugin = plugin;
    }

    public void initializeListeners() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerDropItemListeners(plugin), plugin);
        pluginManager.registerEvents(new PlayerFishListeners(plugin), plugin);
    }
}
