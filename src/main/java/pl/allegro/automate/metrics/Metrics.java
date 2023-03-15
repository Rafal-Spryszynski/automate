package pl.allegro.automate.metrics;

import java.util.concurrent.Callable;

public interface Metrics {

    <T> T measure(String label, Callable<T> action) throws Exception;
}
