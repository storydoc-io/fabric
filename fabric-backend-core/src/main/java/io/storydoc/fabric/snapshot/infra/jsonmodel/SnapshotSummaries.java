package io.storydoc.fabric.snapshot.infra.jsonmodel;

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
public class SnapshotSummaries implements WorkspaceResource {

    private List<SnapshotSummary> summaries;

}
