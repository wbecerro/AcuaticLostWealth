package wbe.acuaticLostWealth.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.acuaticLostWealth.util.Utilities;

public class PlayerDropItemListeners implements Listener {

    private AcuaticLostWealth plugin;

    private Utilities utilities;

    public PlayerDropItemListeners(AcuaticLostWealth plugin) {
        this.plugin = plugin;
        this.utilities = new Utilities(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void switchModeOnDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(!player.isSneaking()) {
            return;
        }

        ItemStack droppedItem = event.getItemDrop().getItemStack();
        if(droppedItem.getType().equals(Material.AIR)) {
            return;
        }

        ItemMeta meta = droppedItem.getItemMeta();
        if(meta == null) {
            return;
        }

        NamespacedKey modeKey = new NamespacedKey(plugin, "ALWFishingMode");
        if(!meta.getPersistentDataContainer().has(modeKey)) {
            return;
        }

        NamespacedKey baseItemKey = new NamespacedKey(plugin, "baseItemChance");
        NamespacedKey alteredItemKey = new NamespacedKey(plugin, "alteredItemChance");
        NamespacedKey baseCreatureKey = new NamespacedKey(plugin, "baseCreatureChance");
        NamespacedKey alteredCreatureKey = new NamespacedKey(plugin, "alteredCreatureChance");

        int mode = meta.getPersistentDataContainer().get(modeKey, PersistentDataType.INTEGER);
        String newMode = "";
        double itemChance = 0;
        double creatureChance = 0;
        if(mode == 2) {
            itemChance = meta.getPersistentDataContainer().get(baseItemKey, PersistentDataType.DOUBLE);
            creatureChance = meta.getPersistentDataContainer().get(baseCreatureKey, PersistentDataType.DOUBLE);
            newMode = AcuaticLostWealth.config.itemMode;
            mode = 1;
        } else {
            itemChance = meta.getPersistentDataContainer().get(alteredItemKey, PersistentDataType.DOUBLE);
            creatureChance = meta.getPersistentDataContainer().get(alteredCreatureKey, PersistentDataType.DOUBLE);
            newMode = AcuaticLostWealth.config.creatureMode;
            mode = 2;
        }

        utilities.changeRodMode(droppedItem, itemChance, creatureChance, newMode);
        meta = droppedItem.getItemMeta();
        meta.getPersistentDataContainer().set(modeKey, PersistentDataType.INTEGER, mode);
        droppedItem.setItemMeta(meta);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(AcuaticLostWealth.messages.modeChanged
                .replace("%mode%", newMode)));
        player.playSound(player.getLocation(), Sound.valueOf(AcuaticLostWealth.config.changeModeSound), 1F, 1F);
        event.setCancelled(true);
    }
}
