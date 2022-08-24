package io.storydoc.fabric.connection.domain;

import io.storydoc.fabric.connection.app.ConnectionTestRequestDTO;
import io.storydoc.fabric.connection.app.ConnectionTestResponseDTO;
import io.storydoc.fabric.core.domain.CommandHandler;

public interface ConnectionHandler extends CommandHandler {
    ConnectionTestResponseDTO testConnection(ConnectionTestRequestDTO connectionTestRequestDTO);
}
