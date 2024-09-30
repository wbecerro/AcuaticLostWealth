package wbe.acuaticLostWealth.rarities;

import java.util.List;
import java.util.Random;

public class FishingRarity {

    private String internalName;

    private String prefix;

    private int weight;

    private List<String> creatures;

    private String creatureSpawn;

    private List<Reward> rewards;

    private int rewardsSize;

    public FishingRarity(String internalName, String prefix, int weight, List<String> creatures, String creatureSpawn, List<Reward> rewards) {
        this.internalName = internalName;
        this.prefix = prefix;
        this.weight = weight;
        this.creatures = creatures;
        this.creatureSpawn = creatureSpawn;
        this.rewards = rewards;
        this.rewardsSize = rewards.size();
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<String> getCreatures() {
        return creatures;
    }

    public void setCreatures(List<String> creatures) {
        this.creatures = creatures;
    }

    public String getCreatureSpawn() {
        return creatureSpawn;
    }

    public void setCreatureSpawn(String creatureSpawn) {
        this.creatureSpawn = creatureSpawn;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public Reward getRandomReward() {
        Random random = new Random();
        Reward reward = rewards.get(random.nextInt(rewardsSize));
        return reward;
    }
}
