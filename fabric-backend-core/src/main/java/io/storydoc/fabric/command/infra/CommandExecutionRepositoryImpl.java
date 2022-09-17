package io.storydoc.fabric.command.infra;

import io.storydoc.fabric.command.domain.ExecutionContext;
import io.storydoc.fabric.command.domain.ExecutionContextRepository;
import io.storydoc.fabric.command.domain.ExecutionId;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandExecutionRepositoryImpl implements ExecutionContextRepository {

    private Map<ExecutionId, ExecutionContext> contexts = new HashMap<>();

    public void save(ExecutionId executionId , ExecutionContext context) {
        contexts.put(executionId, context);
    }

    @Override
    public ExecutionContext get(ExecutionId executionId) {
        return contexts.get(executionId);
    }
}
