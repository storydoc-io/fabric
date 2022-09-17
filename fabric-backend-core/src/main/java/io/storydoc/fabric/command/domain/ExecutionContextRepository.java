package io.storydoc.fabric.command.domain;

public interface ExecutionContextRepository {

    void save(ExecutionId executionId, ExecutionContext context);

    ExecutionContext get(ExecutionId executionId);
}
