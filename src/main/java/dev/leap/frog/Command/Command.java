package dev.leap.frog.Command;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public abstract class Command extends UtilManager {

    private static String prefix;
    private static boolean waterMark = true;

    public Command(String[] alias) {
        prefix = ".";
    }


    public static void sendMessage(String message) {
        if(mc.player != null && mc.world != null) {

            if(waterMark) {
                mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(message + getSuffix()));
            } else {
                mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(message));
            }

        }
    }

    public static String getCommandPrefix() {
        return prefix;
    }
}
