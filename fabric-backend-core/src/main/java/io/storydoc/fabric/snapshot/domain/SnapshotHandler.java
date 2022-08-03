package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;

public interface SnapshotHandler<SC extends SnapshotComponent> {

    String systemType();

    SC takeComponentSnapshot(String environmentKey, SystemComponentDTO systemComponent, SnapshotId snapshotId);

    SnapshotSerializer<SC> getSerializer();
}
