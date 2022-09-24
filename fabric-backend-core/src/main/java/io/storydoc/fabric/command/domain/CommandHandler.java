package io.storydoc.fabric.command.domain;

abstract public class CommandHandler<CT extends Command> {

    abstract public CommandType getType();

    public abstract void run(CT command, ExecutionContext context, CommandExecutionEngine commandExecutionEngine);

    public ExecutionContext createContext(CT command, ExecutionContext parentContext) {
        return new ExecutionContext(command.getName(), parentContext);
    }

}
