package io.storydoc.fabric.systemdescription.domain;

import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import org.springframework.stereotype.Component;

@Component
public class SystemStructureCommandRunner {

    private final SystemStructureHandlerRegistry handlerRegistry;

    public SystemStructureCommandRunner(SystemStructureHandlerRegistry handlerRegistry) {
        this.handlerRegistry = handlerRegistry;
    }

    public StructureDTO getStructure(String systemType, SystemComponentCoordinate coordinate) {
        return handlerRegistry.getHandler(systemType).getStructure(coordinate);
    }

}
