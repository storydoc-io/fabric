package io.storydoc.fabric.command.domain;

import java.util.ArrayList;
import java.util.List;

public class ExecutionContext {

    private final List<ExecutionContext> children = new ArrayList<>();

    private final ExecutionContext parentContext;

    private final String name;

    private int percentDone;

    public ExecutionContext(String name, ExecutionContext parentContext) {
        this.name = name;
        this.parentContext = parentContext;
        if (parentContext != null) {
            parentContext.getChildren().add(this);
        }
    }

    public int getPercentDone() {
        return percentDone;
    }

    public void setPercentDone(int percentDone) {
        this.percentDone = percentDone;
    }

    public List<ExecutionContext> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }
}
