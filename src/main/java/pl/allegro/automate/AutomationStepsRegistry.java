package pl.allegro.automate;

import io.vavr.collection.Map;

import javax.inject.Inject;

public class AutomationStepsRegistry {

    private final Map<Class<? extends AutomationStep>, AutomationStep> automationSteps;

    @Inject
    AutomationStepsRegistry(Map<Class<? extends AutomationStep>, AutomationStep> automationSteps) {
        this.automationSteps = automationSteps;
    }

    public <T extends AutomationStep> T get(Class<T> automationStepClass) {
        return automationSteps.get(automationStepClass)
            .map(automationStepClass::cast)
            .getOrElseThrow(() -> new RuntimeException("Missing " + automationStepClass.getSimpleName()));
    }
}
