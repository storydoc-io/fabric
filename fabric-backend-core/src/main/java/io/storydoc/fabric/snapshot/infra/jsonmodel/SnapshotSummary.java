package io.storydoc.fabric.snapshot.infra.jsonmodel;

import io.storydoc.fabric.snapshot.domain.SnapshotId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SnapshotSummary {
    private SnapshotId snapshotId;
    private String name;
    private String envKey;
}
