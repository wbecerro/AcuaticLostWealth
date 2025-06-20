package wbe.acuaticLostWealth.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.acuaticLostWealth.rarities.FishingRarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabListener implements TabCompleter {

    private final List<String> subCommands = Arrays.asList("help", "rod", "double", "itemChance", "creatureChance",
            "boostRarity", "reload");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if(!command.getName().equalsIgnoreCase("AcuaticLostWealth")) {
            return completions;
        }

        // Mostrar subcomandos
        if(args.length == 1) {
            StringUtil.copyPartialMatches(args[0], subCommands, completions);
        }

        // Argumento 1
        if(args.length == 2) {
            switch(args[0].toLowerCase()) {
                case "rod":
                    completions.add("<Probabilidad objeto>");
                    break;
                case "double":
                case "itemchance":
                case "creaturechance":
                    completions.add("<Probabilidad>");
                    break;
                case "boostrarity":
                    for(FishingRarity rarity : AcuaticLostWealth.config.rarities) {
                        if(args[1].isEmpty()) {
                            completions.add(rarity.getInternalName());
                        } else if(rarity.getInternalName().startsWith(args[1])) {
                            completions.add(rarity.getInternalName());
                        }
                    }
                    break;
            }
        }

        // Argumento 2
        if(args.length == 3) {
            switch(args[0].toLowerCase()) {
                case "rod":
                    completions.add("<Probabilidad criatura>");
                    break;
                case "boostrarity":
                    completions.add("<Procentaje en base 0>");
                    break;
            }
        }

        // Argumento 3
        if(args.length == 4) {
            switch(args[0].toLowerCase()) {
                case "rod":
                    completions.add("<Probabilidad objeto alternativa>");
                    break;
            }
        }

        // Argumento 4
        if(args.length == 5) {
            switch(args[0].toLowerCase()) {
                case "rod":
                    completions.add("<Probabilidad criatura alternativa>");
                    break;
            }
        }

        // Argumento 5
        if(args.length == 6) {
            switch(args[0].toLowerCase()) {
                case "rod":
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        if(args[5].isEmpty()) {
                            completions.add(player.getName());
                        } else if(player.getName().startsWith(args[5])) {
                            completions.add(player.getName());
                        }
                    }
                    break;
            }
        }

        return completions;
    }
}
