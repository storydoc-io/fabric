package io.storydoc.fabric.command.domain;

import java.util.Objects;

public class ExecutionId {

    public static final String CATEGORY = "command";

    private String id;

    private ExecutionId() {}

    public ExecutionId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExecutionId itemId = (ExecutionId) o;
        return Objects.equals(id, itemId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static ExecutionId fromString(String id) {
        return new ExecutionId(id);
    }
}
