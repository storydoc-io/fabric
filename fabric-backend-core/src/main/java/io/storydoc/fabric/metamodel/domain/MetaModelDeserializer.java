package io.storydoc.fabric.metamodel.domain;


import io.storydoc.fabric.metamodel.infra.MetaModel;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface MetaModelDeserializer<NM extends MetaModel> {
    NM read(InputStream inputStream) throws IOException;
}
