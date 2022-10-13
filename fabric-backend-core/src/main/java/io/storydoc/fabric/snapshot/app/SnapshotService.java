package io.storydoc.fabric.snapshot.app;

import io.storydoc.fabric.command.domain.ExecutionId;
import io.storydoc.fabric.infra.IDGenerator;
import io.storydoc.fabric.snapshot.app.descriptor.SnapshotDescriptorDTO;
import io.storydoc.fabric.snapshot.app.result.SnapshotComponentDTO;
import io.storydoc.fabric.snapshot.app.result.SnapshotDTO;
import io.storydoc.fabric.snapshot.app.result.SnapshotSummaryDTO;
import io.storydoc.fabric.snapshot.domain.SnapshotCommandRunner;
import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.snapshot.domain.SnapshotStorage;
import io.storydoc.fabric.snapshot.infra.jsonmodel.Snapshot;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SnapshotService {

    private final SnapshotStorage snapshotStorage;

    private final SnapshotCommandRunner snapshotCommandRunner;

    private final IDGenerator idGenerator;

    public SnapshotService(IDGenerator idGenerator, SnapshotStorage snapshotStorage, SnapshotCommandRunner snapshotCommandRunner) {
        this.snapshotStorage = snapshotStorage;
        this.snapshotCommandRunner = snapshotCommandRunner;
        this.idGenerator = idGenerator;
    }

    public SnapshotId createSnapshot(SnapshotDescriptorDTO snapshotDescriptor) {
        SnapshotId snapshotId = SnapshotId.fromString(idGenerator.generateID(SnapshotId.CATEGORY));
        snapshotCommandRunner.runSnapshotCommand(snapshotId, snapshotDescriptor);
        return snapshotId;
    }

    public SnapshotDTO getSnapshot(SnapshotId snapshotId) {
        Snapshot snapshot = snapshotStorage.loadSnapshot(snapshotId);

        return SnapshotDTO.builder()
                .snapshotId(snapshotId)
                .name(snapshot.getName())
                .environmentKey(snapshot.getEnvKey())
                .componentSnapshots(snapshot.getSnapshotComponentSummaries().stream()
                        .map(snapshotComponentSummary -> SnapshotComponentDTO.builder()
                                .componentKey(snapshotComponentSummary.getComponentKey())
                                .systemType(snapshotComponentSummary.getSystemType())
                                .build())
                        .collect(Collectors.toList())
                )
                .build();
    }


    public List<SnapshotSummaryDTO> list() {
        return snapshotStorage.getSummaries().getSummaries().stream()
            .map(summary -> SnapshotSummaryDTO.builder()
                .snapshotId(summary.getSnapshotId())
                .name(summary.getName())
                .environmentKey(summary.getEnvKey())
                .build())
            .collect(Collectors.toList());
    }

    public void deleteSnapshot(SnapshotId snapshotId) {
        snapshotStorage.deleteSnapshot(snapshotId);
    }

    public ExecutionId upload(SnapshotId snapshotId, String environmentKey) {
        return snapshotCommandRunner.runUploadSnapshotCommand(environmentKey, snapshotId);
    }
}
