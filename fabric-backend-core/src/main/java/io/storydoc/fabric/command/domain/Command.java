package io.storydoc.fabric.command.domain;


import io.storydoc.fabric.command.app.ExecutionStatus;

abstract public class Command {

    private Command parent;

    private ExecutionStatus status;

    private final CommandType commandType;

    public Command(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    abstract public String getName();

    public Command getParent() {
        return parent;
    }

    public void setParent(Command parent) {
        this.parent = parent;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }
}
