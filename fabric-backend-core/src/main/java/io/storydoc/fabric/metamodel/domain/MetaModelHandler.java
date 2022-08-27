package io.storydoc.fabric.metamodel.domain;

import io.storydoc.fabric.core.domain.CommandHandler;
import io.storydoc.fabric.metamodel.infra.MetaModel;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;

import java.util.Map;

public interface MetaModelHandler<MM extends MetaModel> extends CommandHandler {
    MM createMetaModel(MetaModelId metaModelId, SystemComponentDTO systemComponent, Map<String, String> settings);
    MetaModelSerializer<MM> getMetaModelSerializer();
}
