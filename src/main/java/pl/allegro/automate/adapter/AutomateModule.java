package pl.allegro.automate.adapter;

import dagger.Module;
import dagger.Provides;
import io.vavr.Tuple;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.adapter.logging.metrics.LoggingAutomationStepFactory;
import pl.allegro.automate.gui.GuiAutomationSteps;

@Module
interface AutomateModule {

    @Provides
    static Map<Class<? extends AutomationStep>, AutomationStep> provideAllAutomationSteps(
        java.util.Map<Class<? extends AutomationStep>, AutomationStep> automationSteps,
        @GuiAutomationSteps java.util.Map<Class<? extends AutomationStep>, AutomationStep> guiAutomationSteps,
        LoggingAutomationStepFactory loggingAutomationStepFactory
    ) {
        var allAutomationSteps = new java.util.HashMap<>(automationSteps);
        allAutomationSteps.putAll(guiAutomationSteps);
        return HashMap.ofAll(allAutomationSteps)
            .map((automationStepKey, automationStep) -> {
                if (!automationStepKey.isInterface()) {
                    return Tuple.of(automationStepKey, automationStep);
                }
                AutomationStep automationStepProxy = loggingAutomationStepFactory.decorate(automationStepKey, automationStep);
                return Tuple.of(automationStepKey, automationStepProxy);
            });
    }
}
