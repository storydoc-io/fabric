package io.storydoc.fabric.core.infra;

import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.workspace.domain.FolderURN;
import io.storydoc.fabric.workspace.domain.ResourceUrn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkspaceStructure {

    public FolderURN getSnapshotFolder(SnapshotId snapshotId) {
        return new FolderURN(List.of(snapshotId.getId()));
    }

    public ResourceUrn getSystemDescriptionUrn() {
        return ResourceUrn.of("systemdescription.json");
    }
}
