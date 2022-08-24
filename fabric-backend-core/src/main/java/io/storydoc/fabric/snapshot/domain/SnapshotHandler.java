package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.core.domain.CommandHandler;
import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;

public interface SnapshotHandler<SC extends SnapshotComponent> extends CommandHandler {

    String systemType();

    SC takeComponentSnapshot(String environmentKey, SystemComponentDTO systemComponent, SnapshotId snapshotId);

    SnapshotSerializer<SC> getSerializer();
}
