package io.storydoc.fabric.snapshot.infra;

import io.storydoc.fabric.snapshot.domain.SnapshotStorage;
import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.core.infra.WorkspaceStructure;
import io.storydoc.fabric.workspace.app.WorkspaceService;
import org.springframework.stereotype.Component;

@Component
public class SnapshotStorageImpl implements SnapshotStorage {

    private final WorkspaceStructure workspaceStructure;

    private final WorkspaceService workspaceService;

    public SnapshotStorageImpl(WorkspaceStructure workspaceStructure, WorkspaceService workspaceService) {
        this.workspaceStructure = workspaceStructure;
        this.workspaceService = workspaceService;
    }

    @Override
    public void createSnapshot(SnapshotId snapshotId) {
        workspaceService.createFolder(workspaceStructure.getSnapshotFolder(snapshotId));
    }


}
