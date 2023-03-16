package pl.allegro.automate.flow;

import io.vavr.control.Option;

import javax.inject.Inject;
import java.time.Duration;
import java.util.function.Supplier;

public class LoopCommand {

    private final SleepCommand sleepCommand;

    @Inject
    LoopCommand(SleepCommand sleepCommand) {
        this.sleepCommand = sleepCommand;
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
