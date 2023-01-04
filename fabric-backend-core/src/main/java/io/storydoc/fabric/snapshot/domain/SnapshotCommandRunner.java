package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.command.app.CommandService;
import io.storydoc.fabric.command.domain.Command;
import io.storydoc.fabric.command.domain.ExecutionId;
import io.storydoc.fabric.snapshot.app.descriptor.SnapshotDescriptorDTO;
import io.storydoc.fabric.snapshot.domain.upload.UploadSnapshotCommandParams;
import io.storydoc.fabric.snapshot.domain.upload.UploadSnapshotComponentCommandParams;
import io.storydoc.fabric.snapshot.infra.jsonmodel.Snapshot;
import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponentSummary;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;
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

    public void runSnapshotCommand(SnapshotId snapshotId, SnapshotDescriptorDTO snapshotDescriptor) {
        String environmentKey = snapshotDescriptor.getEnvironmentKey();
        String name = snapshotDescriptor.getName();

        SystemDescriptionDTO systemDescriptionDTO = systemDescriptionService.getSystemDescription();

        snapshotStorage.createSnapshot(snapshotId, name, environmentKey);

        for (SystemComponentDTO systemComponent : systemDescriptionDTO.getSystemComponents()) {
            SystemComponentCoordinate coordinate = SystemComponentCoordinate.builder()
                    .environmentKey(environmentKey)
                    .systemComponentKey(systemComponent.getKey())
                    .build();
            try {
                String systemType = systemComponent.getSystemType();
                SnapshotHandler snapshotHandler = handlerRegistry.getHandler(systemType);
                Map<String, String> settings = systemDescriptionDTO.getSettings()
                        .get(environmentKey)
                        .get(systemComponent.getKey());
                if (snapshotHandler instanceof SnapshotHandler_ModelBased) {
                    SnapshotHandler_ModelBased modelBasedHandler = (SnapshotHandler_ModelBased) snapshotHandler;
                    SnapshotComponent snapshotComponent = modelBasedHandler.takeComponentSnapshot(snapshotId, coordinate, settings);
                    snapshotStorage.saveSnapshotComponent(snapshotComponent, systemComponent, modelBasedHandler.getSerializer(), snapshotId);
                } else {
                    SnapshotHandler_StreamBased streamHandler = (SnapshotHandler_StreamBased) snapshotHandler;
                    OutputStream outputStream = snapshotStorage.streamSnapshotComponent(systemComponent, snapshotId);
                    streamHandler.streamSnapshot(snapshotId, coordinate, settings, outputStream);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }


    }

    public ExecutionId runUploadSnapshotCommand(String environmentKey, SnapshotId snapshotId) {
        Snapshot snapshot = snapshotStorage.loadSnapshot(snapshotId);
log.info("!!");
        if (snapshot == null || snapshot.getSnapshotComponentSummaries().size() == 0) return null;

log.info("!!!");
        return commandService.run(createUploadSnapshotCommand(environmentKey, snapshot));

    }

    private Command<UploadSnapshotCommandParams> createUploadSnapshotCommand(String environmentKey, Snapshot snapshot) {
        return new Command.Builder<UploadSnapshotCommandParams>()
                .name("upload snapshot " + snapshot.getName())
                .params(new UploadSnapshotCommandParams(snapshot, environmentKey))
                .children(snapshot.getSnapshotComponentSummaries().stream()
                        .map(this::createUploadSnapshotComponentCommand)
                        .collect(Collectors.toList())
                )
                .build();
    }

    private Command<UploadSnapshotComponentCommandParams> createUploadSnapshotComponentCommand(SnapshotComponentSummary summary) {
        return new Command.Builder<UploadSnapshotComponentCommandParams>()
                .name("upload component " + summary.getComponentKey())
                .params(new UploadSnapshotComponentCommandParams(100, summary))
                .run(this::runUploadSnapshotComponentCommand)
                .build();
    }

    private void runUploadSnapshotComponentCommand(Command<UploadSnapshotComponentCommandParams> command) {
        Command<UploadSnapshotCommandParams> parent =  command.getParent();

        Snapshot snapshot = parent.getParams().getSnapShot();
        SnapshotComponentSummary snapshotComponentSummary = command.getParams().getSnapshotComponentSummary();

        String systemType = snapshotComponentSummary.getSystemType();
        String environmentKey = parent.getParams().getEnvironmentKey();
        String componentKey = snapshotComponentSummary.getComponentKey();

        handlerRegistry
                .getHandler(systemType)
                .upload(snapshot, environmentKey, componentKey, command);


    }

}
