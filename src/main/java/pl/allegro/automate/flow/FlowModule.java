package pl.allegro.automate.flow;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.AutomationStepKey;

@Module
public interface FlowModule {

    @Binds
    @AutomationStepKey(LoopCommand.class)
    @IntoMap
    AutomationStep bindLoopCommand(LoopCommand command);
}
