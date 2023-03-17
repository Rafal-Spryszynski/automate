package pl.allegro.automate.adapter.system.flow;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.AutomationStepKey;
import pl.allegro.automate.flow.SleepCommand;

@Module
public interface SystemFlowModule {

    @Binds
    SleepCommand bindSleepCommand(ThreadSleepCommand command);

    @Binds
    @AutomationStepKey(SleepCommand.class)
    @IntoMap
    AutomationStep bindSleepAutomationStep(ThreadSleepCommand command);
}
