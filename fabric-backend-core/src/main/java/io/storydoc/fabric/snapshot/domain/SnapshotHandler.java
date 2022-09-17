package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.core.domain.CommandHandler;

public interface SnapshotHandler extends CommandHandler {

    String systemType();

}
