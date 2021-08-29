package dev.leap.frog.Util.Render;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class Chatutil extends UtilManager {

    public static ChatFormatting red = ChatFormatting.RED;
    public static ChatFormatting blue = ChatFormatting.BLUE;
    public static ChatFormatting white = ChatFormatting.WHITE;

    public static String prefix = ChatFormatting.GREEN + "LeapFrog > " + ChatFormatting.WHITE;

    public static void sendClientSideMessgage(String message) {
        if (Wrapper.getMC().ingameGUI != null && Wrapper.getPlayer() != null)
            Wrapper.getMC().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(prefix  + message));
    }

    public static void setModuleMessage(Module module) {
        if(module.isToggled()) {
            removeableMessage(module.getName() + ChatFormatting.GREEN + " Enabled");
        } else {
            removeableMessage(module.getName() + ChatFormatting.RED + " Disabled");
        }
    }

    public static void removeableMessage(String message) {
        if(Wrapper.getPlayer() != null) {
            ITextComponent text = new TextComponentString(prefix + message).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Leap Frog"))));
            Wrapper.getMC().ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(text, 5936);
        }
    }
}
