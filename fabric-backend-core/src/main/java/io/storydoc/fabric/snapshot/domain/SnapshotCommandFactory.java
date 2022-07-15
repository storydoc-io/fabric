package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.infra.IDGenerator;
import io.storydoc.fabric.snapshot.app.descriptor.SnapshotDescriptorDTO;
import org.springframework.stereotype.Component;

@Component
public class SnapshotCommandFactory {

    private final IDGenerator idGenerator;

    public SnapshotCommandFactory(IDGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }


    public SnapshotCommand createCommand(String environmentKey, SnapshotDescriptorDTO snapshotDescriptorDTO) {
        SnapshotId snapshotId = SnapshotId.fromString(idGenerator.generateID(SnapshotId.CATEGORY));
        return null;
    }

}
