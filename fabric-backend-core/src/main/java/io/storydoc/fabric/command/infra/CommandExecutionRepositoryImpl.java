package io.storydoc.fabric.command.infra;

import io.storydoc.fabric.command.domain.Command;
import io.storydoc.fabric.command.domain.CommandExecutionRepository;
import io.storydoc.fabric.command.domain.ExecutionId;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandExecutionRepositoryImpl implements CommandExecutionRepository {

    private final Map<ExecutionId, Command<?>> contexts = new HashMap<>();

    @Override
    public void save(ExecutionId executionId, Command<?> command) {
        contexts.put(executionId, command);
    }

    @Override
    public Command<?> get(ExecutionId executionId) {
        return contexts.get(executionId);
    }

}
