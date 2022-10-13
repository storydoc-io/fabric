package io.storydoc.fabric.command.domain;


import io.storydoc.fabric.command.app.ExecutionStatus;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class CommandExecutionEngine {

    private final ScheduledExecutorService executorService;

    public CommandExecutionEngine() {
        executorService = createExecutorService();
    }

    private ScheduledExecutorService createExecutorService() {
        CustomizableThreadFactory factory = new CustomizableThreadFactory();
        factory.setThreadNamePrefix("command");
        return Executors.newScheduledThreadPool(5, factory);

    }

    public <CT extends CommandParams> void  runCommand(ExecutionId executionId, Command<CT> command) {
        if (command.getChildren() !=null && command.getChildren().size() > 0) {
            command.setStatus(ExecutionStatus.RUNNING);
            command.getChildren().forEach(childCommand -> runCommand(executionId, childCommand));
        } else {
            executorService.execute(()->{
                try {
                    command.setStatus(ExecutionStatus.RUNNING);
                    command.getRun().exceute(command);
                    command.setStatus(ExecutionStatus.DONE);
                    if (command.getParent()!=null) {
                        Command<?>  parent = command.getParent();
                        boolean childrenFinished = parent.getChildren().stream()
                                .noneMatch(child -> child.getStatus().equals(ExecutionStatus.RUNNING));
                        if (childrenFinished) {
                            parent.setStatus(ExecutionStatus.DONE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    command.setStatus(ExecutionStatus.ERROR);
                    command.setException(e);
                }
            });
        }
    }


}
