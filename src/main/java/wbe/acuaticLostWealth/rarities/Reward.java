package wbe.acuaticLostWealth.rarities;

public class Reward {

    public String suffix;

    public String command;

    public Reward(String suffix, String command) {
        this.suffix = suffix;
        this.command = command;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
