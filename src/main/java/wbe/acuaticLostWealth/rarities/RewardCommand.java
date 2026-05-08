package wbe.acuaticLostWealth.rarities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;

public class RewardCommand extends Reward {

    private String command;

    public RewardCommand(String suffix, String command) {
        super(suffix);
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void giveReward(Player player, Event event) {
        if(!(event instanceof PlayerFishEvent fishEvent)) {
            return;
        }

        if(fishEvent.getCaught() instanceof Item) {
            fishEvent.getCaught().remove();
        }

        String command = getCommand().replace("%player%", player.getName());
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
