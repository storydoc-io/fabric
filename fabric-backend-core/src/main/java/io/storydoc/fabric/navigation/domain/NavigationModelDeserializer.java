package io.storydoc.fabric.navigation.domain;


import io.storydoc.fabric.navigation.infra.NavigationModel;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface NavigationModelDeserializer <NM extends NavigationModel> {
    NM read(InputStream inputStream) throws IOException;
}
