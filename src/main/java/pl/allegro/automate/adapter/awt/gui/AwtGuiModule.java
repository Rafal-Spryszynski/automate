package pl.allegro.automate.adapter.awt.gui;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.vavr.control.Try;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.AutomationStepKey;
import pl.allegro.automate.gui.GuiAutomationSteps;
import pl.allegro.automate.gui.LoadImageAutomationStep;
import pl.allegro.automate.gui.SendMouseClickAutomationStep;
import pl.allegro.automate.gui.TakeScreenCaptureAutomationStep;
import pl.allegro.automate.gui.TypeCharsAutomationStep;

import java.awt.Robot;
import java.time.Duration;
import java.util.Map;

@Module
interface AwtGuiModule {

    @Binds
    @GuiAutomationSteps
    Map<Class<? extends AutomationStep>, AutomationStep> guiAutomationSteps(Map<Class<? extends AutomationStep>, AutomationStep> guiAutomationSteps);

    @Binds
    @AutomationStepKey(LoadImageAutomationStep.class)
    @IntoMap
    AutomationStep bindLoadImageAutomationStep(LoadImageFromDiskAutomationStep automationStep);

    @Binds
    TakeScreenCaptureAutomationStep bindTakeScreenCaptureAutomationStep(TakeDeviceScreenCaptureAutomationStep automationStep);

    @Binds
    @AutomationStepKey(TakeScreenCaptureAutomationStep.class)
    @IntoMap
    AutomationStep bindTakeScreenCaptureStep(TakeDeviceScreenCaptureAutomationStep automationStep);

    @Binds
    SendMouseClickAutomationStep bindSendMouseClickAutomationStep(SendDeviceMouseClickAutomationStep automationStep);

    @Binds
    @AutomationStepKey(SendMouseClickAutomationStep.class)
    @IntoMap
    AutomationStep bindSendMouseClickStep(SendDeviceMouseClickAutomationStep automationStep);

    @Binds
    TypeCharsAutomationStep bindTypeCharsAutomationStep(TypeKeyboardCharsAutomationStep automationStep);

    @Binds
    @AutomationStepKey(TypeCharsAutomationStep.class)
    @IntoMap
    AutomationStep bindTypeCharsStep(TypeKeyboardCharsAutomationStep automationStep);

    @Provides
    static Robot robot(Duration autoDelay) {
        return Try.of(Robot::new)
            .peek(robot -> robot.setAutoDelay((int) autoDelay.toMillis()))
            .get();
    }
}
