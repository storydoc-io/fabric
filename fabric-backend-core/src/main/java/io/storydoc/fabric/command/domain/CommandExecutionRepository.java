package io.storydoc.fabric.command.domain;

public interface CommandExecutionRepository {

    void save(ExecutionId executionId, Command<?> command);

    Command<?> get(ExecutionId executionId);
}
