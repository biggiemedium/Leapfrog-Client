package dev.leap.frog.Util.Render;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class Chatutil {

    public static ChatFormatting red = ChatFormatting.RED;
    public static ChatFormatting blue = ChatFormatting.BLUE;
    public static ChatFormatting white = ChatFormatting.WHITE;

    public static String prefix = ChatFormatting.GREEN + "LeapFrog > " + ChatFormatting.WHITE;

    public static void SendGlobalChatMessage(String message) {
        Minecraft.getMinecraft().player.sendChatMessage(message);
    }

    public static void ClientSideMessgage(String message) {
        if (Wrapper.GetMC().ingameGUI != null || Wrapper.GetPlayer() == null)
            Wrapper.GetMC().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(message));
    }

    public static void SendLeapFrogMessage(String message) {
        if (Wrapper.GetMC().ingameGUI != null || Wrapper.GetPlayer() == null)
            Wrapper.GetMC().ingameGUI.getChatGUI().printChatMessage(new TextComponentString( prefix  + message));
    }

}
