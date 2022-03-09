package dev.leap.frog.Command;

import dev.leap.frog.Util.Listeners.Util;
import net.minecraft.util.text.TextComponentString;

public abstract class Command implements Util {

    protected static String prefix = "?";

    public Command() {

    }

    protected void sendMessage(String message) {
        if(mc.player == null || mc.world == null) return;

        mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(message));
    }

    public String getPrefix() {
        return prefix;
    }

}
