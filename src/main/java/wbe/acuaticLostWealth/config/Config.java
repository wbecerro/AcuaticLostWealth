package wbe.acuaticLostWealth.config;

import org.bukkit.configuration.file.FileConfiguration;
import wbe.acuaticLostWealth.rarities.FishingRarity;
import wbe.acuaticLostWealth.rarities.Reward;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Config {

    private FileConfiguration config;

    public int baseItemChance;
    public int baseCreatureChance;
    public int baseDoubleDropChance;
    public String itemMode;
    public String creatureMode;

    public String doubleDropSound;
    public String changeModeSound;
    public String fishCaughtSound;

    public String rodName;
    public List<String> rodLore = new ArrayList<>();
    public String rodMode;
    public String rodItemChance;
    public String rodCreatureChance;
    public String rodDoubleChance;

    public List<FishingRarity> rarities = new ArrayList<>();
    public int totalRarityWeight = 0;

    public Config(FileConfiguration config) {
        this.config = config;

        baseItemChance = config.getInt("Config.baseItemChance");
        baseCreatureChance = config.getInt("Config.baseCreatureChance");
        baseDoubleDropChance = config.getInt("Config.baseDoubleDropChance");
        itemMode = config.getString("Config.itemMode").replace("&", "§");
        creatureMode = config.getString("Config.creatureMode").replace("&", "§");
        doubleDropSound = config.getString("Sounds.doubleDropSound");
        changeModeSound = config.getString("Sounds.changeModeSound");
        fishCaughtSound = config.getString("Sounds.fishCaughtSound");
        rodName = config.getString("Items.FishingRod.name").replace("&", "§");
        rodLore = config.getStringList("Items.FishingRod.lore");
        rodMode = config.getString("Items.FishingRod.mode").replace("&", "§");
        rodItemChance = config.getString("Items.FishingRod.itemChance").replace("&", "§");
        rodCreatureChance = config.getString("Items.FishingRod.creatureChance").replace("&", "§");
        rodDoubleChance = config.getString("Items.FishingRod.doubleChance").replace("&", "§");

        loadRarities();
    }

    private void loadRarities() {
        Set<String> configRarities = config.getConfigurationSection("Rarities").getKeys(false);
        for(String rarity : configRarities) {
            String prefix = config.getString("Rarities." + rarity + ".prefix").replace("&", "§");
            int weight = config.getInt("Rarities." + rarity + ".weight");
            totalRarityWeight += weight;
            List<String> creatures = config.getStringList("Rarities." + rarity + ".creatures");
            String creatureSpawn = config.getString("Rarities." + rarity + ".creatureSpawn").replace("&", "§");
            List<Reward> rewards = getRewards(rarity);
            rarities.add(new FishingRarity(prefix, weight, creatures, creatureSpawn, rewards));
        }
    }

    private List<Reward> getRewards(String rarity) {
        List<Reward> finalRewards = new ArrayList<>();
        Set<String> rewards = config.getConfigurationSection("Rarities." + rarity + ".rewards").getKeys(false);
        for(String reward : rewards) {
            String suffix = config.getString("Rarities." + rarity + ".rewards." + reward + ".suffix").replace("&", "§");
            String command = config.getString("Rarities." + rarity + ".rewards." + reward + ".command");
            finalRewards.add(new Reward(suffix, command));
        }

        return finalRewards;
    }
}
