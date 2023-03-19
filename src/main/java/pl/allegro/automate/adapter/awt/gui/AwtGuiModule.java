package pl.allegro.automate.adapter.awt.gui;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.vavr.control.Try;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.AutomationStepKey;
import pl.allegro.automate.gui.GuiAutomationSteps;
import pl.allegro.automate.gui.LoadImageCommand;
import pl.allegro.automate.gui.SendMouseClickCommand;
import pl.allegro.automate.gui.TakeScreenCaptureCommand;
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
    @AutomationStepKey(LoadImageCommand.class)
    @IntoMap
    AutomationStep bindLoadImageCommand(LoadImageFromDiskCommand command);

    @Binds
    TakeScreenCaptureCommand bindTakeScreenCaptureCommand(TakeDeviceScreenCaptureAutomationStep command);

    @Binds
    @AutomationStepKey(TakeScreenCaptureCommand.class)
    @IntoMap
    AutomationStep bindTakeScreenCaptureStep(TakeDeviceScreenCaptureAutomationStep command);

    @Binds
    @AutomationStepKey(SendMouseClickCommand.class)
    @IntoMap
    AutomationStep bindSendMouseClickCommand(SendDeviceMouseClickAutomationStep command);

    @Binds
    @AutomationStepKey(TypeCharsAutomationStep.class)
    @IntoMap
    AutomationStep typeCharsAutomationStep(TypeKeyboardCharsAutomationStep automationStep);

    @Provides
    static Robot robot(Duration autoDelay) {
        return Try.of(Robot::new)
            .peek(robot -> robot.setAutoDelay((int) autoDelay.toMillis()))
            .get();
    }
}
