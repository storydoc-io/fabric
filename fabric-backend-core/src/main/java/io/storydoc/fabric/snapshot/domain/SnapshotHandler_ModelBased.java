package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;

import java.util.Map;

public interface SnapshotHandler_ModelBased<SC extends SnapshotComponent> extends SnapshotHandler{

    SC takeComponentSnapshot(SnapshotId snapshotId, SystemComponentCoordinate coordinate, Map<String, String> settings);

    SnapshotSerializer<SC> getSerializer();

}
