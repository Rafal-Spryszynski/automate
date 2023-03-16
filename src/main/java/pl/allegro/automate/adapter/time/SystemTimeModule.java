package pl.allegro.automate.adapter.time;

import dagger.Module;
import dagger.Provides;

import java.time.Clock;

@Module
public interface SystemTimeModule {

    @Provides
    static Clock provideClock() {
        return Clock.systemDefaultZone();
    }
}
