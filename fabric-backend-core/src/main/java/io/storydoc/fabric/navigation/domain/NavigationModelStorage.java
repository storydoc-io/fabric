package io.storydoc.fabric.navigation.domain;

import io.storydoc.fabric.navigation.infra.NavigationModel;

public interface NavigationModelStorage {
     <NM extends NavigationModel> NM loadNavigationModel(String systemCompenentKey, NavigationModelDeserializer<NM> deserializer);
}
