package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.snapshot.infra.jsonmodel.Snapshot;
import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotSummaries;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;

public interface SnapshotStorage {
    void createSnapshot(SnapshotId snapshotId, String name, String envKey);

    Snapshot loadSnapshot(SnapshotId snapshotId);

    SnapshotSummaries getSummaries();

    void saveSnapshotComponent(SnapshotComponent snapshotComponent, SystemComponentDTO systemComponent, SnapshotSerializer snapshotSerializer, SnapshotId snapshotId);

    <SC extends SnapshotComponent> SC loadSnapshotComponent(SnapshotId snapshotId, String componentKey, SnapshotDeserializer<SC> deserializer, String systemType);
}
