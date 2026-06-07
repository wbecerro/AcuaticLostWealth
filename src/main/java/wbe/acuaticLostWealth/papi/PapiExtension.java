package wbe.acuaticLostWealth.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.acuaticLostWealth.rarities.FishingRarity;
import wbe.acuaticLostWealth.util.Utilities;

public class PapiExtension extends PlaceholderExpansion {

    private AcuaticLostWealth plugin;

    private Utilities utilities;

    public PapiExtension(AcuaticLostWealth plugin) {
        this.plugin = plugin;
        this.utilities = new Utilities(plugin);
    }

    @Override
    public String getIdentifier() {
        return "AcuaticLostWealth";
    }

    @Override
    public String getAuthor() {
        return "wbe";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("itemchance")) {
            return String.format("%.2f", utilities.getPlayerItemChance(player.getPlayer()));
        } else if(params.equalsIgnoreCase("creaturechance")) {
            return String.format("%.2f", utilities.getPlayerCreatureChance(player.getPlayer()));
        } else if(params.equalsIgnoreCase("doublechance")) {
            return String.format("%.2f", utilities.getPlayerDoubleChance(player.getPlayer()));
        } else if(params.contains("chance")) {
            String rarityName = params.replace("chance", "");
            return utilities.showRarityChance(rarityName, player.getPlayer());
        }

        return null;
    }
}
