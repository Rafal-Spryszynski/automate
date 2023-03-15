package pl.allegro.automate.time;

import dagger.Module;
import dagger.Provides;

import java.time.Clock;

@Module
public interface TimeModule {

    @Provides
    static Clock provideClock() {
        return Clock.systemDefaultZone();
    }
}
