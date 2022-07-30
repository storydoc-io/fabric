package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.systemdescription.app.SystemDescriptionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SnapshotContext {
    private SnapshotId snapshotId;

}
