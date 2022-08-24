package io.storydoc.fabric.metamodel.domain;

import io.storydoc.fabric.metamodel.infra.MetaModel;

public interface MetaModelStorage {
    <MM extends MetaModel>  void saveMetaModel(MM metaModel, String systemComponentKey, MetaModelId metaModelId, MetaModelSerializer<MM> serializer);
    <MM extends MetaModel> MM loadMetaModel(String systemCompenentKey, MetaModelDeserializer<MM> deserializer);

}
