package pl.allegro.automate.adapter.system.flow;

import dagger.Binds;
import dagger.Module;
import pl.allegro.automate.flow.SleepCommand;

@Module
public interface SystemFlowModule {

    @Binds
    SleepCommand bindSleepCommand(ThreadSleepCommand command);
}
