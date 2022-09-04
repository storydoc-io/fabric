package io.storydoc.fabric.snapshot.infra;

import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.snapshot.domain.SnapshotDeserializer;
import io.storydoc.fabric.snapshot.domain.SnapshotSerializer;
import io.storydoc.fabric.snapshot.domain.SnapshotStorage;
import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.core.infra.WorkspaceStructure;
import io.storydoc.fabric.snapshot.infra.jsonmodel.*;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.workspace.app.WorkspaceService;
import io.storydoc.fabric.workspace.domain.WorkspaceException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;

@Component
public class SnapshotStorageImpl extends StorageBase implements SnapshotStorage {

    private final WorkspaceStructure workspaceStructure;

    private final WorkspaceService workspaceService;

    public SnapshotStorageImpl(WorkspaceStructure workspaceStructure, WorkspaceService workspaceService) {
        this.workspaceStructure = workspaceStructure;
        this.workspaceService = workspaceService;
    }

    @Override
    public void createSnapshot(SnapshotId snapshotId, String name, String envKey) {
        SnapshotSummary snapshotSummary = SnapshotSummary.builder()
                .envKey(envKey)
                .name(name)
                .snapshotId(snapshotId)
                .build();

        SnapshotSummaries summaries = loadSummaries();
        summaries.getSummaries().add(snapshotSummary);
        saveSummaries(summaries);

        workspaceService.createFolder(workspaceStructure.getSnapshotFolder(snapshotId));

        Snapshot snapshot = Snapshot.builder()
                .envKey(envKey)
                .name(name)
                .snapshotId(snapshotId)
                .snapshotComponentSummaries(new ArrayList<>())
                .build();
        saveSnapshot(snapshot);


    }

    @Override
    public SnapshotSummaries getSummaries() {
        return loadSummaries();
    }

    @SneakyThrows
    private SnapshotSummaries loadSummaries() {
        try {
            return workspaceService.loadResource(workspaceStructure.getSummariesUrn(), (inputStream) -> objectMapper.readValue(inputStream, SnapshotSummaries.class));
        } catch (WorkspaceException e) {
            if (e.getCause() instanceof FileNotFoundException) {
                return SnapshotSummaries.builder()
                        .summaries(new ArrayList<>())
                        .build();
            } else {
                throw e;
            }
        }
    }

    @SneakyThrows
    private void saveSummaries(SnapshotSummaries summaries) {
        workspaceService.saveResource(workspaceStructure.getSummariesUrn(), (outputStream) -> objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, summaries));
    }

    @SneakyThrows
    private void saveSnapshot(Snapshot snapshot) {
        workspaceService.saveResource(workspaceStructure.getSnapshotUrn(snapshot.getSnapshotId()), (outputStream) -> objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, snapshot));
    }

    @SneakyThrows
    public Snapshot loadSnapshot(SnapshotId snapshotId) {
        return workspaceService.loadResource(workspaceStructure.getSnapshotUrn(snapshotId), (inputStream ->objectMapper.readValue(inputStream, Snapshot.class)));
    }

    @Override
    @SneakyThrows
    public <SC extends SnapshotComponent> SC loadSnapshotComponent(SnapshotId snapshotId, String componentKey, SnapshotDeserializer<SC> deserializer, String systemType) {
        return  workspaceService.loadResource(workspaceStructure.getComponentSnapshotUrn(snapshotId, componentKey, systemType), (inputStream) -> deserializer.read(inputStream));
    }

    @Override
    @SneakyThrows
    public void saveSnapshotComponent(SnapshotComponent snapshotComponent, SystemComponentDTO systemComponent, SnapshotSerializer serializer, SnapshotId snapshotId) {

        Snapshot snapshot = loadSnapshot(snapshotId);
        snapshot.getSnapshotComponentSummaries().add(SnapshotComponentSummary.builder()
                .componentKey(systemComponent.getKey())
                .systemType(systemComponent.getSystemType())
                .build());
        saveSnapshot(snapshot);

        workspaceService.saveResource(workspaceStructure.getComponentSnapshotUrn(snapshotId, systemComponent), (outputStream) -> serializer.write(snapshotComponent, outputStream));
    }

    @Override
    @SneakyThrows
    public OutputStream streamSnapshotComponent(SystemComponentDTO systemComponent, SnapshotId snapshotId) {

        Snapshot snapshot = loadSnapshot(snapshotId);
        snapshot.getSnapshotComponentSummaries().add(SnapshotComponentSummary.builder()
                .componentKey(systemComponent.getKey())
                .systemType(systemComponent.getSystemType())
                .build());
        saveSnapshot(snapshot);

        return workspaceService.getOutputStream(workspaceStructure.getComponentSnapshotUrn(snapshotId, systemComponent));
    }


}
