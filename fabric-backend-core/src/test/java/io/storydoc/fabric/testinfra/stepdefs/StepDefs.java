package io.storydoc.fabric.testinfra.stepdefs;

import io.storydoc.fabric.infra.canon.Step;
import org.springframework.stereotype.Component;

@Component
public class StepDefs {


    private final CreateSystemDescriptionStepDef systemDescriptionCreated;

    public StepDefs(CreateSystemDescriptionStepDef systemDescriptionCreated) {
        this.systemDescriptionCreated = systemDescriptionCreated;
    }

    public Step given_a_system_description() {
        Step step =  systemDescriptionCreated.run();
        return step;
    }


}
