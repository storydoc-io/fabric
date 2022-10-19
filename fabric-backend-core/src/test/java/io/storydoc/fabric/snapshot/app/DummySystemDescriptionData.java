package io.storydoc.fabric.snapshot.app;

import io.storydoc.fabric.systemdescription.domain.SystemDescriptionStorage;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.Environment;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemComponent;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DummySystemDescriptionData {

    @Autowired
    SystemDescriptionStorage systemDescriptionStorage;

    public void createdummySystemDescription() {
        systemDescriptionStorage.saveSystemDescription(SystemDescription.builder()
                .environments(List.of(
                        Environment.builder()
                                .key("DEV")
                                .label("Develop environment")
                                .build(),
                        Environment.builder()
                                .key("INT")
                                .label("Integration environment")
                                .build())
                )
                .systemComponents(List.of(
                        SystemComponent.builder()
                                .key("PRODUCTS")
                                .systemType("MONGO")
                                .label("Mongo DB")
                                .build())
                )
                .settings(Map.of(
                        "DEV", Map.of(
                                "PRODUCTS", Map.of(
                                        "some_key", "some_value")),
                        "INT", Map.of(
                                "PRODUCTS", Map.of(
                                        "some_key", "some_value"))))
                .build());
    }


}
