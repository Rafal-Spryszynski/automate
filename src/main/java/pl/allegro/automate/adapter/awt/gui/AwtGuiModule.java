package pl.allegro.automate.adapter.awt.gui;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.AutomationStepKey;
import pl.allegro.automate.gui.GuiAutomationSteps;
import pl.allegro.automate.gui.LoadImageCommand;
import pl.allegro.automate.gui.SendMouseClickCommand;
import pl.allegro.automate.gui.TakeScreenCaptureCommand;

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
    @AutomationStepKey(TakeScreenCaptureCommand.class)
    @IntoMap
    AutomationStep bindTakeScreenCaptureCommand(TakeDeviceScreenCaptureCommand command);

    @Binds
    @AutomationStepKey(SendMouseClickCommand.class)
    @IntoMap
    AutomationStep bindSendMouseClickCommand(SendDeviceMouseClickCommand command);
}
