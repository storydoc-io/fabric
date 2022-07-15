package io.storydoc.fabric.systemdescription.domain;

import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemDescription;

public interface SystemDescriptionStorage {

    public SystemDescription getOrCreateSystemDescription();

    public void saveSystemDescription(SystemDescription systemDescription);

}
