package dev.leap.frog.Manager;

import dev.leap.frog.Command.Command;

import java.util.ArrayList;

public class CommandManager {

    public static ArrayList<Command> commands = new ArrayList<>();

    public CommandManager() {

    }

    public void Add(Command command) {
        commands.add(command);
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

}
