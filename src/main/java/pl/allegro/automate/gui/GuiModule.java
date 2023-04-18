package pl.allegro.automate.gui;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.AutomationStepKey;

@Module
public interface GuiModule {

    @Binds
    @AutomationStepKey(FindImageInImageAutomationStep.class)
    @IntoMap
    AutomationStep bindFindImageInImageCommand(FindImageInImageAutomationStep command);

    @Binds
    @AutomationStepKey(FindImageOnScreenAutomationStep.class)
    @IntoMap
    AutomationStep bindFindImageOnScreenAutomationStep(FindImageOnScreenAutomationStep command);
}
