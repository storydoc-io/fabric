package io.storydoc.fabric.snapshot.domain.upload;

import io.storydoc.fabric.command.domain.CommandParams;
import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponentSummary;

public class UploadSnapshotComponentCommandParams extends CommandParams {

    private final int recordCount;

    private final SnapshotComponentSummary snapshotComponentSummary;

    public UploadSnapshotComponentCommandParams(int recordCount, SnapshotComponentSummary snapshotComponentSummary) {
        this.recordCount = recordCount;
        this.snapshotComponentSummary = snapshotComponentSummary;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public SnapshotComponentSummary getSnapshotComponentSummary() {
        return snapshotComponentSummary;
    }
}
