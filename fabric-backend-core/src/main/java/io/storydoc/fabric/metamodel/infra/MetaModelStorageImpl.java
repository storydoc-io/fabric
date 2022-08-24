package io.storydoc.fabric.metamodel.infra;

import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.core.infra.WorkspaceStructure;
import io.storydoc.fabric.metamodel.domain.MetaModelDeserializer;
import io.storydoc.fabric.metamodel.domain.MetaModelId;
import io.storydoc.fabric.metamodel.domain.MetaModelSerializer;
import io.storydoc.fabric.metamodel.domain.MetaModelStorage;
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
    public <MM extends MetaModel>void saveMetaModel(MM metaModel, String systemComponentKey, MetaModelId metaModelId, MetaModelSerializer<MM> serializer) {
        workspaceService.saveResource(workspaceStructure.getMetaModelUrn(systemComponentKey), (outputStream) -> serializer.write(metaModel, outputStream));
    }

    @Override
    @SneakyThrows
    public <MM extends MetaModel> MM loadMetaModel(String systemCompenentKey, MetaModelDeserializer<MM> deserializer) {
        ResourceUrn metaModelModelUrn = workspaceStructure.getMetaModelUrn(systemCompenentKey);
        return deserializer.read(workspaceQueryService.getInputStream(metaModelModelUrn));
    }
}
