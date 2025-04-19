package pl.allegro.automate.gui;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.allegro.automate.AutomationFlow;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.AutomationStepEnumKey;
import pl.allegro.automate.AutomationStepKey;

@Module
public interface GuiModule {

    @Binds
    @AutomationStepKey(FindImageInImageAutomationStep.class)
    @IntoMap
    AutomationStep bindFindImageInImageAutomationStep(FindImageInImageAutomationStep command);

    @Binds
    @AutomationStepKey(FindImageOnScreenAutomationStep.class)
    @IntoMap
    AutomationStep bindFindImageOnScreenAutomationStep(FindImageOnScreenAutomationStep command);

    @Binds
    @AutomationStepEnumKey(AutomationFlow.Step.Code.MOUSE_CLICK)
    @IntoMap
    AutomationStep bindSendMouseClickAutomationStep(SendMouseClickAutomationStep automationStep);

    @Binds
    @AutomationStepEnumKey(AutomationFlow.Step.Code.TYPE_CHARS)
    @IntoMap
    AutomationStep bindTypeCharsAutomationStep(TypeCharsAutomationStep automationStep);
}
