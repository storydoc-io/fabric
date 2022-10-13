package io.storydoc.fabric.snapshot.domain.upload;

import io.storydoc.fabric.command.domain.CommandParams;
import io.storydoc.fabric.snapshot.infra.jsonmodel.Snapshot;

public class UploadSnapshotCommandParams extends CommandParams {

    private final Snapshot snapShot;

    private final String environmentKey;

    public UploadSnapshotCommandParams(Snapshot snapShot, String environmentKey) {
        this.snapShot = snapShot;
        this.environmentKey = environmentKey;
    }

    public Snapshot getSnapShot() {
        return snapShot;
    }

    public String getEnvironmentKey() {
        return environmentKey;
    }
}
