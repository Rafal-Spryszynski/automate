package pl.allegro.automate.adapter.os;

import dagger.Binds;
import dagger.Module;
import pl.allegro.automate.os.StartProcessCommand;

@Module
public interface OsModule {

    @Binds
    StartProcessCommand bindStartProcessCommand(StartOsProcessCommand command);
}
