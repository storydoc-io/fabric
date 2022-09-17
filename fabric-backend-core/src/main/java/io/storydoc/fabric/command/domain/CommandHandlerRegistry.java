package io.storydoc.fabric.command.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Component
@Slf4j
public class CommandHandlerRegistry {

    private Map<CommandType, CommandHandler> commandExecutorMap = new HashMap<>();

    public CommandHandlerRegistry(List<CommandHandler> commandHandlerList) {
        this.commandExecutorMap = toMap(commandHandlerList);
    }

    private Map<CommandType, CommandHandler> toMap(List<CommandHandler> commandHandlerList) {

        Map<CommandType, CommandHandler> map = new HashMap<>();
        for (CommandHandler commandHandler : commandHandlerList) {
            CommandType type = commandHandler.getType();
            if (map.keySet().contains(type)) {
                throw new IllegalArgumentException("type already registered: " + type);
            }
            map.put(type, commandHandler);
        }

        return map;
    }


    public CommandHandler getExecutor(CommandType commandType) {
        return commandExecutorMap.get(commandType);
    }
}
