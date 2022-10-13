package io.storydoc.fabric.command.app;

import io.storydoc.fabric.command.domain.Command;
import io.storydoc.fabric.command.domain.CommandExecutionEngine;
import io.storydoc.fabric.command.domain.CommandExecutionRepository;
import io.storydoc.fabric.command.domain.ExecutionId;
import io.storydoc.fabric.infra.UUIDGenerator;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CommandService {

    private final UUIDGenerator uuidGenerator;

    private final CommandExecutionEngine commandExecutionEngine;

    private final CommandExecutionRepository commandExecutionRepository;


    public CommandService(UUIDGenerator uuidGenerator, CommandExecutionEngine commandExecutionEngine, CommandExecutionRepository commandExecutionRepository) {
        this.uuidGenerator = uuidGenerator;
        this.commandExecutionEngine = commandExecutionEngine;
        this.commandExecutionRepository = commandExecutionRepository;
    }

    public ExecutionId run(Command command) {
        ExecutionId executionId = ExecutionId.fromString(uuidGenerator.generateID(ExecutionId.CATEGORY));
        commandExecutionRepository.save(executionId, command);
        commandExecutionEngine.runCommand(executionId, command);
        return executionId;
    }

    public ExecutionDTO getContextInfo(ExecutionId executionId) {
        return toDto(commandExecutionRepository.get(executionId));
    }

    private ExecutionDTO toDto(Command<?> command) {
        return ExecutionDTO.builder()
                .label(command.getName())
                .percentDone(command.getPercentDone())
                .status(command.getStatus())
                .children(command.getChildren()==null || command.getChildren().size()==0
                        ? null
                        : command.getChildren().stream()
                            .map(this::toDto)
                            .collect(Collectors.toList())
                )
                .build();
    }
}
