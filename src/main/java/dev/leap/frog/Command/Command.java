package dev.leap.frog.Command;

import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public abstract class Command {

    private String name;
    private String description;
    String syntax;

    public static String prefix = ".";
    protected Minecraft mc = Minecraft.getMinecraft();

    public Command(String name, String description, String syntax) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
    }

    public void sendMessage(String message) {
        if(!Wrapper.nullCheck()) {
            mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(message));
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String newPrifix) {
        prefix = newPrifix;
    }

    public abstract void execute(String[] args);

}
