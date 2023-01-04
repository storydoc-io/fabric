package io.storydoc.fabric.metamodel.infra;

import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.core.infra.WorkspaceStructure;
import io.storydoc.fabric.metamodel.domain.MetaModelDeserializer;
import io.storydoc.fabric.metamodel.domain.MetaModelId;
import io.storydoc.fabric.metamodel.domain.MetaModelSerializer;
import io.storydoc.fabric.metamodel.domain.MetaModelStorage;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;
import io.storydoc.fabric.workspace.app.WorkspaceQueryService;
import io.storydoc.fabric.workspace.app.WorkspaceService;
import io.storydoc.fabric.workspace.domain.ResourceUrn;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class MetaModelStorageImpl extends StorageBase implements MetaModelStorage {

    private final WorkspaceStructure workspaceStructure;

    private final WorkspaceQueryService workspaceQueryService;

    private final WorkspaceService workspaceService;

    public MetaModelStorageImpl(WorkspaceStructure workspaceStructure, WorkspaceQueryService workspaceQueryService, WorkspaceService workspaceService) {
        this.workspaceStructure = workspaceStructure;
        this.workspaceQueryService = workspaceQueryService;
        this.workspaceService = workspaceService;
    }

    @Override
    @SneakyThrows
    public <MM extends MetaModel>void saveMetaModel(SystemComponentCoordinate coordinate, MetaModelId metaModelId, MM metaModel, MetaModelSerializer<MM> serializer) {
        workspaceService.saveResource(workspaceStructure.getMetaModelUrn(coordinate), (outputStream) -> serializer.write(metaModel, outputStream));
    }

    @Override
    public <MM extends MetaModel> MM loadMetaModel(SystemComponentCoordinate coordinate, MetaModelDeserializer<MM> deserializer) {
        try {
            ResourceUrn metaModelModelUrn = workspaceStructure.getMetaModelUrn(coordinate);
            return deserializer.read(workspaceQueryService.getInputStream(metaModelModelUrn));
        } catch (Exception e) {
            return null;
        }
    }
}
