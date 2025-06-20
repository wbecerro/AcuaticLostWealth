package wbe.acuaticLostWealth.rarities;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;

import java.util.List;
import java.util.Random;

public class FishingRarity {

    private String internalName;

    private String name;

    private String prefix;

    private double weight;

    private List<String> creatures;

    private String creatureSpawn;

    private PrimarySkillType skill;

    private int skillLevel;

    private List<Reward> rewards;

    private int rewardsSize;

    private String broadcast;

    private String title;

    private int fireworks;

    public FishingRarity(String internalName, String name, String prefix, double weight, List<String> creatures, String creatureSpawn,
                         PrimarySkillType skill, int skillLevel, List<Reward> rewards, String broadcast, String title, int fireworks) {
        this.internalName = internalName;
        this.name = name;
        this.prefix = prefix;
        this.weight = weight;
        this.creatures = creatures;
        this.creatureSpawn = creatureSpawn;
        this.skill = skill;
        this.skillLevel = skillLevel;
        this.rewards = rewards;
        this.rewardsSize = rewards.size();
        this.broadcast = broadcast;
        this.title = title;
        this.fireworks = fireworks;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
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

    public PrimarySkillType getSkill() {
        return skill;
    }

    public void setSkill(PrimarySkillType skill) {
        this.skill = skill;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFireworks() {
        return fireworks;
    }

    public void setFireworks(int fireworks) {
        this.fireworks = fireworks;
    }

    public Reward getRandomReward() {
        Random random = new Random();
        Reward reward = rewards.get(random.nextInt(rewardsSize));
        return reward;
    }
}
