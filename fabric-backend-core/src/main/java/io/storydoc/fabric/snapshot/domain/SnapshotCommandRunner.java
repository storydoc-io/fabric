package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.command.app.CommandService;
import io.storydoc.fabric.command.domain.ExecutionId;
import io.storydoc.fabric.snapshot.domain.upload.UploadSnapshotCommand;
import io.storydoc.fabric.snapshot.domain.upload.UploadSnapshotComponentCommand;
import io.storydoc.fabric.snapshot.infra.jsonmodel.Snapshot;
import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponentSummary;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SnapshotCommandRunner {

    private final SystemDescriptionService systemDescriptionService;

    private final SnapshotStorage snapshotStorage;

    private final SnapshotHandlerRegistry handlerRegistry;

    private final CommandService commandService;

    public SnapshotCommandRunner(SystemDescriptionService systemDescriptionService, SnapshotStorage snapshotStorage, SnapshotHandlerRegistry handlerRegistry, CommandService commandService) {
        this.systemDescriptionService = systemDescriptionService;
        this.snapshotStorage = snapshotStorage;
        this.handlerRegistry = handlerRegistry;
        this.commandService = commandService;
    }

    public void runSnapshotCommand(String envKey, SnapshotId snapshotId, String name) {

        SystemDescriptionDTO systemDescriptionDTO  = systemDescriptionService.getSystemDescription();

        snapshotStorage.createSnapshot(snapshotId, name, envKey);

        for (SystemComponentDTO systemComponent : systemDescriptionDTO.getSystemComponents()) {
            try {
                String systemType = systemComponent.getSystemType();
                SnapshotHandler snapshotHandler = handlerRegistry.getHandler(systemType);
                Map<String, String> settings = systemDescriptionDTO.getSettings()
                        .get(envKey)
                        .get(systemComponent.getKey());
                if (snapshotHandler instanceof SnapshotHandler_ModelBased) {
                    SnapshotHandler_ModelBased modelBasedHandler = (SnapshotHandler_ModelBased) snapshotHandler;
                    SnapshotComponent snapshotComponent = modelBasedHandler.takeComponentSnapshot(snapshotId, systemComponent, settings);
                    snapshotStorage.saveSnapshotComponent(snapshotComponent, systemComponent, modelBasedHandler.getSerializer(), snapshotId);
                } else {
                    SnapshotHandler_StreamBased streamHandler = (SnapshotHandler_StreamBased) snapshotHandler;
                    OutputStream outputStream = snapshotStorage.streamSnapshotComponent(systemComponent, snapshotId);
                    streamHandler.streamSnapshot(snapshotId, systemComponent, settings, outputStream);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }


    }

    public ExecutionId runUploadSnapshotCommand(String environmentKey, SnapshotId snapshotId) {
        Snapshot snapshot = snapshotStorage.loadSnapshot(snapshotId);

        List<UploadSnapshotComponentCommand> componentCommands = new ArrayList<>();
        for(SnapshotComponentSummary snapshotComponentSummary : snapshot.getSnapshotComponentSummaries()) {
            componentCommands.add(new UploadSnapshotComponentCommand(100, snapshotComponentSummary));
        }
        UploadSnapshotCommand command = new UploadSnapshotCommand(componentCommands, snapshot, environmentKey);

        return commandService.run(command);

    }


}
