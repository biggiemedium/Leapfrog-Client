package dev.leap.frog.Manager;

import dev.leap.frog.Command.Command;
import dev.leap.frog.Command.Commands.Toggle;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Util.Listeners.Util;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listenable;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements Listenable, Util {

    public static List<Command> commands = new ArrayList<>();
    private String prefix = Command.Get.getPrefix();

    public CommandManager() {
        if(commands == null) {
            commands = new ArrayList<>();
        }

        Add(new Toggle());
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void Add(Command command) {
        commands.add(command);
    }

    //@SubscribeEvent
    //public void onChat(ClientChatEvent event) {
    //    if (event.getMessage().startsWith(Command.Get.getPrefix())) {
    //        event.setCanceled(true);
    //        try {
    //            mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
    //            if (event.getMessage().length() > 1) {
    //                Command.Get.execute(new String[] {event.getMessage().substring(Command.Get.getPrefix().length() - 1)});
    //            } else {
    //                Command.sendMessage("Please enter a command.");
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            Command.sendMessage("An error occurred while running this command.");
    //        }
    //        event.setMessage("");
    //    }
    //}

}
