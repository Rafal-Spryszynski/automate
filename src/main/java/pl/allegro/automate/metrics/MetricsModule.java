package pl.allegro.automate.metrics;

import dagger.Binds;
import dagger.Module;

@Module
public interface MetricsModule {

    @Binds
    Metrics bindMetrics(LoggingMetrics metrics);
}
