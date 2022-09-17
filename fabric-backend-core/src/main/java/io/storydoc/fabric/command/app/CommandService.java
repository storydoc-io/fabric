package io.storydoc.fabric.command.app;

import io.storydoc.fabric.command.domain.Command;
import io.storydoc.fabric.command.domain.CommandExecutionEngine;
import io.storydoc.fabric.command.domain.ExecutionContext;
import io.storydoc.fabric.command.domain.ExecutionId;
import io.storydoc.fabric.infra.UUIDGenerator;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CommandService {

    private final UUIDGenerator uuidGenerator;

    private final CommandExecutionEngine commandExecutionEngine;

    public CommandService(UUIDGenerator uuidGenerator, CommandExecutionEngine commandExecutionEngine) {
        this.uuidGenerator = uuidGenerator;
        this.commandExecutionEngine = commandExecutionEngine;
    }

    public ExecutionId run(Command command) {
        ExecutionId executionId = ExecutionId.fromString(uuidGenerator.generateID(ExecutionId.CATEGORY));
        commandExecutionEngine.runCommand(executionId, command);
        return executionId;
    }

    public ExecutionDTO getContextInfo(ExecutionId executionId) {
        return toDto(commandExecutionEngine.getContext(executionId));
    }

    private ExecutionDTO toDto(ExecutionContext context) {
        return ExecutionDTO.builder()
                .label(context.getName())
                .percentDone(context.getPercentDone())
                .children(context.getChildren()==null || context.getChildren().size()==0
                        ? null
                        : context.getChildren().stream()
                            .map(this::toDto)
                            .collect(Collectors.toList())
                )
                .build();
    }
}
