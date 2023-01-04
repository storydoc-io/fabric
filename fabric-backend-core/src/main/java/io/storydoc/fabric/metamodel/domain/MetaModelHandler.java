package io.storydoc.fabric.metamodel.domain;

import io.storydoc.fabric.core.domain.CommandHandler;
import io.storydoc.fabric.metamodel.infra.MetaModel;
import io.storydoc.fabric.systemdescription.app.entity.EntityDTO;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;

import java.util.Map;

public interface MetaModelHandler<MM extends MetaModel> extends CommandHandler {
    MM createMetaModel(MetaModelId metaModelId, SystemComponentCoordinate coordinate, Map<String, String> settings);
    MetaModelSerializer<MM> getMetaModelSerializer();
    EntityDTO getAsEntity(SystemComponentCoordinate coordinate);
}
