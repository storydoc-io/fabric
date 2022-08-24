package io.storydoc.fabric.connection.app;

import io.storydoc.fabric.connection.domain.ConnectionHandlerRegistry;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {

    private final ConnectionHandlerRegistry handlerRegistry;

    public ConnectionService(ConnectionHandlerRegistry handlerRegistry) {
        this.handlerRegistry = handlerRegistry;
    }

    public ConnectionTestResponseDTO testConnection(ConnectionTestRequestDTO connectionTestRequestDTO) {
        return handlerRegistry.getHandler(connectionTestRequestDTO.getSystemType()).testConnection(connectionTestRequestDTO);
    }

}
