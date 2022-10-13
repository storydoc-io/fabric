package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.command.domain.ProgressMonitor;
import io.storydoc.fabric.core.domain.CommandHandler;
import io.storydoc.fabric.snapshot.infra.jsonmodel.Snapshot;



public interface SnapshotHandler extends CommandHandler {

    String systemType();

    void upload(Snapshot snapshot, String environmentKey, String componentKey, ProgressMonitor command);

}
