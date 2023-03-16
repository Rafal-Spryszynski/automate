package pl.allegro.automate.metrics;

import java.util.concurrent.Callable;

public interface Metrics {

    <T> T measure(String format, Callable<T> action, Object... arguments);
}
