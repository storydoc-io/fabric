package io.storydoc.fabric.core.app;

import io.storydoc.fabric.config.FabricServerProperties;
import io.storydoc.fabric.workspace.app.WorkspaceQueryService;
import io.storydoc.fabric.workspace.domain.ResourceStorage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemService {

    private final FabricServerProperties fabricServerProperties;

    private final ResourceStorage resourceStorage;

    private final WorkspaceQueryService workspaceQueryService;

    public SystemService(FabricServerProperties fabricServerProperties, ResourceStorage resourceStorage, WorkspaceQueryService workspaceQueryService) {
        this.fabricServerProperties = fabricServerProperties;
        this.resourceStorage = resourceStorage;
        this.workspaceQueryService = workspaceQueryService;
    }

    public SystemCheckResultDTO checkSystem() {
        List<String> messages = new ArrayList<>();
        checkRootDir(messages);
        return SystemCheckResultDTO.builder()
                .messages(messages)
                .build();
    }

    private void checkRootDir(List<String> messages) {
        if (!resourceStorage.folderExists(workspaceQueryService.getRootFolder().getUrn())) {
            messages.add(String.format("workspaceFolder does not exist or is not accesible: %s", fabricServerProperties.getWorkspaceFolder()));
        }
    }

}
