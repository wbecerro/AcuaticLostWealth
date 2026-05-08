package wbe.acuaticLostWealth.rarities;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class Reward {

    private String suffix;

    public Reward(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public abstract void giveReward(Player player, Event event);
}
