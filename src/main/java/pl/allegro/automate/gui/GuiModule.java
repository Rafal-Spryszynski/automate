package pl.allegro.automate.gui;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.AutomationStepKey;

@Module
public interface GuiModule {

    @Binds
    @AutomationStepKey(FindImageInImageCommand.class)
    @IntoMap
    AutomationStep bindFindImageInImageCommand(FindImageInImageCommand command);
}
