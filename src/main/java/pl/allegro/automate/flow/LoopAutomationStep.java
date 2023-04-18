package pl.allegro.automate.flow;

import io.vavr.control.Option;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.Exchange;

import javax.inject.Inject;
import java.time.Duration;
import java.util.function.Supplier;

public class LoopAutomationStep implements AutomationStep {

    private final SleepAutomationStep sleepAutomationStep;
    private final Duration defaultSleepDuration;

    @Inject
    LoopAutomationStep(SleepAutomationStep sleepAutomationStep, Duration defaultSleepDuration) {
        this.sleepAutomationStep = sleepAutomationStep;
        this.defaultSleepDuration = defaultSleepDuration;
    }

    @Override
    public void execute(Exchange exchange) {
        Supplier<Option<Object>> automationSteps = exchange.getNextParam();
        Duration sleepDuration = exchange.<Duration>getLastOptionalParam()
            .getOrElse(defaultSleepDuration);
        loop(automationSteps, sleepDuration);
    }

    public <T> T loop(Supplier<Option<T>> automationSteps) {
        return loop(automationSteps, defaultSleepDuration);
    }

    public <T> T loop(Supplier<Option<T>> automationSteps, Duration sleepDuration) {
        Option<T> result = automationSteps.get();

        while (result.isEmpty()) {
            sleepAutomationStep.sleep(sleepDuration);
            result = automationSteps.get();
        }
        return result.get();
    }
}
