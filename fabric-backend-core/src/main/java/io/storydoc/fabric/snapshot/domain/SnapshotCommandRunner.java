package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.command.app.CommandService;
import io.storydoc.fabric.command.domain.Command;
import io.storydoc.fabric.command.domain.ExecutionId;
import io.storydoc.fabric.snapshot.domain.upload.UploadCommandTypes;
import io.storydoc.fabric.snapshot.domain.upload.UploadSnapshotCommandParams;
import io.storydoc.fabric.snapshot.domain.upload.UploadSnapshotComponentCommandParams;
import io.storydoc.fabric.snapshot.infra.jsonmodel.Snapshot;
import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.Map;
import java.util.stream.Collectors;

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

        return commandService.run(
                new Command.Builder<UploadSnapshotCommandParams>()
                        .commandType(UploadCommandTypes.SNAPSHOT)
                        .name("upload snapshot " + snapshot.getName())
                        .params(new UploadSnapshotCommandParams(snapshot, environmentKey))
                        .children(snapshot.getSnapshotComponentSummaries().stream()
                              .map(summary -> new Command.Builder<UploadSnapshotComponentCommandParams>()
                                      .commandType(UploadCommandTypes.SNAPSHOT_COMPONENT)
                                      .name("upload component " + summary.getComponentKey())
                                      .params(new UploadSnapshotComponentCommandParams(100, summary))
                                      .build())
                              .collect(Collectors.toList())
                        )
                        .build());

    }


}
