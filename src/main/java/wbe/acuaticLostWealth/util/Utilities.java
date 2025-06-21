package wbe.acuaticLostWealth.util;

import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.acuaticLostWealth.rarities.FishingRarity;
import wbe.acuaticLostWealth.rarities.Reward;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utilities {

    AcuaticLostWealth plugin;

    public Utilities(AcuaticLostWealth plugin) {
        this.plugin = plugin;
    }

    public ItemStack changeRodMode(ItemStack rod, double itemChance, double creatureChance, String mode) {
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

    public double getPlayerCreatureChance(Player player) {
        double chance = AcuaticLostWealth.config.baseCreatureChance;

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

        NamespacedKey baseCreatureKey = new NamespacedKey(plugin, "baseCreatureChance");
        if(player.getPersistentDataContainer().has(baseCreatureKey)) {
            chance += player.getPersistentDataContainer().get(baseCreatureKey, PersistentDataType.DOUBLE);
        }

        return chance;
    }

    private double getItemCreatureChance(ItemStack item) {
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
                return meta.getPersistentDataContainer().get(baseCreatureKey, PersistentDataType.DOUBLE);
            } else {
                NamespacedKey alteredCreatureKey = new NamespacedKey(plugin, "alteredCreatureChance");
                return meta.getPersistentDataContainer().get(alteredCreatureKey, PersistentDataType.DOUBLE);
            }
        }

        // Caso general
        NamespacedKey baseCreatureKey = new NamespacedKey(plugin, "baseCreatureChance");
        if(meta.getPersistentDataContainer().has(baseCreatureKey)) {
            return meta.getPersistentDataContainer().get(baseCreatureKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public double getPlayerItemChance(Player player) {
        double chance = AcuaticLostWealth.config.baseItemChance;

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

        NamespacedKey baseItemKey = new NamespacedKey(plugin, "baseItemChance");
        if(player.getPersistentDataContainer().has(baseItemKey)) {
            chance += player.getPersistentDataContainer().get(baseItemKey, PersistentDataType.DOUBLE);
        }

        return chance;
    }

    private double getItemItemChance(ItemStack item) {
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
                return meta.getPersistentDataContainer().get(baseItemKey, PersistentDataType.DOUBLE);
            } else {
                NamespacedKey alteredItemKey = new NamespacedKey(plugin, "alteredItemChance");
                return meta.getPersistentDataContainer().get(alteredItemKey, PersistentDataType.DOUBLE);
            }
        }

        // Caso general
        NamespacedKey baseItemKey = new NamespacedKey(plugin, "baseItemChance");
        if(meta.getPersistentDataContainer().has(baseItemKey)) {
            return meta.getPersistentDataContainer().get(baseItemKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public double getPlayerDoubleChance(Player player) {
        double chance = 0;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemDoubleChance(mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemDoubleChance(offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemDoubleChance(item);
        }

        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "baseDoubleChance");
        if(player.getPersistentDataContainer().has(baseDoubleKey)) {
            chance += player.getPersistentDataContainer().get(baseDoubleKey, PersistentDataType.DOUBLE);
        }

        return chance;
    }

    private double getItemDoubleChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        // Caso general
        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "baseDoubleChance");
        if(meta.getPersistentDataContainer().has(baseDoubleKey)) {
            return meta.getPersistentDataContainer().get(baseDoubleKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public double getPlayerBoostedChance(FishingRarity rarity, Player player) {
        double chance = 0;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemBoostedChance(rarity, mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemBoostedChance(rarity, offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemBoostedChance(rarity, item);
        }

        NamespacedKey rarityKey = new NamespacedKey(plugin, "boostRarity");
        if(player.getPersistentDataContainer().has(rarityKey)) {
            if(player.getPersistentDataContainer().get(rarityKey, PersistentDataType.STRING).equalsIgnoreCase(rarity.getInternalName())) {
                NamespacedKey percentKey = new NamespacedKey(plugin, "boostRarityPercent");
                if(player.getPersistentDataContainer().has(percentKey)) {
                    chance += player.getPersistentDataContainer().get(percentKey, PersistentDataType.DOUBLE);
                }
            }
        }

        return chance;
    }

    private double getItemBoostedChance(FishingRarity rarity, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        NamespacedKey rarityKey = new NamespacedKey(plugin, "boostRarity");
        if(meta.getPersistentDataContainer().has(rarityKey)) {
            if(!meta.getPersistentDataContainer().get(rarityKey, PersistentDataType.STRING).equalsIgnoreCase(rarity.getInternalName())) {
                return 0;
            }
        }

        NamespacedKey percentKey = new NamespacedKey(plugin, "boostRarityPercent");
        if(meta.getPersistentDataContainer().has(percentKey)) {
            return meta.getPersistentDataContainer().get(percentKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public FishingRarity calculateRarity() {
        Random random = new Random();
        double randomNumber = random.nextDouble(AcuaticLostWealth.config.totalRarityWeight);
        double weight = 0;
        List<FishingRarity> rarities = AcuaticLostWealth.config.rarities;

        for(FishingRarity rarity : rarities) {
            weight += rarity.getWeight();
            if(randomNumber < weight) {
                return rarity;
            }
        }

        return rarities.get(rarities.size() - 1);
    }

    public FishingRarity calculateRarityWithBoost(FishingRarity boostedRarity, double percent) {
        double boostedAmount = getBoostedAmount(boostedRarity, percent);
        Random random = new Random();
        double randomNumber = random.nextDouble(AcuaticLostWealth.config.totalRarityWeight + boostedAmount);
        double weight = 0;
        List<FishingRarity> rarities = AcuaticLostWealth.config.rarities;

        for(FishingRarity rarity : rarities) {
            if(rarity.getInternalName().equalsIgnoreCase(boostedRarity.getInternalName())) {
                weight += boostedAmount;
            }
            weight += rarity.getWeight();
            if(randomNumber < weight) {
                return rarity;
            }
        }

        return rarities.get(rarities.size() - 1);
    }

    public FishingRarity getRarity(Player player) {
        for(FishingRarity configRarity : AcuaticLostWealth.config.rarities) {
            double boosted = getPlayerBoostedChance(configRarity, player);
            if(boosted != 0) {
                return calculateRarityWithBoost(configRarity, boosted);
            }
        }

        return calculateRarity();
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

    /**
     * Método para añadir probabilidad de objetos, criaturas, doble recompensa o rareza mejorada a un jugador.
     *
     * @param player Jugador al que añadir
     * @param chance Probabiliad a añadir
     * @param type Tipo de probabilidad 0 -> objetos, 1 -> criaturas, 2 -> doble, 3 -> rareza mejorada
     * @param rarity Parámetro exclusivo para el uso de rareza mejorada
     */
    public void addChanceToPlayer(Player player, double chance, int type, String rarity) {
        NamespacedKey key;
        switch(type) {
            case 0:
                key = new NamespacedKey(plugin, "itemChance");
                break;
            case 1:
                key = new NamespacedKey(plugin, "creatureChance");
                break;
            case 2:
                key = new NamespacedKey(plugin, "doubleChance");
                break;
            default:
                key = new NamespacedKey(plugin, "boostRarityPercent");
                NamespacedKey rarityKey = new NamespacedKey(plugin, "boostRarity");
                player.getPersistentDataContainer().set(rarityKey, PersistentDataType.STRING, rarity);
                break;
        }

        player.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, chance);
    }

    /**
     * Método para quitar probabilidad de objetos, criaturas, doble recompensa o rareza mejorada a un jugador.
     *
     * @param player Jugador al que quitar
     * @param chance Probabiliad a quitar
     * @param type Tipo de probabilidad 0 -> objetos, 1 -> criaturas, 2 -> doble, 3 -> rareza mejorada
     * @param rarity Parámetro exclusivo para el uso de rareza mejorada
     */
    public void removeChanceFromPlayer(Player player, double chance, int type, String rarity) {
        NamespacedKey key;
        switch(type) {
            case 0:
                key = new NamespacedKey(plugin, "itemChance");
                break;
            case 1:
                key = new NamespacedKey(plugin, "creatureChance");
                break;
            case 2:
                key = new NamespacedKey(plugin, "doubleChance");
                break;
            default:
                key = new NamespacedKey(plugin, "boostRarityPercent");
                break;
        }

        if(!player.getPersistentDataContainer().has(key)) {
            return;
        }

        double playerChance = player.getPersistentDataContainer().get(key, PersistentDataType.DOUBLE);
        playerChance -= chance;
        if(playerChance <= 0) {
            player.getPersistentDataContainer().remove(key);
            if(type > 2) {
                player.getPersistentDataContainer().remove(new NamespacedKey(plugin, "boostRarity"));
            }
        } else {
            player.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, playerChance);
        }
    }

    public void addDoubleDropChance(ItemStack item, double chance) {
        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "baseDoubleChance");
        String loreLine = AcuaticLostWealth.config.rodDoubleChance
                .replace("%double_chance%", String.valueOf(chance));
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(loreLine);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(baseDoubleKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    public void addItemChance(ItemStack item, double chance) {
        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "baseItemChance");
        String loreLine = AcuaticLostWealth.config.rodItemChance
                .replace("%item_chance%", String.valueOf(chance));
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(loreLine);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(baseDoubleKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    public void addCreatureChance(ItemStack item, double chance) {
        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "baseCreatureChance");
        String loreLine = AcuaticLostWealth.config.rodCreatureChance
                .replace("%creature_chance%", String.valueOf(chance));
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(loreLine);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(baseDoubleKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    public void addBoostRarityChance(ItemStack item, double chance, String rarityName) {
        NamespacedKey percentKey = new NamespacedKey(plugin, "boostRarityPercent");
        NamespacedKey rarityKey = new NamespacedKey(plugin, "boostRarity");
        FishingRarity rarity = getRarityByName(rarityName);
        String loreLine = AcuaticLostWealth.config.boostChance
                .replace("%boost_chance%", String.valueOf((int) (chance * 100)))
                .replace("%rarity%", rarity.getName());
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(loreLine);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(rarityKey, PersistentDataType.STRING, rarityName);
        meta.getPersistentDataContainer().set(percentKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    private FishingRarity getRarityByName(String name) {
        for(FishingRarity rarity : AcuaticLostWealth.config.rarities) {
            if(rarity.getInternalName().equalsIgnoreCase(name)) {
                return rarity;
            }
        }

        return null;
    }

    public FireworkMeta getRandomFirework(Firework firework) {
        Random random = new Random();
        FireworkMeta meta = firework.getFireworkMeta();
        meta.setPower(random.nextInt(3) + 1);
        meta.addEffect(FireworkEffect.builder()
                .with(getRandomFireworkType())
                .flicker(random.nextBoolean())
                .trail(random.nextBoolean())
                .withColor(Color.fromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                .withColor(Color.fromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                .withFade(Color.fromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                .build());
        return meta;
    }

    private double getBoostedAmount(FishingRarity boostedRarity, double percent) {
        double booster = 1 + percent;
        double newWeight = boostedRarity.getWeight() * booster;
        return newWeight - boostedRarity.getWeight();
    }

    private FireworkEffect.Type getRandomFireworkType() {
        Random random = new Random();
        return FireworkEffect.Type.values()[random.nextInt(FireworkEffect.Type.values().length)];
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
