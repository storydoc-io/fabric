package io.storydoc.fabric.core.domain;

public class FabricException extends RuntimeException {

    public FabricException(String message, Throwable cause) {
        super(message, cause);
    }

    public FabricException(String message) {
        super(message);
    }
}
