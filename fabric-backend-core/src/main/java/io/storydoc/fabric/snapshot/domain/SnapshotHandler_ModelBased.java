package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;

import java.util.Map;

public interface SnapshotHandler_ModelBased<SC extends SnapshotComponent> extends SnapshotHandler{

    SC takeComponentSnapshot(SnapshotId snapshotId, SystemComponentDTO systemComponent, Map<String, String> settings);

    SnapshotSerializer<SC> getSerializer();

}
