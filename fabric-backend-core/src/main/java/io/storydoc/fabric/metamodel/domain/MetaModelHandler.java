package io.storydoc.fabric.metamodel.domain;

import io.storydoc.fabric.core.domain.CommandHandler;
import io.storydoc.fabric.metamodel.infra.MetaModel;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;

public interface MetaModelHandler<MM extends MetaModel> extends CommandHandler {
    MM createMetaModel(String environmentKey, SystemComponentDTO systemComponent, MetaModelId metaModelId);
    MetaModelSerializer<MM> getMetaModelSerializer();
}
