package io.storydoc.fabric.core.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HandlerRegistry<COMMAND_HANDLER_TYPE extends CommandHandler> {

    private final Map<String, COMMAND_HANDLER_TYPE> handlerMap;

    private final Class<COMMAND_HANDLER_TYPE> type;

    public HandlerRegistry(Class<COMMAND_HANDLER_TYPE> type, List<COMMAND_HANDLER_TYPE> handlerList) {
        handlerMap = toHandlerMap(handlerList);
        this.type = type;
    }

    private Map<String, COMMAND_HANDLER_TYPE> toHandlerMap(List<COMMAND_HANDLER_TYPE> handlerList) {
        Map<String, COMMAND_HANDLER_TYPE> handlerMap = new HashMap<>();
        handlerList.forEach(snapshotHandler -> {
            log.debug("registering metamodel handler for " + snapshotHandler.systemType());
            handlerMap.put(snapshotHandler.systemType(), snapshotHandler);
        });
        return handlerMap;
    }

    public COMMAND_HANDLER_TYPE getHandler(String systemType) {
        COMMAND_HANDLER_TYPE handler = handlerMap.get(systemType);
        if (handler == null) throw new FabricException(String.format("no suitable handler of type \"%s\" found for system type \"%s\"", type.getSimpleName(), systemType));
        return handler;
    }

    public Collection<COMMAND_HANDLER_TYPE> getHandlers() {
        return handlerMap.values();
    }
}



