package wbe.acuaticLostWealth.rarities;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class RewardItem extends Reward {

    private ItemStack item;

    public RewardItem(String suffix, ItemStack item) {
        super(suffix);
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void giveReward(Player player, Event event) {
        if(!(event instanceof PlayerFishEvent fishEvent)) {
            return;
        }

        if(fishEvent.getCaught() instanceof Item) {
            fishEvent.getCaught().remove();
        }

        Location playerLocation = player.getLocation();
        Item rewardItem = playerLocation.getWorld().dropItem(playerLocation, getItem());
        rewardItem.setOwner(player.getUniqueId());
        rewardItem.setPickupDelay(0);
    }
}
