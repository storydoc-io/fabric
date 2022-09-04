package io.storydoc.fabric.systemdescription.domain;

import io.storydoc.fabric.core.domain.CommandHandler;
import io.storydoc.fabric.metamodel.domain.MetaModelId;
import io.storydoc.fabric.metamodel.domain.MetaModelSerializer;
import io.storydoc.fabric.metamodel.infra.MetaModel;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SystemTypeDescriptorDTO;

public interface SystemStructureHandler extends CommandHandler {
    SystemTypeDescriptorDTO getSystemTypeDescriptor();
    StructureDTO getStructure(String key);
}
