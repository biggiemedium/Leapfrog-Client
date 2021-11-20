package dev.leap.frog.Command;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Util.Listeners.Util;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command extends UtilManager {

    protected String name;
    private String description;
    protected String[] syntax;

    public Command(String name, String... syntax) {
        this.name = name;
        this.syntax = syntax;

    }

    public Command(String name, String description, String... syntax) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
    }

    public Command() {

    }

    public abstract void execute(String[] args);

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

    public String[] getSyntax() {
        return syntax;
    }

    public void setSyntax(String[] syntax) {
        this.syntax = syntax;
    }
}
