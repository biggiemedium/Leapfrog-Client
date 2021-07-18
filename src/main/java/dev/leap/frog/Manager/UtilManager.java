package dev.leap.frog.Manager;

import dev.leap.frog.Command.Command;
import dev.leap.frog.Util.Render.Chatutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class UtilManager {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static Minecraft getMc() {
        return mc;
    }

    public static World getWorld() {
        return mc.world;
    }

    public static EntityPlayerSP getPlayer() {
        return mc.player;
    }

    public static boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public static void sendMessage(String message) {
        if(!Wrapper.nullCheck()) {
            mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(message));
        }
    }

    public static String getPrefix() {
        return Command.prefix;
    }

    public static String getSuffix() {
        return "\u23D0 \u029f\u1d07\u1d00\u1d18\ua730\u0280\u1d0f\u0262";
    }

}
