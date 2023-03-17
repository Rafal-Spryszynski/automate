package pl.allegro.automate.adapter;

import dagger.Module;
import dagger.Provides;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.gui.GuiAutomationSteps;

@Module
interface AutomateModule {

    @Provides
    static Map<Class<? extends AutomationStep>, AutomationStep> provideAllAutomationSteps(
        java.util.Map<Class<? extends AutomationStep>, AutomationStep> automationSteps,
        @GuiAutomationSteps java.util.Map<Class<? extends AutomationStep>, AutomationStep> guiAutomationSteps
    ) {
        var allAutomationSteps = new java.util.HashMap<>(automationSteps);
        allAutomationSteps.putAll(guiAutomationSteps);
        return HashMap.ofAll(allAutomationSteps);
    }
}
