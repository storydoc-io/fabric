package io.storydoc.fabric.command.domain;

import java.util.List;

abstract public class CompositeCommand<CT extends Command> extends Command{

    private final List<CT> subCommands;

    public CompositeCommand(CommandType commandType, List<CT> subCommands) {
        super(commandType);
        this.subCommands = subCommands;
    }

    public List<CT> getSubCommands() {
        return subCommands;
    }
}
