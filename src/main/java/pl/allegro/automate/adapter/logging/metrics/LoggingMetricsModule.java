package pl.allegro.automate.adapter.logging.metrics;

import dagger.Binds;
import dagger.Module;
import pl.allegro.automate.metrics.Metrics;

@Module
public interface LoggingMetricsModule {

    @Binds
    Metrics bindMetrics(LoggingMetrics metrics);
}
