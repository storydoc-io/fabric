package io.storydoc.fabric.metamodel.domain;

import io.storydoc.fabric.metamodel.infra.MetaModel;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;

public interface MetaModelStorage {
    <MM extends MetaModel>  void saveMetaModel(SystemComponentCoordinate coordinate, MetaModelId metaModelId, MM metaModel, MetaModelSerializer<MM> serializer);
    <MM extends MetaModel> MM loadMetaModel(SystemComponentCoordinate coordinate, MetaModelDeserializer<MM> deserializer);

}
