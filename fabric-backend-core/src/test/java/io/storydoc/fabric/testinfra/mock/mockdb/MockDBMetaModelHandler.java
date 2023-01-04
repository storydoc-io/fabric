package io.storydoc.fabric.testinfra.mock.mockdb;

import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.metamodel.domain.*;
import io.storydoc.fabric.systemdescription.app.entity.EntityDTO;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;
import io.storydoc.fabric.testinfra.mock.TestConstants;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MockDBMetaModelHandler extends StorageBase implements MetaModelHandler<MockDBMetaModel> {

    private final MetaModelStorage metaModelStorage;

    public MockDBMetaModelHandler(MetaModelStorage metaModelStorage) {
        this.metaModelStorage = metaModelStorage;
    }

    @Override
    public String systemType() {
        return TestConstants.MOCK_DB_TYPE;
    }

    @Override
    public MockDBMetaModel createMetaModel(MetaModelId metaModelId, SystemComponentCoordinate coordinate, Map settings) {
        return new MockDBMetaModel();
    }

    @Override
    public MetaModelSerializer getMetaModelSerializer() {
        return ((metamodel, outputStream) -> objectMapper.writeValue(outputStream, metamodel));

    }

    public MetaModelDeserializer<MockDBMetaModel> getMetaModelDeSerializer() {
        return ((inputStream) -> objectMapper.readValue(inputStream, MockDBMetaModel.class));
    }


    public MockDBMetaModel getMetaModel(SystemComponentCoordinate coordinate) {
        try {
            return metaModelStorage.loadMetaModel(coordinate, getMetaModelDeSerializer());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public EntityDTO getAsEntity(SystemComponentCoordinate coordinate) {
        MockDBMetaModel metaModel = getMetaModel(coordinate);
        return metaModel == null ? null : EntityDTO.builder().build();
    }
}
