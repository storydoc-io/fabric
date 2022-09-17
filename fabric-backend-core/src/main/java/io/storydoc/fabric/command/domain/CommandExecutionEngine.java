package io.storydoc.fabric.command.domain;


import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class CommandExecutionEngine {

    private final ScheduledExecutorService executorService;

    private final CommandHandlerRegistry commandHandlerRegistry;

    private final ExecutionContextRepository executionContextRepository;


    public CommandExecutionEngine(CommandHandlerRegistry commandHandlerRegistry, ExecutionContextRepository executionContextRepository) {
        this.commandHandlerRegistry = commandHandlerRegistry;
        this.executionContextRepository = executionContextRepository;
        executorService = createExecutorService();
    }

    private ScheduledExecutorService createExecutorService() {
        CustomizableThreadFactory factory = new CustomizableThreadFactory();
        factory.setThreadNamePrefix("command");
        return Executors.newScheduledThreadPool(5, factory);

    }

    public <CT extends Command> void  runCommand(ExecutionId executionId, CT command) {
        CommandHandler<CT> commandHandler = commandHandlerRegistry.getExecutor(command.getCommandType());
        ExecutionContext context  = commandHandler.createContext(command, null);
        executionContextRepository.save(executionId, context);
        executorService.execute(()->{
            commandHandler.run(command, context, this);
        });
    }

    public void runSubCommand(Command command, ExecutionContext parentContext) {
        CommandHandler commandHandler = commandHandlerRegistry.getExecutor(command.getCommandType());
        ExecutionContext context  = commandHandler.createContext(command, parentContext);
        executorService.execute(()->{
            commandHandler.run(command, context, this);
        });
    }


    public ExecutionContext getContext(ExecutionId executionId) {
        return executionContextRepository.get(executionId);
    }

}
