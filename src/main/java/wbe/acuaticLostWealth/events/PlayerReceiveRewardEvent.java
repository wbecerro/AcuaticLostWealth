package wbe.acuaticLostWealth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import wbe.acuaticLostWealth.rarities.FishingRarity;
import wbe.acuaticLostWealth.rarities.Reward;

public class PlayerReceiveRewardEvent extends Event implements Cancellable {

    private Player player;

    private FishingRarity rarity;

    private Reward reward;

    private boolean isCancelled;

    private static final HandlerList handlers = new HandlerList();

    public PlayerReceiveRewardEvent(Player player, FishingRarity rarity, Reward reward) {
        this.player = player;
        this.rarity = rarity;
        this.reward = reward;
    }

    public Player getPlayer() {
        return player;
    }

    public FishingRarity getRarity() {
        return rarity;
    }

    public Reward getReward() {
        return reward;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
