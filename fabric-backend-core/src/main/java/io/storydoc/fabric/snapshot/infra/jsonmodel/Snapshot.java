package io.storydoc.fabric.snapshot.infra.jsonmodel;

import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.workspace.domain.WorkspaceResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Snapshot implements WorkspaceResource {
    private SnapshotId snapshotId;
    private String name;
    private String envKey;
    private List<SnapshotComponentSummary> snapshotComponentSummaries;
}
