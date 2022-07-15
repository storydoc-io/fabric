package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.snapshot.domain.SnapshotId;

public interface SnapshotStorage {
    void createSnapshot(SnapshotId snapshotId);
}
