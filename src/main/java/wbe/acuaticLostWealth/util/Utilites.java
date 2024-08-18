package wbe.acuaticLostWealth.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.acuaticLostWealth.items.FishingRod;

import java.util.List;

public class Utilites {

    AcuaticLostWealth plugin;

    public Utilites(AcuaticLostWealth plugin) {
        this.plugin = plugin;
    }

    public void giveFishingRod(Player player, int baseItemChance, int baseCreatureChance, int alteredItemChance, int alteredCreatureChance) {
        FishingRod fishingRod = new FishingRod(plugin, baseItemChance, baseCreatureChance, alteredItemChance, alteredCreatureChance);
        player.getInventory().addItem(new ItemStack[]{fishingRod});
        player.sendMessage(AcuaticLostWealth.messages.rodGiven);
        player.updateInventory();
    }

    public ItemStack changeRodMode(ItemStack rod, int itemChance, int creatureChance, String mode) {
        int modeLine = findLine(rod, AcuaticLostWealth.config.rodMode.split(":")[0]);
        int itemChanceLine = findLine(rod, AcuaticLostWealth.config.rodItemChance.split(":")[0]);
        int creatureChanceLine = findLine(rod, AcuaticLostWealth.config.rodCreatureChance.split(":")[0]);

        ItemMeta meta = rod.getItemMeta();
        List<String> lore = meta.getLore();

        lore.set(modeLine, AcuaticLostWealth.config.rodMode.replace("%mode%", mode));
        lore.set(itemChanceLine, AcuaticLostWealth.config.rodItemChance
                .replace("%item_chance%", String.valueOf(itemChance)));
        lore.set(creatureChanceLine, AcuaticLostWealth.config.rodCreatureChance
                .replace("%creature_chance%", String.valueOf(creatureChance)));

        meta.setLore(lore);
        rod.setItemMeta(meta);

        return rod;
    }

    private int findLine(ItemStack item, String line) {
        List<String> lore = item.getItemMeta().getLore();
        int size = lore.size();
        for(int i=0;i<size;i++) {
            if(lore.get(i).contains(line)) {
                return i;
            }
        }

        return -1;
    }
}
