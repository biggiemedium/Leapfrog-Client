package dev.leap.frog.Command.Commands;

import dev.leap.frog.Command.Command;
import dev.leap.frog.LeapFrog;

public class SaveCommand extends Command {

    public SaveCommand() {
        super(new String[] {"save", "saveconfig"}, "save");
    }

    @Override
    public void exec(String command, String[] args) {
        LeapFrog.getFileManager().save();
    }
}
