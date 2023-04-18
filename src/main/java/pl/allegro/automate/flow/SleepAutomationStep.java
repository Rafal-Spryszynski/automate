package pl.allegro.automate.flow;

import pl.allegro.automate.AutomationStep;

import java.time.Duration;

public interface SleepAutomationStep extends AutomationStep {

    void sleep(Duration duration);
}
