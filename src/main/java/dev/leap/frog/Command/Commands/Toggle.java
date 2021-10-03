package dev.leap.frog.Command.Commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Command.Command;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;

public class Toggle extends Command {

    public Toggle() {
        super("Toggle", "Toggles mod for you", new String[0]);
    }

    @Override
    public void execute(String[] args) {
        if(args.length <= 1) {
            sendMessage("Something went wrong");
            return;
        }

        Module m = LeapFrog.getModuleManager().getModuleName(args[1]);
        if(m == null) {
            sendMessage("Module does not exist!");
        } else {
            if(m.isToggled()) {
                m.toggle();
                sendMessage(ChatFormatting.RED + "Toggled " + ChatFormatting.RESET + m.getName());
            } else {
                m.setToggled(true);
                sendMessage(ChatFormatting.GREEN + "Toggled " + ChatFormatting.RESET + m.getName());
            }
        }

    }
}
