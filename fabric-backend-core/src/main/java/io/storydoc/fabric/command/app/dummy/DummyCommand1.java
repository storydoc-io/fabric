package io.storydoc.fabric.command.app.dummy;

import io.storydoc.fabric.command.domain.Command;

import java.util.List;

public class DummyCommand1 extends Command {

    private final List<DummyCommand2> subCommands;

    public DummyCommand1(List<DummyCommand2> subCommands) {
        super(DummyCommandTypes.DUMMY_COMMAND_TYPE_1);
        this.subCommands = subCommands;
    }

    public List<DummyCommand2> getSubCommands() {
        return subCommands;
    }
}
