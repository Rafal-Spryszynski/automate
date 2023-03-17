package pl.allegro.automate.adapter.os;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.AutomationStepKey;
import pl.allegro.automate.os.StartProcessCommand;

@Module
public interface OsModule {

    @Binds
    @AutomationStepKey(StartProcessCommand.class)
    @IntoMap
    AutomationStep bindStartProcessCommand(StartOsProcessCommand command);
}
