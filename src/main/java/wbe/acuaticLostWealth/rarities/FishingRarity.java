package wbe.acuaticLostWealth.rarities;

import java.util.List;
import java.util.Random;

public class FishingRarity {

    private String prefix;

    private int weight;

    private List<String> creatures;

    private List<Reward> rewards;

    private int rewardsSize;

    public FishingRarity(String prefix, int weight, List<String> creatures, List<Reward> rewards) {
        this.prefix = prefix;
        this.weight = weight;
        this.creatures = creatures;
        this.rewards = rewards;
        this.rewardsSize = rewards.size();
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
