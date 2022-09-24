package io.storydoc.fabric.snapshot.domain.upload;

import io.storydoc.fabric.command.domain.Command;
import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponentSummary;

public class UploadSnapshotComponentCommand extends Command {

    private final int recordCount;

    private final SnapshotComponentSummary snapshotComponentSummary;

    public UploadSnapshotComponentCommand(int recordCount, SnapshotComponentSummary snapshotComponentSummary) {
        super(UploadCommandTypes.SNAPSHOT_COMPONENT);
        this.recordCount = recordCount;
        this.snapshotComponentSummary = snapshotComponentSummary;
    }

    public int getRecordCount() {
        return recordCount;
    }

    @Override
    public String getName() {
        return snapshotComponentSummary.getComponentKey();
    }

}
