package wbe.acuaticLostWealth.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import wbe.acuaticLostWealth.AcuaticLostWealth;

public class PlayerInteractListeners implements Listener {

    private AcuaticLostWealth plugin;

    public PlayerInteractListeners(AcuaticLostWealth plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void switchModeOnInteract(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            return;
        }

        Player player = event.getPlayer();
        if(!player.isSneaking()) {
            return;
        }
    }
}
