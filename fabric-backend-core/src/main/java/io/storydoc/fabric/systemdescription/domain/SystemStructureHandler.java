package io.storydoc.fabric.systemdescription.domain;

import io.storydoc.fabric.core.domain.CommandHandler;
import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SystemTypeDescriptorDTO;

public interface SystemStructureHandler extends CommandHandler {
    SystemTypeDescriptorDTO getSystemTypeDescriptor();
    StructureDTO getStructure(String key);
}
