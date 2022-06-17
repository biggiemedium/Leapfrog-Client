package dev.leap.frog.Command.Commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Command.Command;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;

public class ToggleCommand extends Command {

    public ToggleCommand() {
        super(new String[]{"prefix", "setprefix"}, "prefix <character>");
    }

    @Override
    public void exec(String command, String[] args) {
        setPrefix(args[0]);
        sendMessage("New prefix set to \"" + getPrefix() + "\"");
    }
}
