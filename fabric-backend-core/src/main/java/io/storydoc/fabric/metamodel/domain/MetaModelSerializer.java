package io.storydoc.fabric.metamodel.domain;

import io.storydoc.fabric.metamodel.infra.MetaModel;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface MetaModelSerializer<MM extends MetaModel> {
    void write(MM metaModel, OutputStream outputStream) throws IOException;
}

