package io.storydoc.fabric.command.app.dummy;

import io.storydoc.fabric.command.domain.CommandParams;

public class DummyCommand1 extends CommandParams {

    private String percent;

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
