package io.storydoc.fabric.testinfra.stepdefs;

import io.storydoc.fabric.infra.canon.Step;
import io.storydoc.fabric.infra.canon.StepDef;
import io.storydoc.fabric.systemdescription.domain.SystemDescriptionStorage;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.Environment;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemComponent;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemDescription;
import io.storydoc.fabric.testinfra.mock.TestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CreateSystemDescriptionStepDef implements StepDef {

    @Autowired
    SystemDescriptionStorage systemDescriptionStorage;

    public Step run() {
        createdummySystemDescription();
        return Step.builder()
                .description("create system description")
                .build();
    }

    private void createdummySystemDescription() {
        systemDescriptionStorage.saveSystemDescription(SystemDescription.builder()
                .environments(List.of(
                        Environment.builder()
                                .key(TestConstants.DEV_ENVIRONMENT)
                                .label("Develop environment")
                                .build(),
                        Environment.builder()
                                .key(TestConstants.INT_ENVIRONMENT)
                                .label("Integration environment")
                                .build())
                )
                .systemComponents(List.of(
                        SystemComponent.builder()
                                .key(TestConstants.PRODUCTS_SYSTEM_COMPONENT)
                                .systemType(TestConstants.MOCK_DB_TYPE)
                                .label("Mock DB")
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
