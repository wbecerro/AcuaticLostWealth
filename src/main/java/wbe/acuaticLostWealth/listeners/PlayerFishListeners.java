package wbe.acuaticLostWealth.listeners;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.MobExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.acuaticLostWealth.rarities.FishingRarity;
import wbe.acuaticLostWealth.rarities.Reward;
import wbe.acuaticLostWealth.util.Utilites;

import java.util.Random;

public class PlayerFishListeners implements Listener {

    private AcuaticLostWealth plugin;

    private Utilites utilites;

    public PlayerFishListeners(AcuaticLostWealth plugin) {
        this.plugin = plugin;
        this.utilites = new Utilites(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void getRewardOnFish(PlayerFishEvent event) {

        if(!event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            return;
        }

        Random random = new Random();
        Player player = event.getPlayer();
        int creatureChance = utilites.getPlayerCreatureChance(player);
        int itemChance = utilites.getPlayerItemChance(player);
        int doubleChance = utilites.getPlayerDoubleChance(player);

        int iterations = 1;
        if(random.nextInt(100) + 1 <= doubleChance) {
            iterations++;
            player.sendMessage(AcuaticLostWealth.messages.doubleDrop);
            player.playSound(player.getLocation(), Sound.valueOf(AcuaticLostWealth.config.doubleDropSound), 1F, 1F);
        }

        for(int i=0;i<iterations;i++) {
            if(random.nextInt(100) + 1 <= creatureChance) {
                spawnCreature(event, player);
                continue;
            }


            if(random.nextInt(100 ) + 1 <= itemChance) {
                giveReward(event, player);
                continue;
            }
        }
    }

    private void spawnCreature(PlayerFishEvent event, Player player) {
        FishingRarity rarity = utilites.calculateRarity();
        String mob = utilites.getRandomCreature(rarity);
        Location location = event.getHook().getLocation().add(0, 1, 0);
        MobExecutor mobExecutor = MythicBukkit.inst().getMobManager();
        MythicMob mythicMob = mobExecutor.getMythicMob(mob).get();
        mobExecutor.spawnMob(mob, location);
        String message = rarity.getPrefix() + rarity.getCreatureSpawn().replace("%creature%", mythicMob.getDisplayName().get());
        player.sendMessage(message);
        if(event.getCaught() instanceof Item) {
            event.getCaught().remove();
        }
    }

    private void giveReward(PlayerFishEvent event, Player player) {
        FishingRarity rarity = utilites.calculateRarity();
        Reward reward = utilites.getRandomReward(rarity);
        String command = reward.getCommand().replace("%player%", player.getName());
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        String message = rarity.getPrefix() + reward.getSuffix();
        player.sendMessage(message);
        if(event.getCaught() instanceof Item) {
            event.getCaught().remove();
        }
    }
}
