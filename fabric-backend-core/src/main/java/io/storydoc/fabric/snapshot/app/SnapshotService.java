package io.storydoc.fabric.snapshot.app;

import io.storydoc.fabric.infra.IDGenerator;
import io.storydoc.fabric.snapshot.app.descriptor.SnapshotDescriptorDTO;
import io.storydoc.fabric.snapshot.domain.SnapshotCommandFactory;
import io.storydoc.fabric.snapshot.domain.SnapshotCommandRunner;
import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.snapshot.domain.SnapshotStorage;
import org.springframework.stereotype.Service;

@Service
public class SnapshotService {

    private final SnapshotStorage snapshotStorage;

    private final SnapshotCommandFactory snapshotCommandFactory;

    private final SnapshotCommandRunner snapshotCommandRunner;

    public SnapshotService(IDGenerator idGenerator, SnapshotStorage snapshotStorage, SnapshotCommandFactory snapshotCommandFactory, SnapshotCommandRunner snapshotCommandRunner) {
        this.snapshotStorage = snapshotStorage;
        this.snapshotCommandFactory = snapshotCommandFactory;
        this.snapshotCommandRunner = snapshotCommandRunner;
    }

    public SnapshotId createSnapshot(String environmentKey, SnapshotDescriptorDTO snapshotDescriptorDTO) {
        return null;
    }

    public SnapshotDTO getSnapshot(SnapshotId snapshotId) {
        return SnapshotDTO.builder()
                .snapshotId(snapshotId)
                .build();
    }



}
