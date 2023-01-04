package io.storydoc.fabric.infra.canon;

import java.util.ArrayList;
import java.util.List;

public class StepLog {

    private List<Step> steps = new ArrayList();

    public void addStep(Step step) {
        steps.add(step);
    }

}
