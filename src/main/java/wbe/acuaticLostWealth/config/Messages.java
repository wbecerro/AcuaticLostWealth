package wbe.acuaticLostWealth.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Messages {

    private FileConfiguration config;

    public String noPermission;
    public String rodGiven;
    public String rodArguments;
    public String notEnoughArgs;
    public String modeChanged;
    public String doubleDropAdded;
    public String doubleDropArguments;
    public String doubleDrop;
    public String itemChanceAdded;
    public String itemChanceArguments;
    public String creatureChanceAdded;
    public String creatureChanceArguments;
    public String reload;
    public List<String> help = new ArrayList<>();

    public Messages(FileConfiguration config) {
        this.config = config;

        noPermission = config.getString("Messages.noPermission").replace("&", "§");
        rodGiven = config.getString("Messages.rodGiven").replace("&", "§");
        rodArguments = config.getString("Messages.rodArguments").replace("&", "§");
        notEnoughArgs = config.getString("Messages.notEnoughArgs").replace("&", "§");
        modeChanged = config.getString("Messages.modeChanged").replace("&", "§");
        doubleDropAdded = config.getString("Messages.doubleDropAdded").replace("&", "§");
        doubleDropArguments = config.getString("Messages.doubleDropArguments").replace("&", "§");
        doubleDrop = config.getString("Messages.doubleDrop").replace("&", "§");
        itemChanceAdded = config.getString("Messages.itemChanceAdded").replace("&", "§");
        itemChanceArguments = config.getString("Messages.itemChanceArguments").replace("&", "§");
        creatureChanceAdded = config.getString("Messages.creatureChanceAdded").replace("&", "§");
        creatureChanceArguments = config.getString("Messages.creatureChanceArguments").replace("&", "§");
        reload = config.getString("Messages.reload").replace("&", "§");
        help = config.getStringList("Messages.help");
    }
}
