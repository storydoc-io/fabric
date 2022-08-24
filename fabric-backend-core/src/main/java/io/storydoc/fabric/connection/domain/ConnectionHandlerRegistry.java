package io.storydoc.fabric.connection.domain;

import io.storydoc.fabric.core.domain.HandlerRegistry;
import io.storydoc.fabric.snapshot.domain.SnapshotHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Component
@Slf4j
public class ConnectionHandlerRegistry extends HandlerRegistry<ConnectionHandler> {

    public ConnectionHandlerRegistry(List<ConnectionHandler> handlerList) {
        super(handlerList);
    }

}
