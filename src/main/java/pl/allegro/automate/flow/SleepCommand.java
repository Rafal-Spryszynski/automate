package pl.allegro.automate.flow;

import pl.allegro.automate.VoidAutomationStep;

import java.time.Duration;

public interface SleepCommand extends VoidAutomationStep {

    default void sleep(Duration duration) {
        run(duration);
    }
}
