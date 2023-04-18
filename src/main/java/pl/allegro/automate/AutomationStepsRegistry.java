package pl.allegro.automate;

import io.vavr.collection.Map;

import javax.inject.Inject;

public class AutomationStepsRegistry {

    private final Map<Class<? extends AutomationStep>, AutomationStep> automationSteps;
    private final Map<AutomationFlow.Step.Code, AutomationStep> codeToStep;

    @Inject
    AutomationStepsRegistry(
        Map<Class<? extends AutomationStep>, AutomationStep> automationSteps,
        Map<AutomationFlow.Step.Code, AutomationStep> codeToStep
    ) {
        this.automationSteps = automationSteps;
        this.codeToStep = codeToStep;
    }

    public <T extends AutomationStep> T get(Class<T> automationStepClass) {
        return automationSteps.get(automationStepClass)
            .map(automationStepClass::cast)
            .getOrElseThrow(() -> new RuntimeException("Missing " + automationStepClass.getSimpleName()));
    }

    public AutomationStep get(AutomationFlow.Step.Code code) {
        return codeToStep.get(code)
            .getOrElseThrow(() -> new RuntimeException("Missing " + code));
    }
}
