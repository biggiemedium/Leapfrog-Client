package dev.leap.frog.Command.Commands;

import dev.leap.frog.Command.Command;

public class Toggle extends Command {

    public Toggle() {
        super("Toggle", "Toggles modules", ".toggle");
    }

    @Override
    public void execute(String[] args) {
        if(args.length > 0) {
            sendMessage("Warning! too many arguments!");
        }
    }
}
