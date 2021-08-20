package dev.leap.frog.Manager;

import dev.leap.frog.Command.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    public static List<Command> commands = new ArrayList<>();

    public CommandManager() {

    }

    public void Add(Command command) {
        commands.add(command);
    }

}
