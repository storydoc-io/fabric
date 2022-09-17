package io.storydoc.fabric.snapshot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SnapshotContext {
    private SnapshotId snapshotId;

}
