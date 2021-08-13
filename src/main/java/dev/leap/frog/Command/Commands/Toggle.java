package dev.leap.frog.Command.Commands;

import dev.leap.frog.Command.Command;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Render.Chatutil;

public class Toggle extends Command {

    public Toggle() {
        super("Toggle", "Toggles commands for you", new String[0]);
    }

    @Override
    public void execute(String[] args) {
        if(args.length > 1 && args[1].equalsIgnoreCase("help")) {
            return;
        }

        if(args.length <= 1) {
            sendMessage("Not enough arguments");
            return;
        }

        if(LeapFrog.getModuleManager().getModuleName(args[1]) != null) {
            Module mod = LeapFrog.getModuleManager().getModuleName(args[1]);
            mod.toggle();
            sendMessage(mod.getName() + " " + "toggled");
        } else {
            sendMessage("Module not found");
        }
    }
}
