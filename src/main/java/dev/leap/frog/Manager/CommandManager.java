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

   private ArrayList<Command> commands = new ArrayList<>();

   public CommandManager() {

   }

   private void Add(Command command) {
       this.commands.add(command);
   }

}
