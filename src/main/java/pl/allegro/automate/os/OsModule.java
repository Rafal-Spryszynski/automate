package pl.allegro.automate.os;

import dagger.Binds;
import dagger.Module;

@Module
public interface OsModule {

    @Binds
    ProcessCommand bindProcessCommand(OsProcessCommand osProcessCommand);
}
