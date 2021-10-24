package dev.leap.frog.Command.Commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Command.Command;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;

public class Toggle extends Command {

    public Toggle() {
        super("Toggle", "Toggles Module for you", new String[0]);
    }

    @Override
    public void execute(String[] args) {

        if(args.length <= 1) {
            sendMessage("Module not specified!");
        }

        if(LeapFrog.getModuleManager().getModuleName(args[1]) != null) {
            Module m = LeapFrog.getModuleManager().getModuleName(args[1]);
            m.toggle();
        }
    }
}
