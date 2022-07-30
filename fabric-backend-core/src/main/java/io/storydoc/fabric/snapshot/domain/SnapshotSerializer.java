package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface SnapshotSerializer<SC extends SnapshotComponent> {
    void write(SC snapshotComponent, OutputStream outputStream) throws IOException;
}
