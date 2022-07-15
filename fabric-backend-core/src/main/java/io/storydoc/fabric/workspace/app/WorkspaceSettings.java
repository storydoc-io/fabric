package io.storydoc.fabric.workspace.app;

import io.storydoc.fabric.config.FabricServerProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class WorkspaceSettings {

    private final  FabricServerProperties serverProperties;

    public WorkspaceSettings(FabricServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public Path getWorkspaceFolder() {
        return Path.of(serverProperties.getWorkspaceFolder());
    }
}
