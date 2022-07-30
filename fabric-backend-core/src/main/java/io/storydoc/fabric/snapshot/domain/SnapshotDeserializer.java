package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@FunctionalInterface
public interface SnapshotDeserializer <SC extends SnapshotComponent> {
    SC read(InputStream inputStream) throws IOException;
}
