package pl.allegro.automate.adapter;

import dagger.Module;
import dagger.Provides;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import pl.allegro.automate.AutomationFlow;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.adapter.logging.metrics.LoggingAutomationStepFactory;
import pl.allegro.automate.gui.GuiAutomationSteps;

@Module
interface AutomateModule {

    @Provides
    static Map<Class<? extends AutomationStep>, AutomationStep> provideAllAutomationSteps(
        java.util.Map<Class<? extends AutomationStep>, AutomationStep> automationSteps,
        @GuiAutomationSteps
        java.util.Map<Class<? extends AutomationStep>, AutomationStep> guiAutomationSteps,
        LoggingAutomationStepFactory loggingAutomationStepFactory
    ) {
        var allAutomationSteps = new java.util.HashMap<>(automationSteps);
        allAutomationSteps.putAll(guiAutomationSteps);
        return HashMap.ofAll(allAutomationSteps)
            .map((automationStepKey, automationStep) ->
                decorateWithLogging(loggingAutomationStepFactory, automationStepKey, automationStep)
            );
    }

    @Provides
    static Map<AutomationFlow.Step.Code, AutomationStep> allAutomationSteps(
        java.util.Map<AutomationFlow.Step.Code, AutomationStep> automationSteps,
        @GuiAutomationSteps
        java.util.Map<AutomationFlow.Step.Code, AutomationStep> guiAutomationSteps,
        LoggingAutomationStepFactory loggingAutomationStepFactory
    ) {
        var allAutomationSteps = new java.util.HashMap<>(automationSteps);
        allAutomationSteps.putAll(guiAutomationSteps);
        return HashMap.ofAll(allAutomationSteps)
            .map((code, automationStep) ->
                decorateWithLogging(loggingAutomationStepFactory, AutomationStep.class, automationStep)
                    .map1(aClass -> code)
            );
    }

    private static Tuple2<Class<? extends AutomationStep>, AutomationStep> decorateWithLogging(
        LoggingAutomationStepFactory loggingAutomationStepFactory,
        Class<? extends AutomationStep> automationStepKey,
        AutomationStep automationStep
    ) {
        if (!automationStepKey.isInterface()) {
            return Tuple.of(automationStepKey, automationStep);
        }
        AutomationStep automationStepProxy = loggingAutomationStepFactory.decorate(automationStepKey, automationStep);
        return Tuple.of(automationStepKey, automationStepProxy);
    }
}
