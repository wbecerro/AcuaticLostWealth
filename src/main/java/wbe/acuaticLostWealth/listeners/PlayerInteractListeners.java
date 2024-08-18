package wbe.acuaticLostWealth.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.acuaticLostWealth.util.Utilites;

public class PlayerInteractListeners implements Listener {

    private AcuaticLostWealth plugin;

    private Utilites utilites;

    public PlayerInteractListeners(AcuaticLostWealth plugin) {
        this.plugin = plugin;
        this.utilites = new Utilites(plugin);
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

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        if(mainHand.getType().equals(Material.AIR)) {
            return;
        }

        ItemMeta meta = mainHand.getItemMeta();
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
        int itemChance = 0;
        int creatureChance = 0;
        if(mode == 2) {
            itemChance = meta.getPersistentDataContainer().get(baseItemKey, PersistentDataType.INTEGER);
            creatureChance = meta.getPersistentDataContainer().get(baseCreatureKey, PersistentDataType.INTEGER);
            newMode = AcuaticLostWealth.config.itemMode;
            mode = 1;
        } else {
            itemChance = meta.getPersistentDataContainer().get(alteredItemKey, PersistentDataType.INTEGER);
            creatureChance = meta.getPersistentDataContainer().get(alteredCreatureKey, PersistentDataType.INTEGER);
            newMode = AcuaticLostWealth.config.creatureMode;
            mode = 2;
        }

        utilites.changeRodMode(mainHand, itemChance, creatureChance, newMode);
        meta = mainHand.getItemMeta();
        meta.getPersistentDataContainer().set(modeKey, PersistentDataType.INTEGER, mode);
        mainHand.setItemMeta(meta);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(AcuaticLostWealth.messages.modeChanged
                .replace("%mode%", newMode)));
    }
}
