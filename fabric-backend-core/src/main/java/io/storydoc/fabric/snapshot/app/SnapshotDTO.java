package io.storydoc.fabric.snapshot.app;

import io.storydoc.fabric.snapshot.domain.SnapshotId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SnapshotDTO {

    private SnapshotId snapshotId;
    private String content;

}
