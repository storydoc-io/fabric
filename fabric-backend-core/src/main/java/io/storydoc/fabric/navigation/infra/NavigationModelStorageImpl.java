package io.storydoc.fabric.navigation.infra;

import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.core.infra.WorkspaceStructure;
import io.storydoc.fabric.navigation.domain.NavigationModelDeserializer;
import io.storydoc.fabric.navigation.domain.NavigationModelStorage;
import io.storydoc.fabric.workspace.app.WorkspaceQueryService;
import io.storydoc.fabric.workspace.domain.ResourceUrn;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class NavigationModelStorageImpl extends StorageBase implements NavigationModelStorage {

    private final WorkspaceStructure workspaceStructure;

    private final WorkspaceQueryService workspaceQueryService;

    public NavigationModelStorageImpl(WorkspaceStructure workspaceStructure, WorkspaceQueryService workspaceQueryService) {
        this.workspaceStructure = workspaceStructure;
        this.workspaceQueryService = workspaceQueryService;
    }

    @SneakyThrows
    public <NM extends NavigationModel> NM loadNavigationModel(String systemCompenentKey, NavigationModelDeserializer<NM> deserializer) {
        ResourceUrn navigationModelUrn = workspaceStructure.getNavigationModelUrn(systemCompenentKey);
        return deserializer.read(workspaceQueryService.getInputStream(navigationModelUrn));
    }

}
