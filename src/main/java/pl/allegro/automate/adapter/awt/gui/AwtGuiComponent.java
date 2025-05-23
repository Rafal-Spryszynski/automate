package pl.allegro.automate.adapter.awt.gui;

import dagger.BindsInstance;
import dagger.Component;
import pl.allegro.automate.AutomationFlow;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.adapter.logging.metrics.LoggingMetricsModule;
import pl.allegro.automate.adapter.system.time.SystemTimeModule;
import pl.allegro.automate.gui.GuiAutomationSteps;
import pl.allegro.automate.gui.TakeScreenCaptureAutomationStep;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;

@Component(modules = {
    AwtGuiModule.class,
    LoggingMetricsModule.class,
    SystemTimeModule.class
})
public interface AwtGuiComponent {

    TakeScreenCaptureAutomationStep takeScreenCaptureAutomationStep();

    @GuiAutomationSteps
    Map<Class<? extends AutomationStep>, AutomationStep> automationSteps();

    @GuiAutomationSteps
    Map<AutomationFlow.Step.Code, AutomationStep> automationStepsByCode();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder imagesPath(Path path);

        @BindsInstance
        Builder saveScreenCaptures(boolean saveScreenCaptures);

        @BindsInstance
        Builder autoDelay(Duration autoDelay);

        AwtGuiComponent build();
    }
}
