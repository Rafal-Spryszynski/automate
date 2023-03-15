package pl.allegro.automate.metrics;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.Callable;

class LoggingMetrics implements Metrics {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    LoggingMetrics() {}

    @Override
    public <T> T measure(String label, Callable<T> action) throws Exception {
        StopWatch stopWatch = new StopWatch(label);
        stopWatch.start();
        T result = action.call();
        stopWatch.stop();
        LOG.info("{}", stopWatch);
        return result;
    }
}
