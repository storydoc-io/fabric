package io.storydoc.fabric.command.domain;

abstract public class CommandHandler<PARAMS extends CommandParams> {

    abstract public CommandType getType();

    public ExecutionContext createContext(Command<PARAMS> command, ExecutionContext parentContext) {
        return new ExecutionContext(command.getName(), parentContext);
    }

    public abstract void run(Command<PARAMS> command, ExecutionContext context, CommandExecutionEngine commandExecutionEngine);

}
