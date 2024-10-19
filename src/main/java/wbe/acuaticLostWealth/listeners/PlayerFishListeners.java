package wbe.acuaticLostWealth.listeners;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.MobExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.acuaticLostWealth.events.PlayerReceiveRewardEvent;
import wbe.acuaticLostWealth.rarities.FishingRarity;
import wbe.acuaticLostWealth.rarities.Reward;
import wbe.acuaticLostWealth.util.Utilities;

import java.util.Random;

public class PlayerFishListeners implements Listener {

    private AcuaticLostWealth plugin;

    private Utilities utilities;

    public PlayerFishListeners(AcuaticLostWealth plugin) {
        this.plugin = plugin;
        this.utilities = new Utilities(plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void playSoundOnBite(PlayerFishEvent event) {
        if(!event.getState().equals(PlayerFishEvent.State.BITE)) {
            return;
        }

        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.valueOf(AcuaticLostWealth.config.fishCaughtSound), 1F, 1F);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void getRewardOnFish(PlayerFishEvent event) {

        if(!event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            return;
        }

        Random random = new Random();
        Player player = event.getPlayer();
        int creatureChance = utilities.getPlayerCreatureChance(player);
        int itemChance = utilities.getPlayerItemChance(player);
        int doubleChance = utilities.getPlayerDoubleChance(player);

        if(random.nextInt(100) + 1 <= creatureChance) {
            spawnCreature(event, player);
            if(random.nextInt(100) + 1 <= doubleChance) {
                player.sendMessage(AcuaticLostWealth.messages.doubleDrop);
                player.playSound(player.getLocation(), Sound.valueOf(AcuaticLostWealth.config.doubleDropSound), 1F, 1F);
                spawnCreature(event, player);
            }
            return;
        }


        if(random.nextInt(100 ) + 1 <= itemChance) {
            giveReward(event, player);
            if(random.nextInt(100) + 1 <= doubleChance) {
                player.sendMessage(AcuaticLostWealth.messages.doubleDrop);
                player.playSound(player.getLocation(), Sound.valueOf(AcuaticLostWealth.config.doubleDropSound), 1F, 1F);
                giveReward(event, player);
            }
            return;
        }
    }

    private void spawnCreature(PlayerFishEvent event, Player player) {
        FishingRarity rarity = utilities.calculateRarity();
        String mob = utilities.getRandomCreature(rarity);
        Location location = event.getHook().getLocation().add(0, 1, 0);
        MobExecutor mobExecutor = MythicBukkit.inst().getMobManager();
        MythicMob mythicMob = mobExecutor.getMythicMob(mob).get();
        mobExecutor.spawnMob(mob, location);
        String message = rarity.getCreatureSpawn().replace("%creature%", mythicMob.getDisplayName().get());
        player.sendMessage(message);
        if(event.getCaught() instanceof Item) {
            event.getCaught().remove();
        }
    }

    private void giveReward(PlayerFishEvent event, Player player) {
        FishingRarity rarity = utilities.calculateRarity();
        Reward reward = utilities.getRandomReward(rarity);
        String command = reward.getCommand().replace("%player%", player.getName());
        if(!rarity.getBroadcast().isEmpty()) {
            Bukkit.getServer().broadcastMessage(rarity.getBroadcast().replace("%player%", player.getName()));
        }

        if(!rarity.getTitle().isEmpty()) {
            player.sendTitle(rarity.getTitle(), "", 10, 70, 20);
        }

        if(rarity.getFireworks() != -1) {
            for(int i=0;i<rarity.getFireworks();i++) {
                Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK_ROCKET);
                firework.setFireworkMeta(utilities.getRandomFirework(firework));
            }
        }

        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        String message = rarity.getPrefix() + reward.getSuffix();
        player.sendMessage(message);
        plugin.getServer().getPluginManager().callEvent(new PlayerReceiveRewardEvent(player, rarity, reward));
        if(event.getCaught() instanceof Item) {
            event.getCaught().remove();
        }
    }
}
