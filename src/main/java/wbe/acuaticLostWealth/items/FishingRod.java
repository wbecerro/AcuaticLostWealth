package wbe.acuaticLostWealth.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.acuaticLostWealth.AcuaticLostWealth;

import java.util.ArrayList;

public class FishingRod extends ItemStack {

    private AcuaticLostWealth plugin;

    public FishingRod(AcuaticLostWealth plugin, int baseItemChance, int baseCreatureChance, int alteredItemChance, int alteredCreatureChance) {
        super(Material.FISHING_ROD);

        this.plugin = plugin;

        ItemMeta meta;
        if (hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(Material.FISHING_ROD);
        }

        meta.setDisplayName(AcuaticLostWealth.config.rodName);

        ArrayList<String> lore = new ArrayList<>();
        for(String line : AcuaticLostWealth.config.rodLore) {
            lore.add(line.replace("&", "§"));
        }
        lore.add(AcuaticLostWealth.config.rodMode.replace("%mode%", AcuaticLostWealth.config.itemMode));
        lore.add(AcuaticLostWealth.config.rodItemChance.replace("%item_chance%", String.valueOf(baseItemChance)));
        lore.add(AcuaticLostWealth.config.rodCreatureChance.replace("%creature_chance%", String.valueOf(baseCreatureChance)));
        meta.setLore(lore);
        setItemMeta(meta);

        setKeys(baseItemChance, baseCreatureChance, alteredItemChance, alteredCreatureChance);
    }

    private void setKeys(int baseItemChance, int baseCreatureChance, int alteredItemChance, int alteredCreatureChance) {
        ItemMeta meta = getItemMeta();
        NamespacedKey rodKey = new NamespacedKey(plugin, "ALWFishingRod");
        NamespacedKey modeKey = new NamespacedKey(plugin, "ALWFishingMode");
        NamespacedKey baseItemKey = new NamespacedKey(plugin, "baseItemChance");
        NamespacedKey alteredItemKey = new NamespacedKey(plugin, "alteredItemChance");
        NamespacedKey baseCreatureKey = new NamespacedKey(plugin, "baseCreatureChance");
        NamespacedKey alteredCreatureKey = new NamespacedKey(plugin, "alteredCreatureChance");

        meta.getPersistentDataContainer().set(rodKey, PersistentDataType.BOOLEAN, true);
        meta.getPersistentDataContainer().set(modeKey, PersistentDataType.INTEGER, 1);
        meta.getPersistentDataContainer().set(baseItemKey, PersistentDataType.INTEGER, baseItemChance);
        meta.getPersistentDataContainer().set(alteredItemKey, PersistentDataType.INTEGER, alteredItemChance);
        meta.getPersistentDataContainer().set(baseCreatureKey, PersistentDataType.INTEGER, baseCreatureChance);
        meta.getPersistentDataContainer().set(alteredCreatureKey, PersistentDataType.INTEGER, alteredCreatureChance);

        setItemMeta(meta);
    }
}
