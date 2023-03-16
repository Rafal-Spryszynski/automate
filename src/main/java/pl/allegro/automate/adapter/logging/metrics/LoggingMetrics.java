package pl.allegro.automate.adapter.logging.metrics;

import io.vavr.control.Try;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.allegro.automate.metrics.Metrics;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.util.concurrent.Callable;

class LoggingMetrics implements Metrics {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    LoggingMetrics() {}

    @Override
    public <T> T measure(String format, Callable<T> action, Object... arguments) {
        String label = MessageFormat.format(format, arguments);
        StopWatch stopWatch = new StopWatch(label);
        stopWatch.start();
        T result = Try.of(action::call)
            .onFailure(e -> logTime(stopWatch))
            .get();
        logTime(stopWatch);
        return result;
    }

    private void logTime(StopWatch stopWatch) {
        stopWatch.stop();
        LOG.info("{}", stopWatch);
    }
}
