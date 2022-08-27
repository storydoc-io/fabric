package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.core.domain.CommandHandler;
import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;

import java.util.Map;

public interface SnapshotHandler<SC extends SnapshotComponent> extends CommandHandler {

    String systemType();

    SC takeComponentSnapshot(SnapshotId snapshotId, SystemComponentDTO systemComponent, Map<String, String> settings);

    SnapshotSerializer<SC> getSerializer();
}
