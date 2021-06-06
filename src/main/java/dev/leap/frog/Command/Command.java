package dev.leap.frog.Command;

import net.minecraft.client.Minecraft;

public class Command {

    private String name;
    private String description;

    protected Minecraft mc = Minecraft.getMinecraft();
    public static final String prefix = ".";

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public boolean getMessage(String[] args) {
        return false;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPrefix() {
        return prefix;
    }

}
