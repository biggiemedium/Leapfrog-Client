package dev.leap.frog.Manager;

import dev.leap.frog.Command.Command;
import dev.leap.frog.Command.Commands.Toggle;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.client.event.ClientChatEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    public static List<Command> commands = new ArrayList<>();
    private String prefix = ".";

    public CommandManager() {

        if(commands == null) {
            commands = new ArrayList<>();
        }

        Add(new Toggle());
    }

    public void Add(Command command) {
        commands.add(command);
    }

    @EventHandler
    private Listener<ClientChatEvent> clientChatEventListener = new Listener<>(event -> {

        if(event.getMessage().startsWith(prefix)) {
            event.setCanceled(true);
            Chatutil.sendMessage("message");
        } else {
            return;
        }
    });

}
