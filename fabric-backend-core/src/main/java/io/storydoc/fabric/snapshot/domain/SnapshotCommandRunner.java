package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.core.domain.FabricException;
import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SnapshotCommandRunner {

    private final SystemDescriptionService systemDescriptionService;

    private final SnapshotStorage snapshotStorage;

    private final SnapshotHandlerRegistry handlerRegistry;

    public SnapshotCommandRunner(SystemDescriptionService systemDescriptionService, SnapshotStorage snapshotStorage, SnapshotHandlerRegistry handlerRegistry) {
        this.systemDescriptionService = systemDescriptionService;
        this.snapshotStorage = snapshotStorage;
        this.handlerRegistry = handlerRegistry;
    }

    public void runSnapshotCommand(String envKey, SnapshotId snapshotId, String name) {

        SystemDescriptionDTO systemDescriptionDTO  = systemDescriptionService.getSystemDescription();

        snapshotStorage.createSnapshot(snapshotId, name, envKey);

        for (SystemComponentDTO systemComponent : systemDescriptionDTO.getSystemComponents()) {
            String systemType = systemComponent.getSystemType();
            SnapshotHandler snapshotHandler = handlerRegistry.getHandler(systemType);
            SnapshotComponent snapshotComponent = snapshotHandler.takeComponentSnapshot(envKey, systemComponent, snapshotId);
            snapshotStorage.saveSnapshotComponent(snapshotComponent, systemComponent, snapshotHandler.getSerializer(), snapshotId);
        }


    }

}
