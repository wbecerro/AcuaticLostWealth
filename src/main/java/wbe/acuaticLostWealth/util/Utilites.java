package wbe.acuaticLostWealth.util;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.acuaticLostWealth.items.FishingRod;
import wbe.acuaticLostWealth.rarities.FishingRarity;
import wbe.acuaticLostWealth.rarities.Reward;

import java.util.List;
import java.util.Random;

public class Utilites {

    AcuaticLostWealth plugin;

    public Utilites(AcuaticLostWealth plugin) {
        this.plugin = plugin;
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

    public int getPlayerCreatureChance(Player player) {
        int chance = AcuaticLostWealth.config.baseCreatureChance;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemCreatureChance(mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemCreatureChance(offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemCreatureChance(item);
        }

        return chance;
    }

    private int getItemCreatureChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        // Caso de caña
        NamespacedKey modeKey = new NamespacedKey(plugin, "ALWFishingMode");
        if(meta.getPersistentDataContainer().has(modeKey)) {
            int mode = meta.getPersistentDataContainer().get(modeKey, PersistentDataType.INTEGER);
            if(mode == 1) {
                NamespacedKey baseCreatureKey = new NamespacedKey(plugin, "baseCreatureChance");
                return meta.getPersistentDataContainer().get(baseCreatureKey, PersistentDataType.INTEGER);
            } else {
                NamespacedKey alteredCreatureKey = new NamespacedKey(plugin, "alteredCreatureChance");
                return meta.getPersistentDataContainer().get(alteredCreatureKey, PersistentDataType.INTEGER);
            }
        }

        // Caso general
        NamespacedKey baseCreatureKey = new NamespacedKey(plugin, "baseCreatureChance");
        if(meta.getPersistentDataContainer().has(baseCreatureKey)) {
            return meta.getPersistentDataContainer().get(baseCreatureKey, PersistentDataType.INTEGER);
        }

        return 0;
    }

    public int getPlayerItemChance(Player player) {
        int chance = AcuaticLostWealth.config.baseItemChance;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemItemChance(mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemItemChance(offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemItemChance(item);
        }

        return chance;
    }

    private int getItemItemChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        // Caso de caña
        NamespacedKey modeKey = new NamespacedKey(plugin, "ALWFishingMode");
        if(meta.getPersistentDataContainer().has(modeKey)) {
            int mode = meta.getPersistentDataContainer().get(modeKey, PersistentDataType.INTEGER);
            if(mode == 1) {
                NamespacedKey baseItemKey = new NamespacedKey(plugin, "baseItemChance");
                return meta.getPersistentDataContainer().get(baseItemKey, PersistentDataType.INTEGER);
            } else {
                NamespacedKey alteredItemKey = new NamespacedKey(plugin, "alteredItemChance");
                return meta.getPersistentDataContainer().get(alteredItemKey, PersistentDataType.INTEGER);
            }
        }

        // Caso general
        NamespacedKey baseItemKey = new NamespacedKey(plugin, "baseItemChance");
        if(meta.getPersistentDataContainer().has(baseItemKey)) {
            return meta.getPersistentDataContainer().get(baseItemKey, PersistentDataType.INTEGER);
        }

        return 0;
    }

    public FishingRarity calculateRarity() {
        Random random = new Random();
        int randomNumber = random.nextInt(AcuaticLostWealth.config.totalRarityWeight);
        int weight = 0;
        List<FishingRarity> rarities = AcuaticLostWealth.config.rarities;

        for(FishingRarity rarity : rarities) {
            weight += rarity.getWeight();
            if(randomNumber < weight) {
                return rarity;
            }
        }

        return rarities.get(rarities.size() - 1);
    }

    public String getRandomCreature(FishingRarity rarity) {
        Random random = new Random();
        return rarity.getCreatures().get(random.nextInt(rarity.getCreatures().size()));
    }

    public Reward getRandomReward(FishingRarity rarity) {
        List<Reward> rewards = rarity.getRewards();
        Random random = new Random();

        return rewards.get(random.nextInt(rewards.size()));
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
