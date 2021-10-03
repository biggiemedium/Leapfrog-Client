package dev.leap.frog.Command;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

import java.util.List;

public abstract class Command extends UtilManager {

    private String name;
    private String description;
    private String[] commands;
    private String prefix;

    public Command(String name, String description, String[] syntax) {
        this.name = name;
        this.description = description;
        this.commands = syntax;
        this.prefix = ".";
    }

    public void commandCheck(String[] args) {
        if(args.length < 1 && args[1].equalsIgnoreCase("help")) {
            this.sendChatMessage("An error has occured");
            return;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public abstract void execute(String[] args);

    protected void sendChatMessage(String message) {
        if(!UtilManager.nullCheck()) {
            mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString( ChatFormatting.GREEN + "Leapfrog >" + ChatFormatting.RESET + " " + message));
        }
    }

    public String[] getCommands() {
        return commands;
    }

    public void setCommands(String[] commands) {
        this.commands = commands;
    }
}
