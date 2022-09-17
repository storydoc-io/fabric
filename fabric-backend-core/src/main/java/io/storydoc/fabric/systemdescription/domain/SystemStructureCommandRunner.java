package io.storydoc.fabric.systemdescription.domain;

import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import org.springframework.stereotype.Component;

@Component
public class SystemStructureCommandRunner {

    private final SystemStructureHandlerRegistry handlerRegistry;

    public SystemStructureCommandRunner(SystemStructureHandlerRegistry handlerRegistry) {
        this.handlerRegistry = handlerRegistry;
    }

    public StructureDTO getStructure(SystemComponentDTO systemComponentDTO) {
        return handlerRegistry.getHandler(systemComponentDTO.getSystemType()).getStructure(systemComponentDTO.getKey());
    }

}
