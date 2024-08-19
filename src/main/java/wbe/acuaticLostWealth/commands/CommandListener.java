package wbe.acuaticLostWealth.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.acuaticLostWealth.items.FishingRod;
import wbe.acuaticLostWealth.util.Utilites;

public class CommandListener implements CommandExecutor {

    private AcuaticLostWealth plugin;

    private FileConfiguration config;

    private Utilites utilities;

    public CommandListener(AcuaticLostWealth plugin) {
        this.plugin = plugin;
        this.config = this.plugin.getConfig();
        this.utilities = new Utilites(plugin);
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("AcuaticLostWealth")) {
            Player player = null;
            if(sender instanceof Player) {
                player = (Player) sender;
            }
            if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
                if(!sender.hasPermission("acuaticlistwealth.command.help")) {
                    sender.sendMessage(AcuaticLostWealth.messages.noPermission);
                    return false;
                }

                for(String line : AcuaticLostWealth.messages.help) {
                    sender.sendMessage(line.replace("&", "§"));
                }
            } else if(args[0].equalsIgnoreCase("rod")) {
                if(!sender.hasPermission("acuaticlistwealth.command.rod")) {
                    player.sendMessage(AcuaticLostWealth.messages.noPermission);
                    return false;
                }

                if(args.length < 5) {
                    sender.sendMessage(AcuaticLostWealth.messages.notEnoughArgs);
                    sender.sendMessage(AcuaticLostWealth.messages.rodArguments);
                    return false;
                }

                int baseItemChance = Integer.valueOf(args[1]);
                int baseCreatureChance = Integer.valueOf(args[2]);
                int alteredItemChance = Integer.valueOf(args[3]);
                int alteredCreatureChance = Integer.valueOf(args[4]);
                FishingRod fishingRod = new FishingRod(plugin, baseItemChance, baseCreatureChance,
                        alteredItemChance, alteredCreatureChance);
                if(args.length > 5) {
                    Bukkit.getPlayer(args[5]).getInventory().addItem(new ItemStack[]{fishingRod});
                    Bukkit.getPlayer(args[5]).sendMessage(AcuaticLostWealth.messages.rodGiven);
                } else {
                    player.getInventory().addItem(new ItemStack[]{fishingRod});
                    player.sendMessage(AcuaticLostWealth.messages.rodGiven);
                }
            } else if(args[0].equalsIgnoreCase("reload")) {
                if(!sender.hasPermission("acuaticlistwealth.command.reload")) {
                    sender.sendMessage(AcuaticLostWealth.messages.noPermission);
                    return false;
                }

                plugin.reloadConfiguration();
                sender.sendMessage(AcuaticLostWealth.messages.reload);
            } else if(args[0].equalsIgnoreCase("double")) {
                if(!sender.hasPermission("acuaticlistwealth.command.double")) {
                    sender.sendMessage(AcuaticLostWealth.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(AcuaticLostWealth.messages.notEnoughArgs);
                    sender.sendMessage(AcuaticLostWealth.messages.doubleDropArguments);
                    return false;
                }

                utilities.addDoubleDropChance(player.getInventory().getItemInMainHand(), Integer.valueOf(args[1]));
                sender.sendMessage(AcuaticLostWealth.messages.doubleDropAdded);
            }
        }
        return true;
    }
}
