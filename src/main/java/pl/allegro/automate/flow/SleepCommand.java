package pl.allegro.automate.flow;

import pl.allegro.automate.AutomationStep;

import java.time.Duration;

public interface SleepCommand extends AutomationStep {

    void sleep(Duration duration);
}
