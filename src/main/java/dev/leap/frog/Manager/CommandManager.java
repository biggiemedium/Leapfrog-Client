package dev.leap.frog.Manager;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Command.Command;
import dev.leap.frog.Command.Commands.FriendCommand;
import dev.leap.frog.Command.Commands.SaveCommand;
import dev.leap.frog.Command.Commands.ToggleCommand;
import dev.leap.frog.Util.Listeners.Util;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.Listenable;

import java.util.ArrayList;

public class CommandManager implements Listenable, Util {

   private ArrayList<Command> commands = new ArrayList<>();
   private boolean b;

   public CommandManager() {
      if(this.commands == null) {
         commands = new ArrayList<>();
      }

      Add(new ToggleCommand());
      Add(new SaveCommand());
      Add(new FriendCommand());
   }

   private void Add(Command command) {
       this.commands.add(command);
   }

   public void callCommand(String input) {
      String[] split = input.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
      String command = split[0];
      String args = input.substring(command.length()).trim();
      b = false;
      commands.forEach(c -> {
         for (String s : c.getArgs()) {
            if (s.equalsIgnoreCase(command)) {
               b = true;
               try {
                  c.exec(args, args.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
               }
               catch (Exception e) {
                  Chatutil.sendClientSideMessgage(ChatFormatting.RED + c.getSyntax());
               }
            }
         }
      });
      if (!b) {
         Chatutil.sendClientSideMessgage(ChatFormatting.RED + "Unknown command!");
      }
   }
}
