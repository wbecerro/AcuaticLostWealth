package wbe.acuaticLostWealth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import wbe.acuaticLostWealth.rarities.FishingRarity;
import wbe.acuaticLostWealth.rarities.Reward;

public class PlayerReceiveRewardEvent extends PlayerEvent {

    private FishingRarity rarity;

    private Reward reward;

    public PlayerReceiveRewardEvent(Player player, FishingRarity rarity, Reward reward) {
        super(player);
        this.rarity = rarity;
        this.reward = reward;
    }

    public FishingRarity getRarity() {
        return rarity;
    }

    public Reward getReward() {
        return reward;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
