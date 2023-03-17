package pl.allegro.automate.flow;

import io.vavr.control.Option;
import pl.allegro.automate.AutomationStep;

import javax.inject.Inject;
import java.time.Duration;
import java.util.function.Supplier;

public class LoopCommand implements AutomationStep {

    private final SleepCommand sleepCommand;
    private final Duration defaultSleepDuration;

    @Inject
    LoopCommand(SleepCommand sleepCommand, Duration defaultSleepDuration) {
        this.sleepCommand = sleepCommand;
        this.defaultSleepDuration = defaultSleepDuration;
    }

    public <T> T loop(Supplier<Option<T>> automationSteps) {
        return loop(automationSteps, defaultSleepDuration);
    }

    public <T> T loop(Supplier<Option<T>> automationSteps, Duration sleepDuration) {
        Option<T> result = automationSteps.get();

        while (result.isEmpty()) {
            sleepCommand.sleep(sleepDuration);
            result = automationSteps.get();
        }
        return result.get();
    }
}
