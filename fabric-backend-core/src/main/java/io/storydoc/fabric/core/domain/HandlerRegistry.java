package io.storydoc.fabric.core.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HandlerRegistry<COMMAND_HANDLER_TYPE extends CommandHandler> {

    private final Map<String, COMMAND_HANDLER_TYPE> handlerMap;

    public HandlerRegistry(List<COMMAND_HANDLER_TYPE> handlerList) {
        handlerMap = toHandlerMap(handlerList);
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
        if (handler == null) throw new FabricException("no suitable handler found for system type " + systemType);
        return handler;
    }

}



