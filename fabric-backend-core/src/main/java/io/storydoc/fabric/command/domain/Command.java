package io.storydoc.fabric.command.domain;


public class Command {

    private CommandType commandType;

    public Command(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return commandType;
    }
}
