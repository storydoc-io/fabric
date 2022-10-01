package io.storydoc.fabric.command.app.dummy;

import io.storydoc.fabric.command.domain.CommandParams;

public class DummyCommand2 extends CommandParams {

    public DummyCommand2(int recordCount) {
        this.recordCount = recordCount;
    }

    private int recordCount;

    public int getRecordCount() {
        return recordCount;
    }

}
