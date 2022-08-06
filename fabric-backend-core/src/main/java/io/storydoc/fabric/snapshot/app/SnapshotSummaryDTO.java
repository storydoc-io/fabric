package io.storydoc.fabric.snapshot.app;

import io.storydoc.fabric.snapshot.domain.SnapshotId;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SnapshotSummaryDTO {
    private String name;
    private String environmentKey;
    private SnapshotId snapshotId;
}