package io.storydoc.fabric.snapshot.domain.upload;

import io.storydoc.fabric.command.domain.CompositeCommand;
import io.storydoc.fabric.snapshot.infra.jsonmodel.Snapshot;

import java.util.List;

import static io.storydoc.fabric.snapshot.domain.upload.UploadCommandTypes.SNAPSHOT;

public class UploadSnapshotCommand extends CompositeCommand<UploadSnapshotComponentCommand> {

    private final Snapshot snapShot;

    private final String environmentKey;

    public UploadSnapshotCommand(List<UploadSnapshotComponentCommand> componentCommands, Snapshot snapShot, String environmentKey) {
        super(SNAPSHOT, componentCommands);
        this.snapShot = snapShot;
        this.environmentKey = environmentKey;
    }

    @Override
    public String getName() {
        return String.format("upload %s to %s", snapShot.getName(), environmentKey);
    }
}
