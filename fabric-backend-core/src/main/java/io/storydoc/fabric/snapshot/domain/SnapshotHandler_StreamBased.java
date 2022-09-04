package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;

import java.io.OutputStream;
import java.util.Map;

public interface SnapshotHandler_StreamBased <SC extends SnapshotComponent> extends SnapshotHandler{

    void streamSnapshot(SnapshotId snapshotId, SystemComponentDTO systemComponent, Map<String, String> settings, OutputStream outputStream);

}
