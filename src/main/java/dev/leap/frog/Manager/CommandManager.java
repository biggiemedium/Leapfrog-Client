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
import java.util.Iterator;
import java.util.List;

public class CommandManager implements Listenable, Util {

    public static List<Command> commands = new ArrayList<>();
    public String PREFIX = ".";

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

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        if (event.getMessage().startsWith(this.PREFIX)) {
            String sub = event.getMessage().substring(1);
            String[] args = sub.split(" ");

            if (args.length > 0) {
                Iterator iterator = this.commands.iterator();

                while (iterator.hasNext()) {
                    Command command = (Command) iterator.next();
                    String[] astring = command.getSyntax();
                    int i = astring.length;

                    for (int j = 0; j < i; ++j) {
                        String s = astring[j];

                        if (s.equalsIgnoreCase(args[0])) {
                            command.execute(args);
                            break;
                        }
                    }
                }
            } else {
                Chatutil.sendClientSideMessgage("Invalid command");
            }

            event.setCanceled(true);
        }

    }

}
