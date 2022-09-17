package io.storydoc.fabric.systemdescription.infra;

import io.storydoc.fabric.core.domain.FabricException;
import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.core.infra.WorkspaceStructure;
import io.storydoc.fabric.systemdescription.domain.SystemDescriptionStorage;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemDescription;
import io.storydoc.fabric.workspace.app.WorkspaceService;
import io.storydoc.fabric.workspace.domain.WorkspaceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

@Component
@Slf4j
public class SystemDescriptionStorageImpl extends StorageBase implements SystemDescriptionStorage {

    private final WorkspaceStructure workspaceStructure;

    private final WorkspaceService workspaceService;

    public SystemDescriptionStorageImpl(WorkspaceStructure workspaceStructure, WorkspaceService workspaceService) {
        super();
        this.workspaceStructure = workspaceStructure;
        this.workspaceService = workspaceService;
    }

    public SystemDescription getOrCreateSystemDescription() {
        try {
            return loadSystemDescription();
        } catch (FabricException e) { log.info("error loading system description, creating new one", e);}
        return createtSystemDescription();
    }

    private SystemDescription createtSystemDescription() {
        SystemDescription systemDescription = new SystemDescription();
        systemDescription.setSystemComponents(new ArrayList<>());
        systemDescription.setEnvironments(new ArrayList<>());
        systemDescription.setSettings(new HashMap());
        saveSystemDescription(systemDescription);
        return systemDescription;
    }

    private SystemDescription loadSystemDescription() {
        try {
            return workspaceService.loadResource(workspaceStructure.getSystemDescriptionUrn(), this::deserializeSystemDescription);
        } catch (WorkspaceException workspaceException) {
            throw new FabricException("could not load system description", workspaceException);
        }
    }

    private SystemDescription deserializeSystemDescription(InputStream inputStream) throws IOException {
        return  objectMapper.readValue(inputStream, SystemDescription.class);
    }


    public void saveSystemDescription(SystemDescription systemDescription) {
        try {
            workspaceService.saveResource(workspaceStructure.getSystemDescriptionUrn(), outputStream -> serializeSystemDescription(systemDescription, outputStream));
        } catch (WorkspaceException workspaceException) {
            throw new FabricException("could not create system description", workspaceException);
        }
    }

    private void serializeSystemDescription(SystemDescription systemDescription, java.io.OutputStream outputStream) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, systemDescription);
    }

}
