package wbe.acuaticLostWealth.config;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import org.bukkit.configuration.file.FileConfiguration;
import wbe.acuaticLostWealth.rarities.FishingRarity;
import wbe.acuaticLostWealth.rarities.Reward;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Config {

    private FileConfiguration config;

    public double baseItemChance;
    public double baseCreatureChance;
    public double baseDoubleDropChance;
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
    public String boostChance;

    public List<FishingRarity> rarities = new ArrayList<>();
    public double totalRarityWeight = 0;

    public Config(FileConfiguration config) {
        this.config = config;

        baseItemChance = config.getDouble("Config.baseItemChance");
        baseCreatureChance = config.getDouble("Config.baseCreatureChance");
        baseDoubleDropChance = config.getDouble("Config.baseDoubleDropChance");
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
        boostChance = config.getString("Items.FishingRod.boostChance").replace("&", "§");

        loadRarities();
    }

    private void loadRarities() {
        Set<String> configRarities = config.getConfigurationSection("Rarities").getKeys(false);
        for(String rarity : configRarities) {
            String name = config.getString("Rarities." + rarity + ".name").replace("&", "§");
            String prefix = config.getString("Rarities." + rarity + ".prefix").replace("&", "§");
            double weight = config.getDouble("Rarities." + rarity + ".weight");
            totalRarityWeight += weight;
            List<String> creatures = config.getStringList("Rarities." + rarity + ".creatures");
            String creatureSpawn = config.getString("Rarities." + rarity + ".creatureSpawn").replace("&", "§");
            PrimarySkillType skill = PrimarySkillType.valueOf(config.getString("Rarities." + rarity + ".mcmmoSkill").toUpperCase());
            int level = config.getInt("Rarities." + rarity + ".mcmmoLevel");
            List<Reward> rewards = getRewards(rarity);
            String broadcast = "";
            if(config.contains("Rarities." + rarity + ".broadcast")) {
                broadcast = config.getString("Rarities." + rarity + ".broadcast").replace("&", "§");
            }
            String title = "";
            if(config.contains("Rarities." + rarity + ".title")) {
                title = config.getString("Rarities." + rarity + ".title").replace("&", "§");
            }
            int fireworks = -1;
            if(config.contains("Rarities." + rarity + ".fireworks")) {
                fireworks = config.getInt("Rarities." + rarity + ".fireworks");
            }
            rarities.add(new FishingRarity(rarity, name, prefix, weight, creatures, creatureSpawn, skill, level, rewards, broadcast, title, fireworks));
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
