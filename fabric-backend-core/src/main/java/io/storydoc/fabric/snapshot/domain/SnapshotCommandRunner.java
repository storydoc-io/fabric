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

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Component
@Slf4j
public class SnapshotCommandRunner {

    private final SystemDescriptionService systemDescriptionService;

    private final SnapshotStorage snapshotStorage;

    private final Map<String, SnapshotHandler> handlerMap;

    public SnapshotCommandRunner(SystemDescriptionService systemDescriptionService, SnapshotStorage snapshotStorage, List<SnapshotHandler> handlerList) {
        this.systemDescriptionService = systemDescriptionService;
        this.snapshotStorage = snapshotStorage;
        this.handlerMap = toHandlerMap(handlerList);
    }

    private Map<String, SnapshotHandler> toHandlerMap(List<SnapshotHandler> snapshotHandlers) {
        Map<String, SnapshotHandler> handlerMap = new HashMap<>();
        snapshotHandlers.forEach(snapshotHandler -> {
            log.debug("registering snapshot handler for " + snapshotHandler.systemType());
            handlerMap.put(snapshotHandler.systemType(), snapshotHandler);
        });
        return handlerMap;
    }

    public void runSnapshotCommand(String envKey, SnapshotId snapshotId, String name) {

        SystemDescriptionDTO systemDescriptionDTO  = systemDescriptionService.getSystemDescription();

        snapshotStorage.createSnapshot(snapshotId, name, envKey);

        for (SystemComponentDTO systemComponent : systemDescriptionDTO.getSystemComponents()) {
            String systemType = systemComponent.getSystemType();
            SnapshotHandler snapshotHandler = getSnapshotHandler(systemType);
            SnapshotComponent snapshotComponent = snapshotHandler.takeComponentSnapshot(envKey, systemComponent, snapshotId);
            snapshotStorage.saveSnapshotComponent(snapshotComponent, systemComponent, snapshotHandler.getSerializer(), snapshotId);
        }


    }

    private SnapshotHandler getSnapshotHandler(String systemType) {
        SnapshotHandler snapshotHandler = handlerMap.get(systemType);
        if (snapshotHandler == null) throw new FabricException("no suitable snapshot handler found for system type " + systemType);
        return snapshotHandler;
    }

}
