package io.storydoc.fabric.command.app.dummy;

import io.storydoc.fabric.command.domain.Command;

public class DummyCommand2 extends Command {

    private final int recordCount;

    public DummyCommand2(int recordCount) {
        super(DummyCommandTypes.DUMMY_COMMAND_TYPE_2);
        this.recordCount = recordCount;
    }

    public int getRecordCount() {
        return recordCount;
    }

    @Override
    public String getName() {
        return "dummy subcommand";
    }
}
