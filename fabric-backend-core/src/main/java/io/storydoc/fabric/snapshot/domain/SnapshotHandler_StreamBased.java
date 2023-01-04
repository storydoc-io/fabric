package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;

import java.io.OutputStream;
import java.util.Map;

public interface SnapshotHandler_StreamBased <SC extends SnapshotComponent> extends SnapshotHandler{

    void streamSnapshot(SnapshotId snapshotId, SystemComponentCoordinate coordinate, Map<String, String> settings, OutputStream outputStream);

}
